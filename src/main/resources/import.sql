-- Gli statement non devono andare a capo
--Insert pizze
INSERT INTO db_pizzeria.pizzas(id, price, description, name, photo)VALUES(1, 3.5, 'Classica regina delle pizze', 'Margherita', 'https://it.ooni.com/cdn/shop/articles/Margherita-9920.jpg?crop=center&height=800&v=1644590028&width=800');
INSERT INTO db_pizzeria.pizzas(id, price, description, name, photo)VALUES(2, 7, '4 formaggi con salsiccia', 'Porco pizza', 'https://blog.giallozafferano.it/ricettechepassione/wp-content/uploads/2020/05/pizza-napoletana-4-formaggi-bianca.jpg');
INSERT INTO db_pizzeria.pizzas(id, price, description, name, photo)VALUES(3, 10, 'crema di tartufo, provola e speck', 'Michelin', 'https://www.menu.it/media/ricette/pizza-speck-e-provola-64729/conversions/PRO-main.jpg');
--Insert discounts
INSERT INTO db_pizzeria.discounts (expire_date, pizza_id, start_date, note) VALUES('2024-05-10', 1, '2023-12-10', '');
INSERT INTO db_pizzeria.discounts (expire_date, pizza_id, start_date, note) VALUES('2024-08-05', 2, '2023-12-05', '');
INSERT INTO db_pizzeria.discounts (expire_date, pizza_id, start_date, note) VALUES('2024-06-02', 3, '2023-12-02', 'Anno Pazzo');
--Insert ingredients
INSERT INTO db_pizzeria.ingredients (name) VALUES('Salsiccia');
INSERT INTO db_pizzeria.ingredients (name) VALUES('Patate al forno');
INSERT INTO db_pizzeria.ingredients (name) VALUES('Gorgonzola');