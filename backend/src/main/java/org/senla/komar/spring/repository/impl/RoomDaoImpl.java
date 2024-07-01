package org.senla.komar.spring.repository.impl;

import org.senla.komar.spring.entity.Room;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.RoomDao;
import org.springframework.stereotype.Repository;

@Repository
public class RoomDaoImpl extends AbstractDao<Long, Room> implements RoomDao {
    @Override
    protected Class<Room> getEntityClass() {
        return Room.class;
    }
}
