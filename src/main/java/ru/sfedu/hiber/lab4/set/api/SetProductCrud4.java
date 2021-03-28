package ru.sfedu.hiber.lab4.set.api;

import ru.sfedu.hiber.lab4.api.IProviderBaseCrud;
import ru.sfedu.hiber.lab4.set.model.Product4Set;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.Optional;

public class SetProductCrud4 implements IProviderBaseCrud<Product4Set, Long> {

    @Override
    public Optional<Product4Set> get(Long id) {
        return HibernateUtil.runSessionQuerySafe(session -> Optional.ofNullable(session.get(Product4Set.class, id)));
    }

    @Override
    public boolean add(Product4Set model) {
        return HibernateUtil.runTransactionSafe(session -> session.save(model));
    }

    @Override
    public boolean delete(Product4Set model) {
        return HibernateUtil.runTransactionSafe(session -> session.remove(model));
    }

    @Override
    public boolean update(Product4Set model) {
        return HibernateUtil.runTransactionSafe(session -> session.update(model));
    }
}