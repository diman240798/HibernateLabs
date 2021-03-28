package ru.sfedu.hiber.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import ru.sfedu.hiber.Constants;
import ru.sfedu.hiber.func.ExConsumer;
import ru.sfedu.hiber.func.ExFunction;
import ru.sfedu.hiber.lab2.model.OtherEntity;
import ru.sfedu.hiber.lab2.model.TestEntity;
import ru.sfedu.hiber.lab3.strategy1.model.Account;
import ru.sfedu.hiber.lab3.strategy1.model.CreditAccount;
import ru.sfedu.hiber.lab3.strategy1.model.DebitAccount;
import ru.sfedu.hiber.lab3.strategy2.model.Account1;
import ru.sfedu.hiber.lab3.strategy2.model.CreditAccount1;
import ru.sfedu.hiber.lab3.strategy2.model.DebitAccount1;
import ru.sfedu.hiber.lab3.strategy3.model.Account2;
import ru.sfedu.hiber.lab3.strategy3.model.CreditAccount2;
import ru.sfedu.hiber.lab3.strategy3.model.DebitAccount2;
import ru.sfedu.hiber.lab3.strategy4.model.Account3;
import ru.sfedu.hiber.lab3.strategy4.model.CreditAccount3;
import ru.sfedu.hiber.lab3.strategy4.model.DebitAccount3;
import ru.sfedu.hiber.lab4.component.model.Category4Component;
import ru.sfedu.hiber.lab4.component.model.Product4Component;
import ru.sfedu.hiber.lab4.list.model.Category4List;
import ru.sfedu.hiber.lab4.list.model.Product4List;
import ru.sfedu.hiber.lab4.map.model.Category4Map;
import ru.sfedu.hiber.lab4.map.model.Product4Map;
import ru.sfedu.hiber.lab4.map_component.model.Category4MapComponent;
import ru.sfedu.hiber.lab4.map_component.model.Product4MapComponent;
import ru.sfedu.hiber.lab4.set.model.Category4Set;
import ru.sfedu.hiber.lab4.set.model.Product4Set;
import ru.sfedu.hiber.lab5.model.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


public class HibernateUtil {
    private final static Logger LOG =  LogManager.getLogger(HibernateUtil.class);
    private static final String HIBERNATE_CONFIG_PATH = System.getProperty("hiber");
    private static SessionFactory sessionFactory;
    /**
     * Создание фабрики
     *
     */
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            File configFile = HIBERNATE_CONFIG_PATH == null ? null : new File(HIBERNATE_CONFIG_PATH);
            // loads configuration and mappings
            Configuration configuration = new Configuration();
            if (configFile != null) {
                try {
                    configuration.configure(configFile);

                } catch(Exception ex){
                    LOG.error("Error loading hibernate.cfg: ", ex);
                    configuration.configure();
                }
            } else {
                configuration.configure();
            }

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            MetadataSources metadataSources = new MetadataSources(serviceRegistry);

            // lab 2
            metadataSources.addAnnotatedClass(OtherEntity.class);
            metadataSources.addAnnotatedClass(TestEntity.class);

            // lab 3
            metadataSources.addAnnotatedClass(CreditAccount.class); // Аннотированная сущность
            metadataSources.addAnnotatedClass(DebitAccount.class);
            metadataSources.addAnnotatedClass(Account.class);
            metadataSources.addAnnotatedClass(Account1.class);
            metadataSources.addAnnotatedClass(CreditAccount1.class);
            metadataSources.addAnnotatedClass(DebitAccount1.class);
            metadataSources.addAnnotatedClass(Account2.class);
            metadataSources.addAnnotatedClass(CreditAccount2.class);
            metadataSources.addAnnotatedClass(DebitAccount2.class);
            metadataSources.addAnnotatedClass(Account3.class);
            metadataSources.addAnnotatedClass(CreditAccount3.class);
            metadataSources.addAnnotatedClass(DebitAccount3.class);

            // lab 4
            metadataSources.addAnnotatedClass(Category4Set.class);
            metadataSources.addAnnotatedClass(Product4Set.class);
            metadataSources.addAnnotatedClass(Category4List.class);
            metadataSources.addAnnotatedClass(Product4List.class);
            metadataSources.addAnnotatedClass(Category4Map.class);
            metadataSources.addAnnotatedClass(Product4Map.class);
            metadataSources.addAnnotatedClass(Category4Component.class);
            metadataSources.addAnnotatedClass(Product4Component.class);
            metadataSources.addAnnotatedClass(Category4MapComponent.class);
            metadataSources.addAnnotatedClass(Product4MapComponent.class);

            // lab 5
            metadataSources.addAnnotatedClass(Bucket.class);
            metadataSources.addAnnotatedClass(Category.class);
            metadataSources.addAnnotatedClass(Receipt.class);
            metadataSources.addAnnotatedClass(Product.class);
            metadataSources.addAnnotatedClass(Fridge.class);
            metadataSources.addAnnotatedClass(Computer.class);
            metadataSources.addAnnotatedClass(Soda.class);

            //metadataSources.addResource("named-queries.hbm.xml");// Именованные запросы
            sessionFactory = metadataSources.buildMetadata().buildSessionFactory();
        }
        return sessionFactory;
    }

    public static <R> Optional<R> runSessionQuerySafe(ExFunction<Session, Optional<R>> func) {
        try (Session session = getSessionFactory().openSession()) {
            return func.apply(session);
        } catch (Exception ex) {
            LOG.error("Error running session request!", ex);
            return Optional.empty();
        }
    }


    public static boolean runTransactionSafe(ExConsumer<Session> func) {
        Transaction transaction = null;
        try (Session session = getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            func.accept(session);
            transaction.commit();
            return true;
        } catch (Exception ex) {
            LOG.error("Error running session request!", ex);
            if (transaction != null) {
                transaction.rollback();
            }
            return false;
        }
    }


    public static <T> List<T> getAll(Class<T> type) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteria = builder.createQuery(type);
            criteria.from(type);
            List<T> data = session.createQuery(criteria).getResultList();
            return data;
        }
    }


    public static <T> Optional<T> getById(Class<T> type, Serializable id) {
        T item = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            item = (T) session.load(type, id);
            Hibernate.initialize(item);
        } catch (Exception ex) {
            LOG.error("Error getting object of class {} with id {} : ", type, id, ex);
            return Optional.empty();
        }
        return Optional.of(item);
    }

    public static List<Computer> getAllComputerHql() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            final String queryString = "FROM " + Constants.COMPUTER_ENTITY;
            Query query = session.createQuery(queryString);
            List<Computer> data = query.getResultList();
            return data;
        }
    }

    public static List<Computer> getAllComputeSql() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            final String queryString = "select p.id, name, weight, price, processorname, processorpower, graphicsname, graphicsvolume, integratedbluetooth, integratedwifi from " + Constants.COMPUTER + " as c inner join " + Constants.PRODUCT + " as p on p.id = c.id";
            NativeQuery<Computer> sqlQuery = session.createSQLQuery(queryString);
            List<Computer> computers = sqlQuery.getResultList();
            return computers;
        }
    }

    public static List<Computer> getAllComputerCriteria() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Computer> criteria = builder.createQuery(Computer.class);
            criteria.from(Computer.class);
            List<Computer> data = session.createQuery(criteria).getResultList();
            return data;
        }
    }

}


