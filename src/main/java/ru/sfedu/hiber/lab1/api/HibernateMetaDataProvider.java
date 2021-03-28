package ru.sfedu.hiber.lab1.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import ru.sfedu.hiber.Constants;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class HibernateMetaDataProvider implements IMetaDataProvider {

    private final static Logger LOG = LogManager.getLogger(HibernateMetaDataProvider.class);

    public HibernateMetaDataProvider() throws IOException {}

    @Override
    public Optional<List<String>> getListSchemas(){
        Optional<List<String>> querySafe = HibernateUtil.<List<String>>runSessionQuerySafe(session -> {
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_SCHEMAS);
            return Optional.ofNullable(query.getResultList());
        });
        
        if (querySafe.isPresent()) {
            List<String> resList = querySafe.get();
            LOG.info("Get schemas {}", String.join(",", resList));
            LOG.info("Get schemas size {}", resList);
        }
        return querySafe;
    }

    @Override
    public Optional<List<String>> getListTables(){
        Optional<List<String>> querySafe = HibernateUtil.<List<String>>runSessionQuerySafe(session -> {
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_TABLES);
            return Optional.ofNullable(query.getResultList());
        });

        if (querySafe.isPresent()) {
            List<String> resList = querySafe.get();
            LOG.info("Get tables size {}", resList);
        }
        return querySafe;
    }

    @Override
    public Optional<List<String>> getListTablesType(){
        Optional<List<String>> querySafe = HibernateUtil.<List<String>>runSessionQuerySafe(session -> {
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_TABLES_TYPE);
            return Optional.ofNullable(query.getResultList());
        });

        if (querySafe.isPresent()) {
            List<String> resList = querySafe.get();
            LOG.info("Get tables type size {}", resList);
        }
        return querySafe;
    }

    @Override
    public Optional<List<String>> getListRoleTablesGrants(){
        Optional<List<String>> querySafe = HibernateUtil.<List<String>>runSessionQuerySafe(session -> {
            NativeQuery query = session.createSQLQuery(Constants.SQL_ALL_ROLE_TABLES_GRANTS);
            return Optional.ofNullable(query.getResultList());
        });

        if (querySafe.isPresent()) {
            List<String> resList = querySafe.get();
            LOG.info("Get role tables grants size {}", resList);
        }
        return querySafe;
    }
}
