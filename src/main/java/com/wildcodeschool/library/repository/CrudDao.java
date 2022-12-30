package com.wildcodeschool.library.repository;

import java.util.List;

public interface CrudDao<T> {

    T save(T entity);

    T findById(Long id);

    List<T> searchBook(String searchTerm);

    List<T> findAll();

    T update(T entity);

    void deleteById(Long id);
}