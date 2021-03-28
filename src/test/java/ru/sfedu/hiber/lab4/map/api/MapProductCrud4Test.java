package ru.sfedu.hiber.lab4.map.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.hiber.lab4.map.model.Category4Map;
import ru.sfedu.hiber.lab4.map.model.Product4Map;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MapProductCrud4Test {

    @Before
    public void drop() {
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM  four_map_category").executeUpdate()));
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM four_map_product").executeUpdate()));
    }

    @Test
    public void get() {
        Category4Map soda = createCategory();
        Product4Map model = createProduct(soda);

        MapProductCrud4 crud = new MapProductCrud4();
        crud.add(model);
        Optional<Product4Map> modelDbOption = crud.get(model.getId());
        Assert.assertTrue(modelDbOption.isPresent());
        Product4Map modelDb = modelDbOption.get();
        Assert.assertEquals(model, modelDb);
    }

    private Product4Map createProduct(Category4Map soda) {
        Map<String, String> set = new HashMap<>();
        Product4Map model = new Product4Map(0, "Fanta", 1.5, 105, set);
        set.put(model.getName(), soda.getName());
        return model;
    }

    private Category4Map createCategory() {
        return new Category4Map("Soda", 0);
    }

    @Test
    public void add() {
        Category4Map soda = createCategory();
        Product4Map model = createProduct(soda);
        MapProductCrud4 crud = new MapProductCrud4();
        Assert.assertTrue(crud.add(model));
    }

    @Test
    public void delete() {
        Category4Map soda = createCategory();
        Product4Map model = createProduct(soda);
        MapProductCrud4 crud = new MapProductCrud4();
        crud.add(model);
        crud.delete(model);
        Assert.assertFalse(crud.get(model.getId()).isPresent());
    }

    @Test
    public void update() {
        Category4Map soda = createCategory();
        Product4Map model = createProduct(soda);

        MapProductCrud4 crud = new MapProductCrud4();
        crud.add(model);
        Assert.assertTrue(crud.get(model.getId()).isPresent());
        model.setName("Waters");
        Assert.assertTrue(crud.update(model));
        Assert.assertEquals(model.getName(), crud.get(model.getId()).get().getName());
    }
}