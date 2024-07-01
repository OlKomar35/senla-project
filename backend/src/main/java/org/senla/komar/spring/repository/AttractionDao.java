package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Attraction;
import org.senla.komar.spring.entity.Hotel;

import java.util.List;

public interface AttractionDao extends GenericDao<Long, Attraction> {

    List<Attraction> getAll(Integer limit, Integer page);

    List<Attraction > getAttractionsNearHotel(Hotel hotel, Integer limit, Integer page);

    List<Attraction> getByAttractionsName(String cityName, Integer limit, Integer page);

    List<Attraction> getByAttractionsAndStreet(String cityName, String streetName, Integer limit, Integer page);
}
