package com.example.class_management_android.database;

import java.util.List;

public interface DbHelper <T> {
    List<T> getList();
    T get(String id);
    long add(T object);
    int delete(String id);
    int update(T object);
    List<T> search(String search);
}
