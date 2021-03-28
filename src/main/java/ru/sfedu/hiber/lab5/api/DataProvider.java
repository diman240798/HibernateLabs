package ru.sfedu.hiber.lab5.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.sfedu.hiber.Constants;
import ru.sfedu.hiber.lab5.api.helper.InitializerData;
import ru.sfedu.hiber.lab5.api.helper.InsertManager;
import ru.sfedu.hiber.lab5.model.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public interface DataProvider {

    Logger LOG = LogManager.getLogger(DataProvider.class);

    // INIT

    /**
     * Инициализации базовых данных
     *
     * @return void
     */
    default void init() throws Exception {
//        InitializerData.COMPUTERS.forEach(this::insertComputer);
//        InitializerData.FRIDGES.forEach(this::insertFridge);
//        InitializerData.SODA.forEach(this::insertSoda);
        InitializerData.CATEGORIES.forEach(this::insertCategory);
    }

    // CRUD


    boolean insertCategory(Category category);

    /**
     * Поиск по имени категории
     *
     * @param name - название категории
     * @return Optional<Category>
     */
    Optional<Category> getCategory(String name);

    /**
     * Поиск по id
     *
     * @param id - идентификатор модели
     * @return Optional<Computer>
     */
    Optional<Computer> getComputer(long id);

    /**
     * Поиск по id
     *
     * @param id - идентификатор модели
     * @return Optional<Fridge>
     */
    Optional<Fridge> getFridge(long id);

    /**
     * Поиск по id
     *
     * @param id - идентификатор модели
     * @return Optional<Receipt>
     */
    Optional<Receipt> getReceipt(long id);

    /**
     * Поиск по id
     *
     * @param id - идентификатор модели
     * @return Optional<Soda>
     */
    Optional<Soda> getSoda(long id);

    /**
     * Вставка в базу данных
     *
     * @param modelStr - строка пользователя с консоли
     * @return boolean - успешно, неуспешно
     */
    default boolean insertComputer(String modelStr) throws Exception {
        Optional<Computer> itemOption = InsertManager.getComputerFromString(modelStr);
        return itemOption.filter(this::insertComputer).isPresent();
    }

    boolean insertComputer(Computer computer);

    /**
     * Вставка в базу данных
     *
     * @param modelStr - строка пользователя с консоли
     * @return boolean - успешно, неуспешно
     */
    default boolean insertFridge(String modelStr) throws Exception {
        Optional<Fridge> itemOption = InsertManager.getFridgeFromString(modelStr);
        return itemOption.filter(this::insertFridge).isPresent();
    }

    boolean insertFridge(Fridge item);

    /**
     * Вставка в базу данных
     *
     * @param modelStr - строка пользователя с консоли
     * @return boolean - успешно, неуспешно
     */
    default boolean insertSoda(String modelStr) throws Exception {
        Optional<Soda> itemOption = InsertManager.getSodaFromString(modelStr);
        return itemOption.filter(this::insertSoda).isPresent();
    }

    boolean insertSoda(Soda soda);

    // SHOP

    /**
     * Завершение покупок в магазине
     *
     * @param bucketId - id корзины
     * @return boolean - успешно, неуспешно
     */
    default boolean closeBucket(String bucketId) throws Exception {
        LOG.info("Getting bucket entity");
        Optional<Bucket> bucketOption = getBucketById(bucketId);

        if (!bucketOption.isPresent()) {
            LOG.info("No bucket with such id exists. No check will be printed.");
            return false;
        }

        LOG.info("Found bucket");
        Bucket bucket = bucketOption.get();
        List<Product> products = bucket.getProducts();

        LOG.info("Got bucket products. Counting receipt.");

        LOG.info("Receipt data ready. Printing.");
        long receiptId = ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);
        double totalPrice = products.stream().map(Product::getPrice).reduce(Double::sum).orElse(0.0);
        Receipt receipt = new Receipt(receiptId, products, totalPrice);
        insertReceipt(receipt);
        LOG.info("Your receipt is: \n{}", receipt);

        deleteBucket(bucket);
        return true;
    }

    boolean insertReceipt(Receipt receipt);

    /**
     * Добавление продукта в корзину
     *
     * @param productId    - id продукта
     * @param prodCategory - категория продукта
     * @param bucketId
     * @return Optional<Bucket>
     */
    default Optional<Bucket> addProduct(long productId, String prodCategory, Optional<String> bucketId) throws Exception {
        Optional<Category> categoryOption = getCategory(prodCategory);
        if (!categoryOption.isPresent()) {
            LOG.info("No such category: {}", prodCategory);
            return Optional.empty();
        }

        Category category = categoryOption.get();

        Optional<Product> productOptional = getProductByIdAndCategory(productId, category.getName());

        if (!productOptional.isPresent()) {
            LOG.info("Product with such id was not found: {}", productId);
            return Optional.empty();
        }

        Product product = productOptional.get();
        LOG.info("Found product: {}", product);
        Optional<Bucket> bucketOption = getBucketById(bucketId);

        Bucket bucket;
        if (bucketOption.isPresent()) {
            bucket = bucketOption.get();
        } else if (bucketId.isPresent()) {
            LOG.error("Bucket with id: {} doesnt exist!", bucketId.get());
            return Optional.empty();
        } else {
            bucket = new Bucket(UUID.randomUUID().toString());
        }

        bucket.getProducts().add(product);
        if (upsertBucket(bucket)) {
            LOG.info("Product added succesfully: Bucket id {}", bucket.getId());
            return Optional.of(bucket);
        } else {
            LOG.info("Error inserting bucket");
            return Optional.empty();
        }
    }


    /**
     * Поиск продукта по id и категории
     *
     * @param productId - id продукта
     * @param category  - категория продукта
     * @return void
     */
    default Optional getProductByIdAndCategory(long productId, String category) {
        if (Objects.equals(category, Constants.SODA)) {
            return getSoda(productId);
        } else if (Objects.equals(category, Constants.COMPUTER)) {
            return getComputer(productId);
        } else if (Objects.equals(category, Constants.FRIDGE)) {
            return getFridge(productId);
        }
        throw new RuntimeException("Unknown category: " + category);
    }


    // OUT OF USE CASE

    /**
     * Поиск корзины по сессии пользователя
     *
     * @param bucketIdOption - Optional<id> корзины
     * @return void
     */
    default Optional<Bucket> getBucketById(Optional<String> bucketIdOption) {
        if (!bucketIdOption.isPresent()) return Optional.empty();
        String bucketId = bucketIdOption.get();
        return getBucketById(bucketId);
    }

    /**
     * Поиск корзины по сессии пользователя
     *
     * @param bucketId - id корзины
     * @return void
     */
    Optional<Bucket> getBucketById(String bucketId);

    /**
     * Удалить козину
     *
     * @param bucket - корзина
     * @return void
     */
    boolean deleteBucket(Bucket bucket) throws Exception;



    /**
     * Вставить/обновить корзину
     *
     * @param item - модель корзины
     * @return boolean
     */
    boolean upsertBucket(Bucket item) throws Exception;

    /**
     * Вставить корзину
     *
     * @param item - модель корзины
     * @return boolean
     */
    boolean insertBucket(Bucket item) throws Exception;

    List<Bucket> getAllBucket();

    List<Category> getAllCategory();

    List<Computer> getAllComputer();

    List<Fridge> getAllFridge();

    List<Receipt> getAllReceipt();

    List<Soda> getAllSoda();

    void deleteAll();

    List<Computer> getComputersSql();

    List<Computer> getComputersHql();

    List<Computer> getComputersCriteria();

    boolean deleteCategory(String category);
}
