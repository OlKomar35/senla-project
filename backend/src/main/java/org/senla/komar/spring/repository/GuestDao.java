package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Guest;

import java.math.BigDecimal;
import java.util.List;

public interface GuestDao extends GenericDao<Long, Guest> {
    Guest getGuestByPhoneNumber(String phoneNumber);
    List<Guest> getGuestsBySurname(String surname, Integer limit, Integer page);

    List<Guest> getAll(Integer limit, Integer page);

    BigDecimal getRankById(Long id);
}
