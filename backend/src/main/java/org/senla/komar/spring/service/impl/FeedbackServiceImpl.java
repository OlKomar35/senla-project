package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.AuthPersonDto;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.dto.GuestDto;
import org.senla.komar.spring.dto.HotelDtoFullInfo;
import org.senla.komar.spring.mapper.FeedbackMapper;
import org.senla.komar.spring.repository.FeedbackDao;
import org.senla.komar.spring.service.FeedbackService;
import org.senla.komar.spring.service.GuestService;
import org.senla.komar.spring.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackDao feedBackDAO;
    private final FeedbackMapper feedbackMapper;
    private final HotelService hotelService;
    private final GuestService guestService;


    @Override
    public void createFeedback(FeedbackDto feedbackDto) {
        feedBackDAO.create(feedbackMapper.toFeedback(feedbackDto));
    }

    @Override
    public void createHotelFeedback(FeedbackDto feedbackDto, Long id){
        createFeedback(feedbackDto);
        HotelDtoFullInfo hotel = hotelService.getHotelById(id);
        hotel.getFeedbacks().add(feedbackDto);
        hotelService.updateById(id, hotel);
        feedBackDAO.recalculateHotelRank(id);
    }

    @Override
    public void createGuestFeedback(FeedbackDto feedbackDto, Long id){
        createFeedback(feedbackDto);
        GuestDto guest = guestService.getGuestById(id);
        guest.getFeedbacks().add(feedbackDto);
        guestService.updateGuestById(id, guest);
        feedBackDAO.recalculateGuestRank(id);
    }
    @Override
    public ResponseEntity<?> getFeedbackById(AuthPersonDto authPersonDto, Long id) {
        return ResponseEntity.ok(feedbackMapper.toDto(feedBackDAO.readById(id)));
    }

    @Override
    public List<FeedbackDto> getAllFeedback(Integer limit, Integer page) {
        return feedBackDAO.getAll()
                .stream()
                .map(feedbackMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        feedBackDAO.deleteById(id);
    }

    @Override
    public void updateById(Long id, FeedbackDto newFeedback) {
        newFeedback.setId(id);
        feedBackDAO.update(id,feedbackMapper.toFeedback(newFeedback));
    }



}
