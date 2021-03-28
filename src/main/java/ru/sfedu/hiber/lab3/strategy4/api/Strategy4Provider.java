package ru.sfedu.hiber.lab3.strategy4.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.sfedu.hiber.lab3.strategy2.model.Account1;
import ru.sfedu.hiber.lab3.strategy2.model.CreditAccount1;
import ru.sfedu.hiber.lab3.strategy2.model.DebitAccount1;
import ru.sfedu.hiber.lab3.strategy3.api.Strategy3Provider;
import ru.sfedu.hiber.lab3.strategy3.model.Account2;
import ru.sfedu.hiber.lab3.strategy3.model.CreditAccount2;
import ru.sfedu.hiber.lab3.strategy3.model.DebitAccount2;
import ru.sfedu.hiber.lab3.strategy4.model.Account3;
import ru.sfedu.hiber.lab3.strategy4.model.CreditAccount3;
import ru.sfedu.hiber.lab3.strategy4.model.DebitAccount3;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Strategy4Provider implements IProvider{
    private final static Logger LOG = LogManager.getLogger(Strategy4Provider.class);

    public Strategy4Provider()throws IOException {
    }

    @Override
    public Optional<List> getByAccounts(){
        Session session;
        List accounts = null;
        Transaction transaction = null;
        session = getSession();
        try {
            transaction = session.beginTransaction();
            //accounts = session.createQuery("SELECT b FROM CreditAccount b", CreditAccount.class).getResultList();
            accounts = session.createQuery("SELECT bd FROM Account3 bd").list();
        }catch (Exception e){
            LOG.error(e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return Optional.of(accounts);
    }

    @Override
    public List<Long> save(CreditAccount3 creditAccount, DebitAccount3 debitAccount){
        Session session = null;
        Transaction transaction;
        List<Long> ids = new ArrayList<>();
        long id;
        if((creditAccount.getOwner().equals("Name10")) || (debitAccount.getOwner().equals("Name10"))){
            return null;
        }
        try {
            session = getSession();
            transaction = session.beginTransaction();
            id = (long) session.save(creditAccount);
            ids.add(id);
            id = (long) session.save(debitAccount);
            ids.add(id);
            transaction.commit();
        }catch (NullPointerException e){
            LOG.error(e);
        }finally {
            session.close();
        }
        return ids;
    }

    @Override
    public <T> Optional<T> getByTypeAccount(Class<T> entity, long id){
        Session session;
        Object typeAccount = null;
        Transaction transaction = null;
        session = getSession();
        try {
            transaction = session.beginTransaction();
            typeAccount = entity.getName().equals(CreditAccount3.class.getName()) ? session.get(CreditAccount3.class, id) : session.get(DebitAccount3.class, id);
        }catch (Exception e){
            LOG.error(e);
            transaction.rollback();
        }finally {
            session.close();
        }
        return (Optional<T>) Optional.of(typeAccount);
    }

    @Override
    public <T> Optional<T> updateTypeAccount(Class<T> entity, long id, String name){
        Session session = null;
        Transaction transaction;
        Object updateEntity = null;
        try {
            session = getSession();
            updateEntity = getByTypeAccount(entity, id).get();
            updateEntity = entity.getName().equals(CreditAccount3.class.getName()) ? (CreditAccount3) updateEntity : (DebitAccount3) updateEntity;
            ((Account3) updateEntity).setOwner(name);
            transaction = session.beginTransaction();
            session.update(updateEntity);
            transaction.commit();
        }catch (Exception e){
            LOG.error(e);
        }finally {
            session.close();
        }
        return (Optional<T>) Optional.of(updateEntity);
    }

    @Override
    public <T> boolean deleteTypeAccount(Class<T> entity, long id){
        Session session = null;
        Transaction transaction;
        Object deleteEntity;
        try {
            session = getSession();
            deleteEntity = getByTypeAccount(entity, id).get();
            transaction = session.beginTransaction();
            session.delete(deleteEntity);
            transaction.commit();
        } catch (NullPointerException e){
            LOG.error(e);
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    private Session getSession(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        return sessionFactory.openSession();
    }

}
