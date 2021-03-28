Проект эмулирует работу онлайн магазина. Имеются продукты разных видов, корзина, сессия и чек.
Онсновной сценарий работы состоит в осуществлении покупки. Пользователь получает сессию, по сессии добавляет корзины в товар (по их id и категории).
По наполнении корзины покупка завершается. Пользователь получает чек на оплату.

Так же имееются сценарии инициалиции базы данный и управления имеющимися продуктами в отдельности.

Переменные окружения
-Dlog4j.configurationFile=./log4j2.properties - свой конфиг логирования
-Denvironment=./environment.properties - конфиг приложения

Инициализация

java -jar shop.jar init - инициализация

Получить список категорий товаров
java -jar shop.jar get_all_category
java -Dhiber=./../src/main/resources/hibernate.cfg.xml -jar shop.jar get_all_category

java -jar shop.jar delete_category computer

Управление продуктами

Fridge-Холодильник
java -jar shop.jar get_all_fridge - получить список
java -jar shop.jar insert_fridge "10 Toshiba_x_32 55.4 64300 80 white 900 true" (id: int name:String weight:double price:double volume:int color:String power:int power:int noFrost: boolean (Optional Default: false))
java -jar shop.jar get_fridge 10 - получить по id

Soda-Воды, соки
java -jar shop.jar get_all_soda - получить список
java -jar shop.jar insert_soda "10 tasty_fanta 1.5 300 orange" (id: int name:String weight:double price:double flavour: String sparkled:(Optional Default: false))
java -jar shop.jar get_soda 10 - получить по id

Computer-Компьютер
java -jar shop.jar get_all_computer - получить список
java -jar shop.jar get_computer 10 - получить по id
java -jar shop.jar insert_computer "10 Hp_pavillion 2.4 64300 intel_i5 600 geforge_gtx_1060 2 true true"
(id: int name:String weight:double price:double processorName:String processorPower:int graphicsName:String graphicsVolume:int integratedWifi:Boolean integratedBluetooth:Boolean)

Receipt-Чек
java -jar shop.jar get_all_receipt - получить список
java -jar shop.jar get_receipt 1 - получить по id

Процесс покупки
CSV
java -jar shop.jar add_product 1 fridge
java -jar shop.jar add_product 1 soda 9cfb02c3-0fe3-4c49-8b70-2087afadb9eb
java -jar shop.jar add_product 1 computer 9cfb02c3-0fe3-4c49-8b70-2087afadb9eb
java -jar shop.jar close_bucket 9cfb02c3-0fe3-4c49-8b70-2087afadb9eb


/// LAB 1
java -jar shop.jar lab 1 get_list_schemas
java -jar shop.jar lab 1 get_list_tables
java -jar shop.jar lab 1 get_list_tables_type
java -jar shop.jar lab 1 get_list_role_tables_grant


/// LAB 2
java -jar shop.jar lab 2 getById
java -jar shop.jar lab 2 save
java -jar shop.jar lab 2 update
java -jar shop.jar lab 2 delete
