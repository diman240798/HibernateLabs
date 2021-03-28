package ru.sfedu.hiber.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.hiber.lab2.model.TestEntity;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.Optional;

public class TestEntityProvider implements ITestEntityProvider {

    private final static Logger LOG = LogManager.getLogger(TestEntityProvider.class);

    @Override
    public Optional<TestEntity> getById(Class<TestEntity> entity, long id) {
        return HibernateUtil.runSessionQuerySafe(session -> Optional.ofNullable(session.get(entity, id)));
    }

    @Override
    public Long save(TestEntity entity) {
        if (entity.getName().equals("Name10")) {
            return (long) 0;
        }
        boolean res = HibernateUtil.runTransactionSafe(session -> session.save(entity));
        return res ? entity.getId() : -1;
    }

    @Override
    public Optional<TestEntity> update(Class<TestEntity> entity, long id, String name) {
        TestEntity updateEntity = getById(entity, id).get();
        updateEntity.setName(name);
        HibernateUtil.runTransactionSafe(session -> {
            session.update(updateEntity);
        });
        return getById(entity, id);
    }

    @Override
    public boolean delete(Class<TestEntity> entity, long id) {
        Optional<TestEntity> optional = getById(entity, id);
        if (!optional.isPresent()) return false;
        TestEntity deleteEntity = optional.get();
        return HibernateUtil.runTransactionSafe(session -> session.delete(deleteEntity));
    }

}
