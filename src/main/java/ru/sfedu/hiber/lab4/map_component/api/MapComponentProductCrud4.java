package ru.sfedu.hiber.lab4.map_component.api;

import ru.sfedu.hiber.lab4.api.IProviderBaseCrud;
import ru.sfedu.hiber.lab4.map_component.model.Product4MapComponent;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.Optional;

public class MapComponentProductCrud4 implements IProviderBaseCrud<Product4MapComponent, Long> {

    @Override
    public Optional<Product4MapComponent> get(Long id) {
        return HibernateUtil.runSessionQuerySafe(session -> Optional.ofNullable(session.get(Product4MapComponent.class, id)));
    }

    @Override
    public boolean add(Product4MapComponent model) {
        return HibernateUtil.runTransactionSafe(session -> session.save(model));
    }

    @Override
    public boolean delete(Product4MapComponent model) {
        return HibernateUtil.runTransactionSafe(session -> session.remove(model));
    }

    @Override
    public boolean update(Product4MapComponent model) {
        return HibernateUtil.runTransactionSafe(session -> session.update(model));
    }
}