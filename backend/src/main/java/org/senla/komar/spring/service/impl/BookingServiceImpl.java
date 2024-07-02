package org.senla.komar.spring.service.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.AuthPersonDto;
import org.senla.komar.spring.dto.BookingDtoFullInfo;
import org.senla.komar.spring.entity.Booking;
import org.senla.komar.spring.enums.BookingStatus;
import org.senla.komar.spring.mapper.BookingFullInfoMapper;
import org.senla.komar.spring.mapper.FacilityMapper;
import org.senla.komar.spring.repository.BookingDao;
import org.senla.komar.spring.security.exception.AuthException;
import org.senla.komar.spring.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final BookingDao bookingDAO;
    private final BookingFullInfoMapper bookingFullInfoMapper;
    private final FacilityMapper facilityMapper;

    @Override
    public void createBooking(BookingDtoFullInfo booking) {
        bookingDAO.create(bookingFullInfoMapper.toBooking(booking));
    }

    @Override
    public BookingDtoFullInfo getBookingById(AuthPersonDto authPersonDto, Long id) {
        BookingDtoFullInfo bookingDto = bookingFullInfoMapper.toDtoFull(bookingDAO.readById(id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        if (!auth.getName().equals(bookingDto.getGuest().getPerson().getLogin())&& roles.contains("ROLE_USER")) {
            throw new AuthException(HttpStatus.BAD_REQUEST, "Нельзя просмотреть чужие бронирования");
        }

        return bookingDto;
    }

    @Override
    public List<BookingDtoFullInfo> getAllBooking(Integer limit, Integer page) {
        return getAllListWithRoles(bookingDAO.getAll(limit, page));
    }

    @Override
    public void deleteById(Long id) {
        bookingDAO.deleteById(id);
    }

    @Override
    public void updateById(Long id, BookingDtoFullInfo newBooking) {
        newBooking.setId(id);
        bookingDAO.update(id,bookingFullInfoMapper.toBooking(newBooking));
    }

    @Override
    public List<BookingDtoFullInfo> getAllBookingsByDateIn(Date date, Integer limit, Integer page) {
        return  getAllListWithRoles(bookingDAO.getAllBookingsByDateIn(date, limit, page));
    }

    @Override
    public List<BookingDtoFullInfo> getAllBookingsByDateOut(Date date, Integer limit, Integer page) {
        return  getAllListWithRoles(bookingDAO.getAllBookingsByDateOut(date, limit, page));
    }

    @Override
    public List<BookingDtoFullInfo> getAllBookingsByStatus(BookingStatus status, Integer limit, Integer page) {
        return  getAllListWithRoles(bookingDAO.getAllBookingsByStatus(status, limit, page));
    }

    @Override
    public ResponseEntity<?> getAllFacilityByBookingId(AuthPersonDto personDto,Long id){
        BookingDtoFullInfo bookingDto = bookingFullInfoMapper.toDtoFull(bookingDAO.readById(id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        if (!auth.getName().equals(bookingDto.getGuest().getPerson().getLogin()) && roles.contains("ROLE_USER")) {
            return new ResponseEntity<>(
                    new AuthException(HttpStatus.BAD_REQUEST, "Нельзя просмотреть чужие бронирования"),
                    HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(bookingDAO.getAllFacilityByBookingId(id)
                .stream()
                .map(facilityMapper::toDto)
                .toList());
    }

    private List<BookingDtoFullInfo> getAllListWithRoles(List<Booking> bookings) {
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
