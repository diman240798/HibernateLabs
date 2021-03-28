package ru.sfedu.hiber.lab2.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import ru.sfedu.hiber.lab2.model.OtherEntity;
import ru.sfedu.hiber.lab2.model.TestEntity;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestEntityProviderTest {

    private final static Logger LOG = LogManager.getLogger(TestEntityProviderTest.class);


    @Test
    public void getByIdSuccess() throws IOException{
        ITestEntityProvider instance = new TestEntityProvider();
        Optional<TestEntity> entity = instance.getById(TestEntity.class, (long)1);
        LOG.info(entity.get());
        assertEquals((long)1, entity.get().getId());
    }

    @Test
    public void getByIdFail() throws IOException{
        ITestEntityProvider instance = new TestEntityProvider();
        Optional<TestEntity> entity = null;
        try {
            entity = instance.getById(TestEntity.class, (long) 77654567);
            LOG.info(entity);
        }catch (Exception ex){
            LOG.info(ex);
        }
        assertFalse(entity.isPresent());
    }

    @Test
    public void saveSuccess() throws IOException {
        TestEntity entity = getTestEntity();
        ITestEntityProvider instance = new TestEntityProvider();
        Long result = instance.save(entity);
        Optional<TestEntity> entityOption = instance.getById(TestEntity.class, result);
        TestEntity testEntity = entityOption.get();
        LOG.info(testEntity);
        assertEquals(entity, testEntity);
    }

    private TestEntity getTestEntity() {
        TestEntity entity = new TestEntity();
        OtherEntity otherEntity = new OtherEntity();
        entity.setName("Name1");
        entity.setDescription("test");
        entity.setDateCreated(new Date());
        entity.setCheck(true);
        otherEntity.setCount(10.76);
        otherEntity.setOther("Name2");
        otherEntity.setComplicationDate(new Date());
        entity.setOtherEntity(otherEntity);
        return entity;
    }

    @Test
    public void saveFail() throws IOException{
        TestEntity entity = new TestEntity();
        OtherEntity otherEntity = new OtherEntity();
        entity.setName("Name10");
        entity.setDescription("test");
        entity.setDateCreated(new Date());
        entity.setCheck(true);
        otherEntity.setCount(10.76);
        otherEntity.setOther("Name2");
        otherEntity.setComplicationDate(new Date());
        entity.setOtherEntity(otherEntity);
        ITestEntityProvider instance = new TestEntityProvider();
        Long result = instance.save(entity);
        assertEquals(Optional.of((long) 0), Optional.of((result)));
    }

    @Test
    public void updateSuccess() throws IOException{
        ITestEntityProvider instance = new TestEntityProvider();
        final TestEntity testEntity = getTestEntity();
        final Long id = instance.save(testEntity);
        final String newName = "NewName";
        TestEntity updateEntity = instance.update(TestEntity.class, id, newName).get();
        assertEquals(newName , updateEntity.getName());
    }

    @Test
    public void updateFail()throws IOException{
        TestEntity updateEntity = null;
        ITestEntityProvider instance = new TestEntityProvider();
        Assert.assertFalse(instance.getById(TestEntity.class, 2).isPresent());
    }

    @Test
    public void deleteSuccess()throws IOException{
        ITestEntityProvider instance = new TestEntityProvider();
        instance.delete(TestEntity.class, 10);
        Optional<TestEntity> entity = Optional.empty();
        try{
            instance.getById(TestEntity.class, 10);
        }catch (Exception e) {
            LOG.error(e);
        }
        assertFalse(entity.isPresent());
    }

    @Test
    public void deleteFail()throws IOException{
        ITestEntityProvider instance = new TestEntityProvider();
        boolean status;
        try {
            status = instance.delete(TestEntity.class, 123);
        }catch (Exception e){
            status = false;
            LOG.error(e);
        }
        assertFalse(status);
    }

}