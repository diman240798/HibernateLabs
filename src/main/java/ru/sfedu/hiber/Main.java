package ru.sfedu.hiber;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.hiber.lab1.api.HibernateMetaDataProvider;
import ru.sfedu.hiber.lab2.api.TestEntityProvider;
import ru.sfedu.hiber.lab2.model.OtherEntity;
import ru.sfedu.hiber.lab2.model.TestEntity;
import ru.sfedu.hiber.lab3.strategy1.api.Strategy1Provider;
import ru.sfedu.hiber.lab3.strategy1.model.CreditAccount;
import ru.sfedu.hiber.lab3.strategy1.model.DebitAccount;
import ru.sfedu.hiber.lab3.strategy2.api.Strategy2Provider;
import ru.sfedu.hiber.lab3.strategy2.model.CreditAccount1;
import ru.sfedu.hiber.lab3.strategy2.model.DebitAccount1;
import ru.sfedu.hiber.lab3.strategy3.api.Strategy3Provider;
import ru.sfedu.hiber.lab3.strategy3.model.CreditAccount2;
import ru.sfedu.hiber.lab3.strategy3.model.DebitAccount2;
import ru.sfedu.hiber.lab3.strategy4.api.Strategy4Provider;
import ru.sfedu.hiber.lab3.strategy4.model.CreditAccount3;
import ru.sfedu.hiber.lab3.strategy4.model.DebitAccount3;
import ru.sfedu.hiber.lab4.api.IProviderBaseCrud;
import ru.sfedu.hiber.lab4.component.api.ComponentProductCrud4;
import ru.sfedu.hiber.lab4.component.model.Category4Component;
import ru.sfedu.hiber.lab4.component.model.Product4Component;
import ru.sfedu.hiber.lab4.list.api.ListProductCrud4;
import ru.sfedu.hiber.lab4.list.model.Category4List;
import ru.sfedu.hiber.lab4.list.model.Product4List;
import ru.sfedu.hiber.lab4.map.api.MapProductCrud4;
import ru.sfedu.hiber.lab4.map.model.Category4Map;
import ru.sfedu.hiber.lab4.map.model.Product4Map;
import ru.sfedu.hiber.lab4.map_component.api.MapComponentProductCrud4;
import ru.sfedu.hiber.lab4.map_component.model.Category4MapComponent;
import ru.sfedu.hiber.lab4.map_component.model.Product4MapComponent;
import ru.sfedu.hiber.lab4.set.api.SetProductCrud4;
import ru.sfedu.hiber.lab4.set.model.Category4Set;
import ru.sfedu.hiber.lab4.set.model.Product4Set;
import ru.sfedu.hiber.lab5.api.DataProvider;
import ru.sfedu.hiber.lab5.api.DataProviderHibernate;
import ru.sfedu.hiber.lab5.model.Computer;
import ru.sfedu.hiber.lab5.model.Fridge;
import ru.sfedu.hiber.lab5.model.Receipt;
import ru.sfedu.hiber.lab5.model.Soda;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class Main {
    private final static Logger LOG = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
//        args = new String[] {"get_all_receipt"};
        LOG.info("Init");
        DataProviderHibernate dataProvider = new DataProviderHibernate();
        LOG.info("DataProvider created");

        String arg = args[0];
        if (arg.equals(Constants.LAB)) {
            LOG.debug("Called lab method");
            String labResult = resolveLabResult(args);
            LOG.info("Lab result:\n{}", labResult);
            return;
        }


        String apiResult = resolveAPIResult(dataProvider, args);
        LOG.info("Api result:\n {}", apiResult);
    }


    /**
     * Вызов метода Лабораторной работы согласно параметрам
     *
     * @return String - Constants.BAD_ARGS, Constants.FAILURE, Constants.SUCCESS
     */
    private static String resolveLabResult(String[] args) {
        try {
            int labNumber = Integer.parseInt(args[1]);
            switch (labNumber) {
                case 1:
                    resolveLab1(args);
                    break;
                case 2:
                    resolveLab2(args);
                    break;
                case 3:
                    resolveLab3(args);
                    break;
                case 4:
                    resolveLab4(args);
                    break;
                case 5:
                    throw new RuntimeException("Incorrect lab number: " + labNumber);
            }
        } catch (Exception ex) {
            LOG.error("Lab execute error!", ex);
            return Constants.FAILURE;
        }

        return Constants.SUCCESS;
    }


    private static void resolveLab1(String[] args) throws IOException {
        HibernateMetaDataProvider provider = new HibernateMetaDataProvider();
        String method = args[2];
        try {
            switch (method) {
                case Constants.LAB_1_GET_SCHEMAS:
                    provider.getListSchemas();
                    break;
                case Constants.LAB_1_GET_TABLES:
                    provider.getListTables();
                    break;
                case Constants.LAB_1_GET_ROLE_TABLES:
                    provider.getListRoleTablesGrants();
                    break;
                case Constants.LAB_1_GET_TABLES_TYPE:
                    provider.getListTablesType();
                    break;
            }
        } catch (Exception e) {
            LOG.error("LAB 1 error: ", e);
        }
    }

    private static void resolveLab2(String[] args) {
        TestEntityProvider provider = new TestEntityProvider();
        String method = args[2];

        try {
            long id;
            switch (method) {
                case Constants.LAB_2_SAVE:
                    TestEntity entity = new TestEntity();
                    OtherEntity other = new OtherEntity();
                    entity.setName(args[3]);
                    entity.setCheck(true);
                    entity.setDateCreated(new Date());
                    entity.setDescription(args[4]);
                    other.setCount(Double.parseDouble(args[5]));
                    other.setComplicationDate(new Date());
                    other.setOther(args[6]);
                    entity.setOtherEntity(other);
                    provider.save(entity);
                    break;
                case Constants.LAB_2_GET_BY_ID:
                    id = Long.parseLong(args[3]);
                    Optional<TestEntity> entityOption = provider.getById(TestEntity.class, id);
                    if (entityOption.isPresent()) {
                        LOG.info(entityOption.get());
                    }
                    break;
                case Constants.LAB_2_UPDATE:
                    id = Long.parseLong(args[3]);
                    String name = args[4];
                    provider.update(TestEntity.class, id, name);
                    break;
                case Constants.LAB_2_DELETE:
                    id = Long.parseLong(args[3]);
                    provider.delete(TestEntity.class, id);
                    break;
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }

    private static void resolveLab3(String[] args) throws IOException {
        switch (args[2]) {
            case Constants.LAB_3_MAPPED:
                chooseLab3Strategy1(args);
                break;
            case Constants.LAB_3_TABLE_PER_CLASS:
                chooseLab3Strategy2(args);
                break;
            case Constants.LAB_3_SINGLE_TABLE:
                chooseLab3Strategy3(args);
                break;
            case Constants.LAB_3_JOINED:
                chooseLab3Strategy4(args);
                break;
        }
    }

    private static void chooseLab3Strategy1(String[] args) {
        Strategy1Provider provider = new Strategy1Provider();
        try {
            long id;
            switch (args[3]) {
                case Constants.LAB_3_SAVE:
                    CreditAccount creditAccount = new CreditAccount();
                    DebitAccount debitAccount = new DebitAccount();
                    creditAccount.setOwner(args[4]);
                    creditAccount.setCreditLimit(new BigDecimal(args[5]));
                    creditAccount.setBalance(new BigDecimal(args[6]));
                    creditAccount.setInterestRate(new BigDecimal(args[7]));
                    debitAccount.setOwner(args[4]);
                    debitAccount.setBalance(new BigDecimal(args[5]));
                    debitAccount.setInterestRate(new BigDecimal(args[7]));
                    debitAccount.setOverdraftFee(new BigDecimal(args[8]));
                    provider.save(creditAccount, debitAccount);
                    break;
                case Constants.LAB_3_GET_BY_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.getByTypeAccount(CreditAccount.class, id).get());
                    break;
                case Constants.LAB_3_UPDATE_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    provider.updateTypeAccount(CreditAccount.class, id, name);
                    break;
                case Constants.LAB_3_DELETE_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    provider.deleteTypeAccount(CreditAccount.class, id);
                    break;
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }


    private static void chooseLab3Strategy2(String[] args) throws IOException {
        Strategy2Provider provider = new Strategy2Provider();
        try {
            long id;
            switch (args[3]) {
                case Constants.LAB_3_SAVE:
                    CreditAccount1 creditAccount = new CreditAccount1();
                    DebitAccount1 debitAccount = new DebitAccount1();
                    creditAccount.setOwner(args[4]);
                    creditAccount.setCreditLimit(new BigDecimal(args[5]));
                    creditAccount.setBalance(new BigDecimal(args[6]));
                    creditAccount.setInterestRate(new BigDecimal(args[7]));
                    debitAccount.setOwner(args[4]);
                    debitAccount.setBalance(new BigDecimal(args[5]));
                    debitAccount.setInterestRate(new BigDecimal(args[7]));
                    debitAccount.setOverdraftFee(new BigDecimal(args[8]));
                    provider.save(creditAccount, debitAccount);
                    break;
                case Constants.LAB_3_GET_BY_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.getByTypeAccount(CreditAccount1.class, id).get());
                    break;
                case Constants.LAB_3_UPDATE_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    provider.updateTypeAccount(CreditAccount1.class, id, name);
                    break;
                case Constants.LAB_3_DELETE_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    provider.deleteTypeAccount(CreditAccount1.class, id);
                    break;
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }


    private static void chooseLab3Strategy3(String[] args) throws IOException {
        Strategy3Provider provider = new Strategy3Provider();
        try {
            long id;
            switch (args[3]) {
                case Constants.LAB_3_SAVE:
                    CreditAccount2 creditAccount = new CreditAccount2();
                    DebitAccount2 debitAccount = new DebitAccount2();
                    creditAccount.setOwner(args[4]);
                    creditAccount.setCreditLimit(new BigDecimal(args[5]));
                    creditAccount.setBalance(new BigDecimal(args[6]));
                    creditAccount.setInterestRate(new BigDecimal(args[7]));
                    debitAccount.setOwner(args[4]);
                    debitAccount.setBalance(new BigDecimal(args[5]));
                    debitAccount.setInterestRate(new BigDecimal(args[7]));
                    debitAccount.setOverdraftFee(new BigDecimal(args[4]));
                    provider.save(creditAccount, debitAccount);
                    break;
                case Constants.LAB_3_GET_BY_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.getByTypeAccount(id).get());
                    break;
                case Constants.LAB_3_UPDATE_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    provider.updateTypeAccount(id, name);
                    break;
                case Constants.LAB_3_DELETE_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    provider.deleteTypeAccount(id);
                    break;
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }


    private static void chooseLab3Strategy4(String[] args) throws IOException {
        Strategy4Provider provider = new Strategy4Provider();
        try {
            long id;
            switch (args[3]) {
                case Constants.LAB_3_SAVE:
                    CreditAccount3 creditAccount = new CreditAccount3();
                    DebitAccount3 debitAccount = new DebitAccount3();
                    creditAccount.setOwner(args[4]);
                    creditAccount.setCreditLimit(new BigDecimal(args[5]));
                    creditAccount.setBalance(new BigDecimal(args[6]));
                    creditAccount.setInterestRate(new BigDecimal(args[7]));
                    debitAccount.setOwner(args[4]);
                    debitAccount.setBalance(new BigDecimal(args[5]));
                    debitAccount.setInterestRate(new BigDecimal(args[7]));
                    debitAccount.setOverdraftFee(new BigDecimal(args[4]));
                    provider.save(creditAccount, debitAccount);
                    break;
                case Constants.LAB_3_GET_BY_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.getByTypeAccount(CreditAccount3.class, id).get());
                    break;
                case Constants.LAB_3_UPDATE_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    provider.updateTypeAccount(CreditAccount3.class, id, name);
                    break;
                case Constants.LAB_3_DELETE_TYPE_ACCOUNT:
                    id = Long.parseLong(args[4]);
                    provider.deleteTypeAccount(CreditAccount3.class, id);
                    break;
            }
        } catch (Exception e) {
            LOG.error(e);
        }
    }


    private static void resolveLab4(String[] args) {
        switch (args[2]) {
            case Constants.LAB_4_COMPONENT:
                chooseLab4Component(args, new ComponentProductCrud4());
                break;
            case Constants.LAB_4_LIST:
                chooseLab4List(args, new ListProductCrud4());
                break;
            case Constants.LAB_4_MAP:
                chooseLab4Map(args, new MapProductCrud4());
                break;
            case Constants.LAB_4_MAP_COMPONENT:
                chooseLab4MapComponent(args, new MapComponentProductCrud4());
                break;
            case Constants.LAB_4_SET:
                chooseLab4Set(args, new SetProductCrud4());
                break;
        }
    }

    private static void chooseLab4Component(String[] args, IProviderBaseCrud<Product4Component, Long> provider) {
        try {
            long id;
            Product4Component item = null;
            switch (args[3]) {
                case Constants.LAB_4_ADD:
                    Category4Component soda = new Category4Component(Long.parseLong(args[4]), args[5]);
                    HashSet<Category4Component> items = new HashSet<>();
                    Product4Component model = new Product4Component(Long.parseLong(args[6]), args[7], Double.parseDouble(args[8]), Integer.parseInt(args[9]), items);
                    items.add(soda);
                    provider.add(model);
                    break;
                case Constants.LAB_4_GET:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.get(id).get());
                    break;
                case Constants.LAB_4_UPDATE:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    item = provider.get(id).get();
                    item.setName(name);
                    LOG.info(provider.update(item));
                    break;
                case Constants.LAB_4_DELETE:
                    id = Long.parseLong(args[4]);
                    item = provider.get(id).get();
                    provider.delete(item);
                    break;
            }
        } catch (Exception e) {
            LOG.error("Lab 4 error: ", e);
        }
    }


    private static void chooseLab4List(String[] args, IProviderBaseCrud<Product4List, Long> provider) {
        try {
            long id;
            Product4List item;
            switch (args[3]) {
                case Constants.LAB_4_ADD:
                    Category4List soda = new Category4List(args[5], Long.parseLong(args[6]));
                    List<String> items = new ArrayList<>();
                    items.add(soda.getName());
                    Product4List model = new Product4List(Long.parseLong(args[6]), args[7], Double.parseDouble(args[8]), Integer.parseInt(args[9]), items);
                    provider.add(model);
                    break;
                case Constants.LAB_4_GET:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.get(id).get());
                    break;
                case Constants.LAB_4_UPDATE:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    item = provider.get(id).get();
                    item.setName(name);
                    LOG.info(provider.update(item));
                    break;
                case Constants.LAB_4_DELETE:
                    id = Long.parseLong(args[4]);
                    item = provider.get(id).get();
                    provider.delete(item);
                    break;
            }
        } catch (Exception e) {
            LOG.error("Lab 4 error: ", e);
        }
    }

    private static void chooseLab4Map(String[] args, IProviderBaseCrud<Product4Map, Long> provider) {
        try {
            long id;
            Product4Map item;
            switch (args[3]) {
                case Constants.LAB_4_ADD:
                    Category4Map soda = new Category4Map(args[5], Long.parseLong(args[6]));
                    Map<String, String> items = new HashMap();
                    Product4Map model = new Product4Map(Long.parseLong(args[6]), args[7], Double.parseDouble(args[8]), Integer.parseInt(args[9]), items);
                    items.put(model.getName(), soda.getName());
                    provider.add(model);
                    break;
                case Constants.LAB_4_GET:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.get(id).get());
                    break;
                case Constants.LAB_4_UPDATE:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    item = provider.get(id).get();
                    item.setName(name);
                    LOG.info(provider.update(item));
                    break;
                case Constants.LAB_4_DELETE:
                    id = Long.parseLong(args[4]);
                    item = provider.get(id).get();
                    provider.delete(item);
                    break;
            }
        } catch (Exception e) {
            LOG.error("Lab 4 error: ", e);
        }
    }

    private static void chooseLab4MapComponent(String[] args, IProviderBaseCrud<Product4MapComponent, Long> provider) {
        try {
            long id;
            Product4MapComponent item;
            switch (args[3]) {
                case Constants.LAB_4_ADD:
                    Map<String, Category4MapComponent> items = new HashMap();
                    Product4MapComponent model = new Product4MapComponent(Long.parseLong(args[6]), args[7], Double.parseDouble(args[8]), Integer.parseInt(args[9]), items);
                    Category4MapComponent soda = new Category4MapComponent(Long.parseLong(args[4]), Long.parseLong(args[6]), args[5]);
                    items.put(model.getName(), soda);
                    provider.add(model);
                    break;
                case Constants.LAB_4_GET:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.get(id).get());
                    break;
                case Constants.LAB_4_UPDATE:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    item = provider.get(id).get();
                    item.setName(name);
                    LOG.info(provider.update(item));
                    break;
                case Constants.LAB_4_DELETE:
                    id = Long.parseLong(args[4]);
                    item = provider.get(id).get();
                    provider.delete(item);
                    break;
            }
        } catch (Exception e) {
            LOG.error("Lab 4 error: ", e);
        }
    }

    private static void chooseLab4Set(String[] args, IProviderBaseCrud<Product4Set, Long> provider) {
        try {
            Long id;
            Product4Set item;
            switch (args[3]) {
                case Constants.LAB_4_ADD:
                    Category4Set soda = new Category4Set(args[5], Long.parseLong(args[6]));
                    HashSet<String> items = new HashSet<>();
                    items.add(soda.getName());
                    Product4Set model = new Product4Set(Long.parseLong(args[6]), args[7], Double.parseDouble(args[8]), Integer.parseInt(args[9]), items);
                    provider.add(model);
                    break;
                case Constants.LAB_4_GET:
                    id = Long.parseLong(args[4]);
                    LOG.info(provider.get(id).get());
                    break;
                case Constants.LAB_4_UPDATE:
                    id = Long.parseLong(args[4]);
                    String name = args[5];
                    item = provider.get(id).get();
                    item.setName(name);
                    LOG.info(provider.update(item));
                    break;
                case Constants.LAB_4_DELETE:
                    id = Long.parseLong(args[4]);
                    item = provider.get(id).get();
                    provider.delete(item);
                    break;
            }
        } catch (Exception e) {
            LOG.error("Lab 4 error: ", e);
        }
    }

    /**
     * Вызов метода API согласно параметрам
     *
     * @return String - Constants.BAD_ARGS, Constants.FAILURE, Constants.SUCCESS
     */
    private static String resolveAPIResult(DataProvider dataProvider, String[] arguments) {
        try {
            String action = arguments[0];
            switch (action.trim().toLowerCase()) {
                case Constants.INIT: {
                    dataProvider.init();
                    break;
                }
                // CRUD

                // DELETE
                case Constants.DELETE_CATEGORY: {
                    String category = arguments[1];
                    if (!dataProvider.deleteCategory(category)) {
                        return Constants.FAILURE;
                    }
                    break;
                }

                // GET ALL
                case Constants.GET_ALL_CATEGORY: {
                    List items = dataProvider.getAllCategory();
                    LOG.info("Categories: \n{}", items);
                    break;
                }
                case Constants.GET_ALL_COMPUTER: {
                    List items = dataProvider.getAllComputer();
                    LOG.info("Computers: \n{}", items);
                    break;
                }
                case Constants.GET_ALL_FRIDGE: {
                    List items = dataProvider.getAllFridge();
                    LOG.info("Fridges: \n{}", items);
                    break;
                }
                case Constants.GET_ALL_RECEIPT: {
                    List items = dataProvider.getAllReceipt();
                    LOG.info("Receipts: \n{}", items);
                    break;
                }
                case Constants.GET_ALL_SODA: {
                    List items = dataProvider.getAllSoda();
                    LOG.info("Sodas: \n{}", items);
                    break;
                }

                // GET BY ID
                case Constants.GET_COMPUTER: {
                    long id = Long.parseLong(arguments[1]);
                    Optional<Computer> item = dataProvider.getComputer(id);
                    if (item.isPresent()) {
                        LOG.info(item.get());
                    } else {
                        LOG.info("Item with such id doesnt exist");
                        LOG.debug("Item with such id doesnt exist: {}", id);
                    }
                    break;
                }
                case Constants.GET_FRIDGE: {
                    long id = Long.parseLong(arguments[1]);
                    Optional<Fridge> item = dataProvider.getFridge(id);
                    if (item.isPresent()) {
                        LOG.info(item.get());
                    } else {
                        LOG.info("Item with such id doesnt exist");
                        LOG.debug("Item with such id doesnt exist: {}", id);
                    }
                    break;
                }
                case Constants.GET_RECEIPT: {
                    long id = Long.parseLong(arguments[1]);
                    Optional<Receipt> item = dataProvider.getReceipt(id);
                    if (item.isPresent()) {
                        LOG.info(item.get());
                    } else {
                        LOG.info("Item with such id doesnt exist");
                        LOG.debug("Item with such id doesnt exist: {}", id);
                    }
                    break;
                }
                case Constants.GET_SODA: {
                    long id = Long.parseLong(arguments[1]);
                    Optional<Soda> item = dataProvider.getSoda(id);
                    if (item.isPresent()) {
                        LOG.info(item.get());
                    } else {
                        LOG.info("Item with such id doesnt exist");
                        LOG.debug("Item with such id doesnt exist: {}", id);
                    }
                    break;
                }

                // INSERT
                case Constants.INSERT_COMPUTER: {
                    String item = arguments[1];
                    if (!dataProvider.insertComputer(item)) {
                        return Constants.FAILURE;
                    }
                    break;
                }
                case Constants.INSERT_FRIDGE: {
                    String item = arguments[1];
                    if (!dataProvider.insertFridge(item)) {
                        return Constants.FAILURE;
                    }
                    break;
                }
                case Constants.INSERT_SODA: {
                    String item = arguments[1];
                    if (!dataProvider.insertSoda(item)) {
                        return Constants.FAILURE;
                    }
                    break;
                }

                // SHOP ACTIONS
                case Constants.CLOSE_BUCKET: {
                    String userSession = arguments[1];
                    if (!dataProvider.closeBucket(userSession)) {
                        return Constants.FAILURE;
                    }
                    break;
                }
                case Constants.ADD_PRODUCT_TO_BUCKET: {
                    long prodId = Long.parseLong(arguments[1]);
                    String category = arguments[2];
                    Optional<String> bucketId = Optional.empty();
                    if (arguments.length > 3) {
                        bucketId = Optional.of(arguments[3]);
                    }
                    if (!dataProvider.addProduct(prodId, category, bucketId).isPresent()) {
                        return Constants.FAILURE;
                    }
                    break;
                }

                default:
                    return Constants.BAD_ARGS;
            }
            return Constants.SUCCESS;
        } catch (Exception e) {
            LOG.error("Action arguments error: {}", e);
            System.exit(1);
        }
        return Constants.FAILURE;
    }
}
