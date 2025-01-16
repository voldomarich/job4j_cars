# job4j_cars

# Проект "АвтоМаг"
## Сайт по продаже машин

На сайте должны быть объявления

В объявлении должно быть:
- описание
- марка машины
- тип кузова
- фото

Объявление имеет статус - продано или нет

## Technology Stack
- Java 17
- PostgreSQL
- Springframework.boot 2.7.6
- Hibernate
- Lombok
- H2database
- Liquibase

## Running the Project

- Создать базу данных под названием 'cars' в программе PostgreSQL
- Добавить таблицы в базу данных (на каждую модель данных своя таблица)
- Проверить настройки доступа к базе данных в следующих файлах:
  -- db/liquibase.properties
  -- src/main/resources/hibernate.cfg.xml
- Перед началом выполнить команду liquibase:update
- Запустить метод Main в корне проекта

## Requirements for the environment
- Java 17
- PostgreSQL 15.2
- Apache Maven 3.8.4
