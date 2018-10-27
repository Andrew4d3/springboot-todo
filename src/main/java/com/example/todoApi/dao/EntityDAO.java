package com.example.todoApi.dao;

import java.util.List;
import java.util.UUID;

public interface EntityDAO<T> {
    int insertNew(T newRecord);

    T select(UUID id);

    List<T> selectAll();

    int update(T modifiedRecord);

    int delete(UUID id);
}
