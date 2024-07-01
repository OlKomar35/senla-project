package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.RoomDto;
import org.senla.komar.spring.entity.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
    Room toRoom(RoomDto roomDto);
    RoomDto toDto(Room room);
}
