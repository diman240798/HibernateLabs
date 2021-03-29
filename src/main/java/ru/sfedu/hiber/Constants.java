package ru.sfedu.hiber;

import java.util.Arrays;
import java.util.List;

public class Constants {
    // название схемы
    public static final String SQL_ALL_SCHEMAS = "SELECT schema_name FROM information_schema.schemata";
    //таблицы
    public static final String SQL_ALL_TABLES = "SELECT table_name FROM information_schema.tables";
    //типы таблиц
    public static final String SQL_ALL_TABLES_TYPE = "SELECT table_type FROM information_schema.tables";
    //список всех привилегий на таблицы
    public static final String SQL_ALL_ROLE_TABLES_GRANTS = "SELECT * FROM information_schema.role_table_grants";


    // ACTIONS
    public static final String INIT = "init";

    // Add
    public static final String INSERT_FRIDGE = "insert_fridge";
    public static final String INSERT_SODA = "insert_soda";
    public static final String INSERT_COMPUTER = "insert_computer";

    // GET ALL
    public static final String GET_ALL_COMPUTER = "get_all_computer";
    public static final String GET_ALL_CATEGORY = "get_all_category";
    public static final String GET_ALL_FRIDGE = "get_all_fridge";
    public static final String GET_ALL_SODA = "get_all_soda";
    public static final String GET_ALL_RECEIPT = "get_all_receipt";

    // GET BY ID
    public static final String GET_COMPUTER = "get_computer";
    public static final String GET_FRIDGE = "get_fridge";
    public static final String GET_SODA = "get_soda";
    public static final String GET_RECEIPT = "get_receipt";

    //
    public static final String DELETE_CATEGORY = "delete_category";

    // Shop
    public static final String ADD_PRODUCT_TO_BUCKET = "add_product";
    public static final String CLOSE_BUCKET = "close_bucket";

    // Models
    public static final String BUCKET = "bucket";
    public static final String CATEGORY = "category";
    public static final String COMPUTER = "computer";
    public static final String PRODUCT = "product";
    public static final String FRIDGE = "fridge";
    public static final String RECEIPT = "receipt";
    public static final String SODA = "soda";

    public static final List<String> ENTITIES = Arrays.asList(
            BUCKET + "_" + PRODUCT,
            BUCKET,
            RECEIPT + "_" + PRODUCT,
            RECEIPT,
            CATEGORY + "_" + PRODUCT,
            COMPUTER,
            FRIDGE,
            SODA,
            PRODUCT,
            CATEGORY
    );

    // Data Providers
    public static final String XML_DATA_PROVIDER = "xml";
    public static final String CSV_DATA_PROVIDER = "csv";
    public static final String JDBC_DATA_PROVIDER = "jdbc";


    // Categories
    public static final String CATEGORY_FRIDGE = "fridge";
    public static final String CATEGORY_SODA = "soda";
    public static final String CATEGORY_COMPUTER = "computer";


    // Messages
    public static final String BAD_ARGS = "BAD_ARGUMENTS";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILURE = "FAILURE";

    public static final String COMPUTER_ENTITY = "computer_entity";
    public static final String PRODUCT_ENTITY = "product_entity";


    public static final String LAB = "lab";

    /// LAB 1
    public static final String LAB_1_GET_SCHEMAS = "get_list_schemas";
    public static final String LAB_1_GET_TABLES = "get_list_tables";
    public static final String LAB_1_GET_ROLE_TABLES = "get_list_tables_type";
    public static final String LAB_1_GET_TABLES_TYPE = "get_list_role_tables_grant";

    /// LAB 2
    public static final String LAB_2_GET_BY_ID = "getById";
    public static final String LAB_2_SAVE = "save";
    public static final String LAB_2_UPDATE = "update";
    public static final String LAB_2_DELETE = "delete";

    /// LAB 3
    public static final String LAB_3_MAPPED = "mapped";
    public static final String LAB_3_TABLE_PER_CLASS = "table_per_class";
    public static final String LAB_3_SINGLE_TABLE = "single_table";
    public static final String LAB_3_JOINED = "joined";

    public static final String LAB_3_DELETE_TYPE_ACCOUNT = "deleteTypeAccount";
    public static final String LAB_3_UPDATE_TYPE_ACCOUNT = "updateTypeAccount";
    public static final String LAB_3_GET_BY_TYPE_ACCOUNT = "getByTypeAccount";
    public static final String LAB_3_GET_BY_ACCOUNTS = "getByAccounts";
    public static final String LAB_3_SAVE = "save";

    /// LAB 4
    public static final String LAB_4_COMPONENT = "component";
    public static final String LAB_4_LIST = "list";
    public static final String LAB_4_MAP = "map";
    public static final String LAB_4_MAP_COMPONENT = "map_component";
    public static final String LAB_4_SET = "set";

    public static final String LAB_4_GET = "get";
    public static final String LAB_4_ADD = "add";
    public static final String LAB_4_DELETE = "delete";
    public static final String LAB_4_UPDATE = "update";

}
