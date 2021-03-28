package ru.sfedu.hiber.lab5.api;

import ru.sfedu.hiber.Constants;
import ru.sfedu.hiber.lab5.model.*;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class DataProviderHibernate implements DataProvider {

    @Override
    public Optional<Category> getCategory(String id) {
        return HibernateUtil.getById(Category.class, id);
    }

    @Override
    public Optional<Computer> getComputer(long id) {
        return HibernateUtil.getById(Computer.class, id);
    }

    @Override
    public Optional<Fridge> getFridge(long id) {
        return HibernateUtil.getById(Fridge.class, id);
    }

    @Override
    public Optional<Receipt> getReceipt(long id) {
        return HibernateUtil.getById(Receipt.class, id);
    }

    @Override
    public Optional<Soda> getSoda(long id) {
        return HibernateUtil.getById(Soda.class, id);
    }

    @Override
    public Optional<Bucket> getBucketById(String id) {
        return HibernateUtil.getById(Bucket.class, id);
    }

    @Override
    public boolean insertComputer(Computer item) {
        return HibernateUtil.runTransactionSafe(session -> session.save(item));
    }

    @Override
    public boolean insertFridge(Fridge item) {
        return HibernateUtil.runTransactionSafe(session -> session.save(item));
    }

    @Override
    public boolean insertSoda(Soda item) {
        return HibernateUtil.runTransactionSafe(session -> session.save(item));
    }

    @Override
    public boolean insertReceipt(Receipt item) {
        return HibernateUtil.runTransactionSafe(session -> session.save(item));
    }


    @Override
    public boolean insertCategory(Category item) {
        return HibernateUtil.runTransactionSafe(session -> session.save(item));
    }

    @Override
    public boolean deleteBucket(Bucket item) throws Exception {
        return HibernateUtil.runTransactionSafe(session -> session.delete(item));
    }

    @Override
    public boolean upsertBucket(Bucket item) throws Exception {
        boolean insert = HibernateUtil.runTransactionSafe(session -> session.save(item));
        if (!insert) {
            return HibernateUtil.runTransactionSafe(session -> session.update(item));
        }
        return true;
    }

    @Override
    public boolean insertBucket(Bucket item) throws Exception {
        return HibernateUtil.runTransactionSafe(session -> session.save(item));
    }

    @Override
    public List<Bucket> getAllBucket() {
        return HibernateUtil.getAll(Bucket.class);
    }

    @Override
    public List<Category> getAllCategory() {
        return HibernateUtil.getAll(Category.class);
    }

    @Override
    public List<Computer> getAllComputer() {
        return HibernateUtil.getAll(Computer.class);
    }

    @Override
    public List<Fridge> getAllFridge() {
        return HibernateUtil.getAll(Fridge.class);
    }

    @Override
    public List<Receipt> getAllReceipt() {
        return HibernateUtil.getAll(Receipt.class);
    }

    @Override
    public List<Soda> getAllSoda() {
        return HibernateUtil.getAll(Soda.class);
    }

    @Override
    public void deleteAll() {
        Constants.ENTITIES.forEach(entity -> {
            HibernateUtil.runTransactionSafe(session -> {
                String query = String.format("DELETE FROM %s", entity);
                session.createSQLQuery(query).executeUpdate();
            });
        });
    }

    @Override
    public List<Computer> getComputersSql() {
        return HibernateUtil.getAllComputeSql();
    }

    @Override
    public List<Computer> getComputersHql() {
        return HibernateUtil.getAllComputerHql();
    }

    @Override
    public List<Computer> getComputersCriteria() {
        return HibernateUtil.getAllComputerCriteria();
    }

    @Override
    public boolean deleteCategory(String category) {
        Optional<Category> categoryOption = HibernateUtil.getById(Category.class, category);
        if (!categoryOption.isPresent()) {
            LOG.error("Category with name {} doesnt exist!", category);
            return false;
        }
        return HibernateUtil.runTransactionSafe(session -> session.delete(categoryOption.get()));
    }
}
