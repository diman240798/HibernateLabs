package ru.sfedu.hiber.lab4.map_component.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.hiber.lab4.map_component.model.Category4MapComponent;
import ru.sfedu.hiber.lab4.map_component.model.Product4MapComponent;
import ru.sfedu.hiber.utils.HibernateUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class MapComponentProductCrud4Test {

    @Before
    public void drop() {
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM four_map_component_category").executeUpdate()));
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM four_map_component_product").executeUpdate()));
    }

    @Test
    public void get() {
        Category4MapComponent soda = createCategory();
        Product4MapComponent model = createProduct(soda);

        MapComponentProductCrud4 crud = new MapComponentProductCrud4();
        crud.add(model);
        Optional<Product4MapComponent> modelDbOption = crud.get(model.getId());
        Assert.assertTrue(modelDbOption.isPresent());
        Product4MapComponent modelDb = modelDbOption.get();
        Assert.assertEquals(model, modelDb);
    }

    private Product4MapComponent createProduct(Category4MapComponent soda) {
        Map<String, Category4MapComponent> set = new HashMap<>();
        String prodName = "Fanta";
        set.put(prodName, soda);
        Product4MapComponent model = new Product4MapComponent(0, prodName, 1.5, 105, set);
        return model;
    }

    private Category4MapComponent createCategory() {
        return new Category4MapComponent(1, 2321, "Soda");
    }

    @Test
    public void add() {
        Category4MapComponent soda = createCategory();
        Product4MapComponent model = createProduct(soda);
        MapComponentProductCrud4 crud = new MapComponentProductCrud4();
        Assert.assertTrue(crud.add(model));
    }

    @Test
    public void delete() {
        Category4MapComponent soda = createCategory();
        Product4MapComponent model = createProduct(soda);
        MapComponentProductCrud4 crud = new MapComponentProductCrud4();
        crud.add(model);
        crud.delete(model);
        Assert.assertFalse(crud.get(model.getId()).isPresent());
    }

    @Test
    public void update() {
        Category4MapComponent soda = createCategory();
        Product4MapComponent model = createProduct(soda);

        MapComponentProductCrud4 crud = new MapComponentProductCrud4();
        crud.add(model);
        Assert.assertTrue(crud.get(model.getId()).isPresent());
        model.setName("Waters");
        Assert.assertTrue(crud.update(model));
        Assert.assertEquals(model.getName(), crud.get(model.getId()).get().getName());
    }
}