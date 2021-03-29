package ru.sfedu.hiber.lab3.strategy3.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.hiber.lab3.strategy3.model.Account2;
import ru.sfedu.hiber.lab3.strategy3.model.CreditAccount2;
import ru.sfedu.hiber.lab3.strategy3.model.DebitAccount2;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Strategy3Provider implements IProvider {
    private final static Logger LOG = LogManager.getLogger(Strategy3Provider.class);

    public Strategy3Provider() throws IOException {
    }

    @Override
    public Optional<List> getByAccounts() {
        Session session;
        List accounts = null;
        Transaction transaction = null;
        session = getSession();
        try {
            transaction = session.beginTransaction();
            accounts = session.createQuery("FROM Account2").list();
        } catch (Exception e) {
            LOG.error(e);
            transaction.rollback();
        } finally {
            session.close();
        }
        return Optional.of(accounts);
    }

    @Override
    public <T> Optional<T> getByTypeAccount(long id) {
        Session session;
        Object typeAccount = null;
        Transaction transaction = null;
        session = getSession();
        try {
            transaction = session.beginTransaction();
            typeAccount = session.createQuery("FROM Account2 p WHERE p.id = :id", Account2.class)
                    .setParameter("id", id)
                    .getResultList().get(0);
        } catch (Exception e) {
            LOG.error(e);
            transaction.rollback();
        } finally {
            session.close();
        }
        return (Optional<T>) Optional.of(typeAccount);
    }


    @Override
    public List<Long> save(CreditAccount2 creditAccount, DebitAccount2 debitAccount) {
        if (creditAccount == null || debitAccount == null) return null;
        Session session = null;
        Transaction transaction;
        List<Long> ids = new ArrayList<>();
        long id;
        try {
            session = getSession();
            transaction = session.beginTransaction();
            id = (long) session.save(creditAccount);
            ids.add(id);
            id = (long) session.save(debitAccount);
            ids.add(id);
            transaction.commit();
        } catch (NullPointerException e) {
            LOG.error(e);
        } finally {
            session.close();
        }
        return ids;
    }

    @Override
    public <T> Optional<T> updateTypeAccount(long id, String name) {
        Session session = null;
        Transaction transaction;
        Object updateEntity = null;
        try {
            session = getSession();
            updateEntity = getByTypeAccount(id).get();
            //updateEntity = entity.getName().equals(CreditAccount1.class.getName()) ? (CreditAccount1) updateEntity : (DebitAccount1) updateEntity;
            ((Account2) updateEntity).setOwner(name);
            transaction = session.beginTransaction();
            session.update(updateEntity);
            transaction.commit();
        } catch (Exception e) {
            LOG.error(e);
        } finally {
            session.close();
        }
        return (Optional<T>) Optional.of(updateEntity);
    }

    @Override
    public <T> boolean deleteTypeAccount(long id) {
        Session session = null;
        Transaction transaction;
        Object deleteEntity;
        try {
            session = getSession();
            deleteEntity = getByTypeAccount(id).get();
            transaction = session.beginTransaction();
            session.delete(deleteEntity);
            transaction.commit();
        } catch (NullPointerException e) {
            LOG.error(e);
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    private Session getSession() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return sessionFactory.openSession();
    }

}
