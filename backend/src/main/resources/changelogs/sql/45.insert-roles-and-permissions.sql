INSERT INTO roles(created_at, updated_at, role_name)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ROLE_USER');
INSERT INTO roles(created_at, updated_at, role_name)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ROLE_ADMIN');
INSERT INTO roles(created_at, updated_at, role_name)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ROLE_MANAGER');
INSERT INTO roles(created_at, updated_at, role_name)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ROLE_ACCOUNTANT');
INSERT INTO roles(created_at, updated_at, role_name)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ROLE_FRONT_DESK');
INSERT INTO roles(created_at, updated_at, role_name)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ROLE_HOUSEKEEPER');



INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'USER', 'Упрвление пользователями и их привилегиями');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'MANAGER', 'Упрвление менеджерами гостиниц и их привилегиями');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACCOUNTANT', 'Упрвление бухгалтерами и их привилегиями');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FRONT_DESK', 'Упрвление ресепшионистами и их привилегиями');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'HOUSEKEEPER', 'Упрвление горничными и их привилегиями');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SETTING_PERMISSIONS', 'Настройка привилегий');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'SYSTEM_MANAGEMENT',
        'Управление системой сайта и настройками приложения');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'HOTEL_INFORMATION',
        'Управленеи информацией о гостиницах,номерах, ценах и доступности');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'COST', 'Управление стоимостью');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'FINANCIAL_OF_HOTEL', 'Управление финансами отеля');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ROOM', 'Управление информацией о номерах');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'BOOKING', 'Управление бронированием');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ALL_HOTEL_ BOOKINGS', 'Управление всеми бронированиями отеля');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ALL_BOOKINGS_ON_APPS', 'Управление всеми бронированиями приложения');
INSERT into permissions(created_at, updated_at, name, description)
VALUES (CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'PAYMENT_FOR_BOOKING', 'Оплата бронирования');






