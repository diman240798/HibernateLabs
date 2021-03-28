package ru.sfedu.hiber;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import ru.sfedu.hiber.lab1.api.HibernateMetaDataProvider;
import ru.sfedu.hiber.lab3.strategy1.api.Strategy1Provider;
import ru.sfedu.hiber.lab3.strategy1.model.Account;
import ru.sfedu.hiber.lab3.strategy1.model.CreditAccount;
import ru.sfedu.hiber.lab3.strategy2.model.Account1;
import ru.sfedu.hiber.lab3.strategy2.model.CreditAccount1;
import ru.sfedu.hiber.lab5.api.DataProvider;
import ru.sfedu.hiber.lab5.api.DataProviderHibernate;
import ru.sfedu.hiber.lab5.model.Computer;
import ru.sfedu.hiber.lab5.model.Fridge;
import ru.sfedu.hiber.lab5.model.Receipt;
import ru.sfedu.hiber.lab5.model.Soda;
import ru.sfedu.hiber.utils.HibernateUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
     * @return String - Constants.BAD_ARGS, Constants.FAILURE, Constants.SUCCESS
     * */
    private static String resolveLabResult(String[] args) {
        int labNumber = Integer.parseInt(args[1]);
        switch (labNumber) {
            case 1:

                break;
            case 1:

                break;
            case 1:

                break;
            case 1:

                break;
            case 5:
                throw new RuntimeException("Incorrect lab number: " + labNumber);
        }
    }


    /**
     * Вызов метода API согласно параметрам
     * @return String - Constants.BAD_ARGS, Constants.FAILURE, Constants.SUCCESS
     * */
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
