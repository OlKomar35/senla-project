package org.senla.komar.spring.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.RoomDto;
import org.senla.komar.spring.exception.RoomNotFoundException;
import org.senla.komar.spring.mapper.RoomMapper;
import org.senla.komar.spring.repository.RoomRepository;
import org.senla.komar.spring.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

  private final RoomRepository roomRepository;
  private final RoomMapper roomMapper;

  @Override
  public void createRoom(RoomDto room) {
    roomRepository.save(roomMapper.toRoom(room));
  }

  @Override
  public RoomDto getRoomById(Long id) {
    return roomRepository.findById(id)
        .map(roomMapper::toDto)
        .orElseThrow(() -> new RoomNotFoundException("Не найден номер с id = " + id));
  }

  @Override
  public List<RoomDto> getAll() {
    return roomRepository.findAll()
        .stream()
        .map(roomMapper::toDto)
        .toList();
  }

  @Override
  public void deleteById(Long id) {
    roomRepository.deleteById(id);
  }

  @Override
  public void updateRoomById(Long id, RoomDto newRoom) {
    newRoom.setId(id);
    roomRepository.save(roomMapper.toRoom(newRoom));
  }
}
