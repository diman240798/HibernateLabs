package ru.sfedu.hiber.lab4.api;

import java.util.Optional;

public interface IProviderBaseCrud<T, R> {
    Optional<T> get(R id);
    boolean add(T model);
    boolean delete(T model);
    boolean update(T model);
}
