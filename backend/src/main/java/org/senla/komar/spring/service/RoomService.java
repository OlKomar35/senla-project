package org.senla.komar.spring.service;

import java.util.List;
import org.senla.komar.spring.dto.RoomDto;


public interface RoomService {
    void createRoom(RoomDto room);

    RoomDto getRoomById(Long id);

    List<RoomDto> getAll();

    void deleteById(Long id);

    void updateRoomById(Long id, RoomDto newRoom);
}
