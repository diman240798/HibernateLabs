package ru.sfedu.hiber.lab4.component.api;

import ru.sfedu.hiber.lab4.api.IProviderBaseCrud;
import ru.sfedu.hiber.lab4.component.model.Product4Component;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.Optional;

public class ComponentProductCrud4 implements IProviderBaseCrud<Product4Component, Long> {

    @Override
    public Optional<Product4Component> get(Long id) {
        return HibernateUtil.runSessionQuerySafe(session -> Optional.ofNullable(session.get(Product4Component.class, id)));
    }

    @Override
    public boolean add(Product4Component model) {
        return HibernateUtil.runTransactionSafe(session -> session.save(model));
    }

    @Override
    public boolean delete(Product4Component model) {
        return HibernateUtil.runTransactionSafe(session -> session.remove(model));
    }

    @Override
    public boolean update(Product4Component model) {
        return HibernateUtil.runTransactionSafe(session -> session.update(model));
    }
}