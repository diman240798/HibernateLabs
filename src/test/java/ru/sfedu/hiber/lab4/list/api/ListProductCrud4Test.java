package ru.sfedu.hiber.lab4.list.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.hiber.lab4.list.model.Category4List;
import ru.sfedu.hiber.lab4.list.model.Product4List;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListProductCrud4Test {

    @Before
    public void drop() {
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM  four_list_category").executeUpdate()));
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM four_list_product").executeUpdate()));
    }

    @Test
    public void get() {
        Category4List soda = createCategory();
        Product4List model = createProduct(soda);

        ListProductCrud4 crud = new ListProductCrud4();
        crud.add(model);
        Optional<Product4List> modelDbOption = crud.get(model.getId());
        Assert.assertTrue(modelDbOption.isPresent());
        Product4List modelDb = modelDbOption.get();
        Assert.assertEquals(model, modelDb);
    }

    private Product4List createProduct(Category4List soda) {
        List<String> set = new ArrayList<>();
        Product4List model = new Product4List(0, "Fanta", 1.5, 105, set);
        set.add(soda.getName());
        return model;
    }

    private Category4List createCategory() {
        return new Category4List("Soda", 0L);
    }

    @Test
    public void add() {
        Category4List soda = createCategory();
        Product4List model = createProduct(soda);
        ListProductCrud4 crud = new ListProductCrud4();
        Assert.assertTrue(crud.add(model));
    }

    @Test
    public void delete() {
        Category4List soda = createCategory();
        Product4List model = createProduct(soda);
        ListProductCrud4 crud = new ListProductCrud4();
        crud.add(model);
        crud.delete(model);
        Assert.assertFalse(crud.get(model.getId()).isPresent());
    }

    @Test
    public void update() {
        Category4List soda = createCategory();
        Product4List model = createProduct(soda);

        ListProductCrud4 crud = new ListProductCrud4();
        crud.add(model);
        Assert.assertTrue(crud.get(model.getId()).isPresent());
        model.setName("Waters");
        Assert.assertTrue(crud.update(model));
        Assert.assertEquals(model.getName(), crud.get(model.getId()).get().getName());
    }
}