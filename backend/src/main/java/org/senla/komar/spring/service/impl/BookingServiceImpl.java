package org.senla.komar.spring.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.AuthPersonDto;
import org.senla.komar.spring.dto.BookingDtoFullInfo;
import org.senla.komar.spring.entity.Booking;
import org.senla.komar.spring.enums.BookingStatus;
import org.senla.komar.spring.enums.MessageType;
import org.senla.komar.spring.enums.PaymentStatus;
import org.senla.komar.spring.event.MessageSentEvent;
import org.senla.komar.spring.exception.BookingNotFoundException;
import org.senla.komar.spring.mapper.BookingFullInfoMapper;
import org.senla.komar.spring.mapper.FacilityMapper;
import org.senla.komar.spring.repository.BookingRepository;
import org.senla.komar.spring.security.exception.AuthException;
import org.senla.komar.spring.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

  private final BookingRepository bookingRepository;
  private final BookingFullInfoMapper bookingFullInfoMapper;
  private final FacilityMapper facilityMapper;
  private final KafkaTemplate<String,MessageSentEvent> kafkaTemplate;

  @Override
  public void createBooking(BookingDtoFullInfo booking) {
    bookingRepository.save(bookingFullInfoMapper.toBooking(booking));

  }

  @Override
  public BookingDtoFullInfo getBookingById(AuthPersonDto authPersonDto, Long id) {
    Booking booking =  bookingRepository.findById(id).orElseThrow(() ->
        new BookingNotFoundException("Не нашлось гостиницу с id=" + id));
    BookingDtoFullInfo bookingDto = bookingRepository.findById(id)
        .map(bookingFullInfoMapper::toDtoFull)
        .orElseThrow(() -> new BookingNotFoundException("Не нашлось гостиницу с id=" + id));
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<String> roles = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    if (!auth.getName().equals(bookingDto.getGuest().getPerson().getLogin()) && roles.contains("ROLE_USER")) {
      throw new AuthException(HttpStatus.BAD_REQUEST, "Нельзя просмотреть чужие бронирования");
    }
    MessageSentEvent message = MessageSentEvent.builder()
        .messageType(MessageType.EMAIL)
        .bookingStatus(BookingStatus.NEW)
        .guestSurname(booking.getGuest().getPerson().getSurname())
        .guestFirstname(booking.getGuest().getPerson().getFirstname())
        .guestEmail(booking.getGuest().getPerson().getEmail())
        .hotelName("booking.getRoom().getHotel().getName()")
        .hotelAddress("booking.getRoom().getHotel().getAddress().toString()")
        .hotelPhoneNumber("booking.getRoom().getHotel().getPhoneNumber()")
        .hotelEmail("booking.getRoom().getHotel().getEmail()")
        .checkInDate(booking.getCheckInDate().toString())
        .checkOutDate(booking.getCheckOutDate().toString())
        .paymentStatus(booking.getPaymentStatus())
        .typeFood(booking.getTypeFood())
        .build();
    try {
      SendResult<String, MessageSentEvent> result = kafkaTemplate
          .send("message-send-topic",message).get();
    } catch (InterruptedException | ExecutionException e) {
      throw new RuntimeException(e);
    }

    return bookingDto;
  }

  @Override
  public List<BookingDtoFullInfo> getAllBooking(Integer limit, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, limit);
    return getAllListWithRoles(bookingRepository.findAll(pageable));
  }

  @Override
  public void deleteById(Long id) {
    bookingRepository.deleteById(id);
  }

  @Override
  public void updateById(Long id, BookingDtoFullInfo newBooking) {
    newBooking.setId(id);
    bookingRepository.save(bookingFullInfoMapper.toBooking(newBooking));
  }

  @Override
  public List<BookingDtoFullInfo> getAllBookingsByDateIn(Date date, Integer limit, Integer page) {
    Pageable pageable = PageRequest.of(page-1, limit);
    return getAllListWithRoles(bookingRepository.findAllByCheckInDate(date,pageable));
  }

  @Override
  public List<BookingDtoFullInfo> getAllBookingsByDateOut(Date date, Integer limit, Integer page) {
    Pageable pageable = PageRequest.of(page-1, limit);
    return getAllListWithRoles(bookingRepository.findAllByCheckOutDate(date,pageable));
  }

  @Override
  public List<BookingDtoFullInfo> getAllBookingsByStatus(BookingStatus status, Integer limit, Integer page) {
    Pageable pageable = PageRequest.of(page-1, limit);
    return getAllListWithRoles(bookingRepository.findAllByBookingStatus(status, pageable));
  }

  @Override
  public ResponseEntity<?> getAllFacilityByBookingId(AuthPersonDto personDto, Long id) {
    BookingDtoFullInfo bookingDto = bookingRepository.findById(id)
        .map(bookingFullInfoMapper::toDtoFull)
        .orElseThrow(() -> new BookingNotFoundException("Не нашлось гостиницу с id=" + id));;
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<String> roles = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    if (!auth.getName().equals(bookingDto.getGuest().getPerson().getLogin()) && roles.contains("ROLE_USER")) {
      return new ResponseEntity<>(
          new AuthException(HttpStatus.BAD_REQUEST, "Нельзя просмотреть чужие бронирования"),
          HttpStatus.BAD_REQUEST);
    }

    return ResponseEntity.ok(bookingRepository.getAllFacilityByBookingId(id)
        .stream()
        .map(facilityMapper::toDto)
        .toList());
  }

  private List<BookingDtoFullInfo> getAllListWithRoles(Page<Booking> bookings) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    List<String> roles = auth.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    if (roles.contains("ROLE_ADMIN") || roles.contains("ROLE_MANAGER") || roles.contains("ROLE_FRONT_DESK")) {
      return bookings.stream()
          .map(bookingFullInfoMapper::toDtoFull)
          .collect(Collectors.toList());
    }
    return bookings.stream()
        .map(bookingFullInfoMapper::toDtoFull)
        .filter(booking -> booking.getGuest().getPerson().getLogin().equals(auth.getName()))
        .collect(Collectors.toList());
  }
}
