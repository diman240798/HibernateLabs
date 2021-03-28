package ru.sfedu.hiber.lab4.component.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.hiber.lab4.component.model.Category4Component;
import ru.sfedu.hiber.lab4.component.model.Product4Component;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.HashSet;
import java.util.Optional;

public class ComponentProductCrud4Test {

    @Before
    public void drop() {
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM four_component_category").executeUpdate()));
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM four_component_product").executeUpdate()));
    }

    @Test
    public void get() {
        Category4Component soda = createCategory();
        Product4Component model = createProduct(soda);

        ComponentProductCrud4 crud = new ComponentProductCrud4();
        crud.add(model);
        Optional<Product4Component> modelDbOption = crud.get(model.getId());
        Assert.assertTrue(modelDbOption.isPresent());
        Product4Component modelDb = modelDbOption.get();
        Assert.assertEquals(model, modelDb);
    }

    private Product4Component createProduct(Category4Component soda) {
        HashSet<Category4Component> set = new HashSet<>();
        Product4Component model = new Product4Component(0, "Fanta", 1.5, 105, set);
        set.add(soda);
        return model;
    }

    private Category4Component createCategory() {
        return new Category4Component(2, "Soda");
    }

    @Test
    public void add() {
        Category4Component soda = createCategory();
        Product4Component model = createProduct(soda);
        ComponentProductCrud4 crud = new ComponentProductCrud4();
        Assert.assertTrue(crud.add(model));
    }

    @Test
    public void delete() {
        Category4Component soda = createCategory();
        Product4Component model = createProduct(soda);
        ComponentProductCrud4 crud = new ComponentProductCrud4();
        crud.add(model);
        crud.delete(model);
        Assert.assertFalse(crud.get(model.getId()).isPresent());
    }

    @Test
    public void update() {
        Category4Component soda = createCategory();
        Product4Component model = createProduct(soda);

        ComponentProductCrud4 crud = new ComponentProductCrud4();
        crud.add(model);
        Assert.assertTrue(crud.get(model.getId()).isPresent());
        model.setName("Waters");
        Assert.assertTrue(crud.update(model));
        Assert.assertEquals(model.getName(), crud.get(model.getId()).get().getName());
    }
}