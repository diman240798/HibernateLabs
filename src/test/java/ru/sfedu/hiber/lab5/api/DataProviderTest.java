package ru.sfedu.hiber.lab5.api;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.sfedu.hiber.Constants;
import ru.sfedu.hiber.lab5.api.helper.InitializerData;
import ru.sfedu.hiber.lab5.model.*;

import java.util.*;

public class DataProviderTest {

    private final static Logger LOG = LogManager.getLogger(DataProviderTest.class);


    private DataProvider dbProvider = new DataProviderHibernate();

    @Before
    public void beforeEach() {
        dbProvider.deleteAll();
    }

    @Test
    public void init() throws Exception {
        dbProvider.init();
        Assert.assertTrue(Objects.deepEquals(dbProvider.getAllCategory(), InitializerData.CATEGORIES));
        Assert.assertTrue(Objects.deepEquals(dbProvider.getAllComputer(), InitializerData.COMPUTERS));
        Assert.assertTrue(Objects.deepEquals(dbProvider.getAllSoda(), InitializerData.SODA));
        Assert.assertTrue(Objects.deepEquals(dbProvider.getAllFridge(), InitializerData.FRIDGES));

    }

    @Test
    public void initBad() throws Exception {
        dbProvider.init();

        Assert.assertFalse(Objects.deepEquals(dbProvider.getAllCategory(), Collections.emptyList()));
        Assert.assertFalse(Objects.deepEquals(dbProvider.getAllComputer(), Arrays.asList(null, null, null, null)));
        Assert.assertFalse(Objects.deepEquals(dbProvider.getAllSoda(), Collections.emptyList()));
        Assert.assertFalse(Objects.deepEquals(dbProvider.getAllFridge(), Collections.singletonList(null)));

    }

    @Test
    public void getCategory() throws Exception {
        dbProvider.init();
        Category model = InitializerData.CATEGORIES.get(0);
        Object modelDb = dbProvider.getCategory(model.getName()).get();
        Assert.assertEquals(model, modelDb);
    }

    @Test
    public void getCategoryBad() throws Exception {
        Optional<Category> model = dbProvider.getCategory("CATS");
        Assert.assertFalse(model.isPresent());
    }

    @Test
    public void getComputer() throws Exception {
        dbProvider.init();
        Computer model = InitializerData.COMPUTERS.get(0);
        dbProvider.insertComputer(model);
        Object modelDb = dbProvider.getComputer(model.getId()).get();
        Assert.assertEquals(model, modelDb);
    }

    @Test
    public void getComputerBad() throws Exception {
        Optional<Computer> model = dbProvider.getComputer(0);
        Assert.assertFalse(model.isPresent());
    }

    @Test
    public void getFridge() throws Exception {
        dbProvider.init();
        Fridge model = InitializerData.FRIDGES.get(0);
        dbProvider.insertFridge(model);
        Object modelDb = dbProvider.getFridge(model.getId()).get();
        Assert.assertEquals(model, modelDb);
    }

    @Test
    public void getFridgeBad() throws Exception {
        Optional<Fridge> model = dbProvider.getFridge(0);
        Assert.assertFalse(model.isPresent());
    }

    @Test
    public void getReceipt() throws Exception {
        dbProvider.init();
        Receipt model = new Receipt(21321423, new ArrayList<>(InitializerData.FRIDGES), 4344);
        dbProvider.insertReceipt(model);
        Object modelDb = dbProvider.getReceipt(model.getId()).get();
        Assert.assertEquals(model, modelDb);
    }

    @Test
    public void getReceiptBad() throws Exception {
        Optional<Receipt> model = dbProvider.getReceipt(0);
        Assert.assertFalse(model.isPresent());
    }

    @Test
    public void getSoda() throws Exception {
        dbProvider.init();
        Soda model = InitializerData.SODA.get(0);
        dbProvider.insertSoda(model);
        Object modelDb = dbProvider.getSoda(model.getId()).get();
        Assert.assertEquals(model, modelDb);
    }

    @Test
    public void getSodaBad() throws Exception {
        Optional<Soda> model = dbProvider.getSoda(0);
        Assert.assertFalse(model.isPresent());
    }

