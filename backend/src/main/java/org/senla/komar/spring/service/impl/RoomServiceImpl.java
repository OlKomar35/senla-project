package org.senla.komar.spring.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.RoomDto;
import org.senla.komar.spring.mapper.RoomMapper;
import org.senla.komar.spring.repository.RoomDao;
import org.senla.komar.spring.service.RoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomDao roomDao;
    private final RoomMapper roomMapper;

    @Override
    public void createRoom(RoomDto room){
        roomDao.create(roomMapper.toRoom(room));
    }

    @Override
    public RoomDto getRoomById(Long id){
        return roomMapper.toDto(roomDao.readById(id));
    }

    @Override
    public List<RoomDto> getAll(){
       return roomDao.getAll()
               .stream()
               .map(roomMapper::toDto)
               .toList();
    }

    @Override
    public void deleteById(Long id) {
        roomDao.deleteById(id);
    }

    @Override
    public void updateRoomById(Long id, RoomDto newRoom) {
        newRoom.setId(id);
        roomDao.update(id,roomMapper.toRoom(newRoom));
    }
}
