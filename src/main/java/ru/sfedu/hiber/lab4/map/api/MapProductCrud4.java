package ru.sfedu.hiber.lab4.map.api;

import ru.sfedu.hiber.lab4.api.IProviderBaseCrud;
import ru.sfedu.hiber.lab4.map.model.Product4Map;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.Optional;

public class MapProductCrud4 implements IProviderBaseCrud<Product4Map, Long> {

    @Override
    public Optional<Product4Map> get(Long id) {
        return HibernateUtil.runSessionQuerySafe(session -> Optional.ofNullable(session.get(Product4Map.class, id)));
    }

    @Override
    public boolean add(Product4Map model) {
        return HibernateUtil.runTransactionSafe(session -> session.save(model));
    }

    @Override
    public boolean delete(Product4Map model) {
        return HibernateUtil.runTransactionSafe(session -> session.remove(model));
    }

    @Override
    public boolean update(Product4Map model) {
        return HibernateUtil.runTransactionSafe(session -> session.update(model));
    }
}