    @Test
    public void insertComputer() throws Exception {
        dbProvider.init();
        Assert.assertTrue(dbProvider.insertComputer("110 Hp_pavillion 2.4 64300 intel_i5 600 geforge_gtx_1060 2 true true"));
        Computer modelDb = dbProvider.getComputer(110).get();
        Assert.assertEquals("Hp_pavillion", modelDb.getName());
    }

    @Test
    public void insertComputerBad() throws Exception {
        Assert.assertFalse(dbProvider.insertComputer(""));
    }

    @Test
    public void insertSoda() throws Exception {
        dbProvider.init();
        dbProvider.insertSoda("110 tasty_fanta 1.5 300 orange");
        Soda modelDb = dbProvider.getSoda(110).get();
        Assert.assertEquals("tasty_fanta", modelDb.getName());
    }

    @Test
    public void insertSodaBad() throws Exception {
        Assert.assertFalse(dbProvider.insertSoda(""));
    }

    @Test
    public void insertFridge() throws Exception {
        dbProvider.init();
        dbProvider.insertFridge("110 Toshiba_x_32 55.4 64300 80 white 900 true");
        Fridge modelDb = dbProvider.getFridge(110).get();
        Assert.assertEquals("Toshiba_x_32", modelDb.getName());
    }

    @Test
    public void insertFridgeBad() throws Exception {
        Assert.assertFalse(dbProvider.insertFridge(""));
    }

    @Test
    public void closeBucket() throws Exception {
        dbProvider.init();
        Fridge product = InitializerData.FRIDGES.get(0);
        Bucket bucket = dbProvider.addProduct(product.getId(), Constants.FRIDGE, Optional.empty()).get();
        boolean closeBucket = dbProvider.closeBucket(bucket.getId());
        Assert.assertTrue(closeBucket);
    }

    @Test
    public void closeBucketBad() throws Exception {
        boolean finishSession = dbProvider.closeBucket(UUID.randomUUID().toString());
        Assert.assertFalse(finishSession);
    }

    @Test
    public void addProduct() throws Exception {
        dbProvider.init();
        Fridge fridge = InitializerData.FRIDGES.get(0);
        Assert.assertTrue(dbProvider.addProduct(fridge.getId(), Constants.FRIDGE, Optional.empty()).isPresent());
    }

    @Test
    public void addProductBad() throws Exception {
        Assert.assertFalse(dbProvider.addProduct(1, Constants.FRIDGE, Optional.empty()).isPresent());
    }

    @Test
    public void getBucket() throws Exception {
        dbProvider.init();
        Bucket model = new Bucket(UUID.randomUUID().toString(), new ArrayList<>(InitializerData.FRIDGES));
        Assert.assertTrue(dbProvider.insertBucket(model));
        Bucket modelDb = dbProvider.getBucketById(model.getId()).get();
        Assert.assertEquals(model, modelDb);
    }

    @Test
    public void deleteBucketBad() throws Exception {
        Bucket bucket = new Bucket(UUID.randomUUID().toString());
        Assert.assertTrue(dbProvider.deleteBucket(bucket));
    }

    @Test
    public void compare() throws Exception {
        dbProvider.init();

        long time_sql = 0;
        final int times = 100;
        for (int i = 0; i < times; i++) {
            long start = System.currentTimeMillis();
            final List<Computer> computersSql = dbProvider.getComputersSql();
            long end = System.currentTimeMillis();
            time_sql += end - start;
        }

        long time_hql = 0;
        for (int i = 0; i < times; i++) {
            long start = System.currentTimeMillis();
            final List<Computer> computersHql = dbProvider.getComputersHql();
            long end = System.currentTimeMillis();
            time_hql += end - start;
        }

        long time_criteria = 0;
        for (int i = 0; i < times; i++) {
            long start = System.currentTimeMillis();
            final List<Computer> computersHql = dbProvider.getComputersCriteria();
            long end = System.currentTimeMillis();
            time_criteria += end - start;
        }

        LOG.info("Sql {}, Hql {}, Criteria {}", time_sql, time_hql, time_criteria);
    }
}