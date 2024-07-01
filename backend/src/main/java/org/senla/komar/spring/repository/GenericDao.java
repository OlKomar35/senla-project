package org.senla.komar.spring.repository;

import java.util.List;

public interface GenericDao<K,T> {
    void create(T entity);
    T readById(K id);
    List<T> getAll();
    T update(K id,T entity);
    void deleteById(K id);
}
