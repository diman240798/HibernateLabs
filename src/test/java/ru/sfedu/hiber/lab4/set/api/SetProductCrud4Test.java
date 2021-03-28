package ru.sfedu.hiber.lab4.set.api;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.hiber.lab4.set.model.Category4Set;
import ru.sfedu.hiber.lab4.set.model.Product4Set;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.util.HashSet;
import java.util.Optional;

public class SetProductCrud4Test {

    @Before
    public void drop() {
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM  four_set_category").executeUpdate()));
        HibernateUtil.runTransactionSafe(session -> Optional.of(session.createSQLQuery("DELETE FROM four_set_product").executeUpdate()));
    }

    @Test
    public void get() {
        Category4Set soda = createCategory();
        Product4Set model = createProduct(soda);

        SetProductCrud4 crud = new SetProductCrud4();
        crud.add(model);
        Optional<Product4Set> modelDbOption = crud.get(model.getId());
        Assert.assertTrue(modelDbOption.isPresent());
        Product4Set modelDb = modelDbOption.get();
        Assert.assertEquals(model, modelDb);
    }

    private Product4Set createProduct(Category4Set soda) {
        HashSet<String> set = new HashSet<>();
        Product4Set model = new Product4Set(0, "Fanta", 1.5, 105, set);
        set.add(soda.getName());
        return model;
    }

    private Category4Set createCategory() {
        return new Category4Set("Soda", 0);
    }

    @Test
    public void add() {
        Category4Set soda = createCategory();
        Product4Set model = createProduct(soda);
        SetProductCrud4 crud = new SetProductCrud4();
        Assert.assertTrue(crud.add(model));
    }

    @Test
    public void delete() {
        Category4Set soda = createCategory();
        Product4Set model = createProduct(soda);
        SetProductCrud4 crud = new SetProductCrud4();
        crud.add(model);
        crud.delete(model);
        Assert.assertFalse(crud.get(model.getId()).isPresent());
    }

    @Test
    public void update() {
        Category4Set soda = createCategory();
        Product4Set model = createProduct(soda);

        SetProductCrud4 crud = new SetProductCrud4();
        crud.add(model);
        Assert.assertTrue(crud.get(model.getId()).isPresent());
        model.setName("Waters");
        Assert.assertTrue(crud.update(model));
        Assert.assertEquals(model.getName(), crud.get(model.getId()).get().getName());
    }
}