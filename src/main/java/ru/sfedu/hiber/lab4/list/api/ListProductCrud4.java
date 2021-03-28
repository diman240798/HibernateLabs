package ru.sfedu.hiber.lab4.list.api;

import ru.sfedu.hiber.lab4.api.IProviderBaseCrud;
import ru.sfedu.hiber.lab4.list.model.Product4List;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.Optional;

public class ListProductCrud4 implements IProviderBaseCrud<Product4List, Long> {

    @Override
    public Optional<Product4List> get(Long id) {
        return HibernateUtil.runSessionQuerySafe(session -> Optional.ofNullable(session.get(Product4List.class, id)));
    }

    @Override
    public boolean add(Product4List model) {
        return HibernateUtil.runTransactionSafe(session -> session.save(model));
    }

    @Override
    public boolean delete(Product4List model) {
        return HibernateUtil.runTransactionSafe(session -> session.remove(model));
    }

    @Override
    public boolean update(Product4List model) {
        return HibernateUtil.runTransactionSafe(session -> session.update(model));
    }
}
