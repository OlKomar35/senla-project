package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.entity.*;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.HotelDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class HotelDaoImpl extends AbstractDao<Long, Hotel> implements HotelDao {
    @Override
    protected Class<Hotel> getEntityClass() {
        return Hotel.class;
    }

    @Override
    public List<Hotel> getHotelsByCity(String cityName) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteriaQuery = criteriaBuilder.createQuery(Hotel.class);
        Root<Hotel> hotelRoot = criteriaQuery.from(Hotel.class);

        hotelRoot.fetch(Hotel_.address, JoinType.INNER);

        Join<Hotel, Address> addressJoin = hotelRoot.join(Hotel_.address);
        Join<Address, City> cityJoin = addressJoin.join(Address_.city);

        criteriaQuery.select(hotelRoot)
                .where(criteriaBuilder.equal(cityJoin.get(City_.name), cityName));

        TypedQuery<Hotel> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Hotel> hotels = typedQuery.getResultList();

        return hotels;
    }

    @Override
    public List<Feedback> getFeedbacksById(Long id) {
        return readById(id).getFeedbacks();
    }

    @Override
    public BigDecimal getRankById(Long id) {
        return readById(id).getRank();
    }
}
