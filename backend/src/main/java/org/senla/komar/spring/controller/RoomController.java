package org.senla.komar.spring.controller;

import java.util.List;
import org.senla.komar.spring.dto.RoomDto;
import org.senla.komar.spring.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
    public void createRoom(RoomDto roomDto) {
        roomService.createRoom(roomDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RoomDto> viewAllRooms() {
        return roomService.getAll();
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public RoomDto getRoomById(@PathVariable("id") Long id) {
        return roomService.getRoomById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
    public void deleteRoomById(@PathVariable("id") Long id) {
        roomService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
    public void updateRoomById(@PathVariable("id") Long id, @RequestBody RoomDto newRoom) {
        roomService.updateRoomById(id, newRoom);
    }
}
