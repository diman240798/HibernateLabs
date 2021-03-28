package ru.sfedu.hiber.lab2.api;

import ru.sfedu.hiber.lab2.model.TestEntity;

import java.util.Optional;

public interface ITestEntityProvider {
    Optional<TestEntity> getById(Class<TestEntity> entity, long id);

    Long save(TestEntity entity);

    Optional<TestEntity> update(Class<TestEntity> entity, long id, String name);

    boolean delete(Class<TestEntity> entity, long id);
}
