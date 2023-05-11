# Учебно-прикладной проект TelegramNotificationService

  * [Ссылка на этот проект](https://github.com/AlekseyShibayev/TelegramNotificationService)
  * Проект использует: [telegram-bot-spring-boot-starter](https://github.com/AlekseyShibayev/telegram-bot-spring-boot-starter)
  * [Ссылка на различные обучающие курсы:](https://github.com/AlekseyShibayev/additional-education-info)
  * как пользоваться? найти бота https://t.me/fixeers_bot или @fixeers_bot и написать ему что-нибудь

## Цели проекта:
### 1. Учебная:
1. Получить продвинутые навыки в разработке приложений на Java + Spring:
  * Java Core: Java 11, Stream API, Lambdas, Optional, Reflection
  * Spring(Core, Boot, Data Jpa, Web, Caching, Scheduling, AOP)
  * Spring Boot Test, JUnit5
  * Refactoring, Patterns, SOLID, DRY, KISS
2. Изучить технологии:
  * Spring Security
  * Docker Compose, Kubernetes, TestContainers
  * Kafka

### 2. Прикладная:
1. Написать приложение с использованием микросервисной архитектуры.
2. Развернуть сервер на одноплатнике Orange Pi 4.
3. Пользоваться благами написанных модулей.
   
## Что уже есть в проекте:
### Модули:
  * модуль core - содержит удобные штуковины, которые будут нужны в каждом микросервисе (вынесены в [telegram-bot-spring-boot-starter](https://github.com/AlekseyShibayev/telegram-bot-spring-boot-starter))
  * модуль telegram - телеграм бот, управление подписками и модулями
  * модуль exchangerate - отслеживает курс доллара aliexpress
  * модуль wildberries - отслеживает желаемые лоты wildberries
### Технологии:
  * java 11 + spring boot + maven
  * бд использую H2, потому что давно хотел. Вот консоль её: http://localhost:8080/h2-console
  * swagger: http://localhost:8080/swagger-ui.html
  * есть возможность запуска в Docker
  * перешел с JUnit4 на JUnit5

## Вечно забываемые команды docker:
1. docker build --tag "app:0" .
2. docker save -o app.tar app:0
3. docker run -d -p 8080:8080 -p 5005:5005 app:0
4. docker stop {$name}

## Маленькая jira (todo'шки, мысли):
1. Простые:

  - подумать над @Transactional, после полученных знаний
  - подумать над DDD, после полученных знаний

  - добавить управление подписками
  - добавить добавление лотов вб

  - использовать бд PostgreSQL, H2 использовать в тестах
  - прикрутить flyway, делать DDL не авто, а скриптами

  - добавить @Validate

2. Сложные:
  - разбить оставшийся проект на 3 модуля, научить общаться их между собой
  - засунуть 3 проекта + core в docker-compose
  - сделать общение через kafka
  - засунуть 3 проекта (с общим core) + kafka в kuber
  - прикрутить TestContainers или WireMock

3. Прочие:
  - посмотреть io.micrometer.core.annotation.Timed io.micrometer.core.aop.TimedAspect
  - найти или сделать Dockerfile openjre_alpine-musl, выкинув не нужные пакеты из jre, ожидаемый вес < 80 mb [habr](https://habr.com/ru/companies/piter/articles/692992/)
  - в core, сделать админку для сброса спринг кеш
  - прикрутить kafka + ELK
  - посмотреть Debezium(конфиги) + Redis(кеш)

## селекты для бд
select * from CHAT;
select * from HISTORY;
select * from USER_INFO;
select * from CHATS_SUBSCRIPTIONS;
select * from SUBSCRIPTION;
select * from LOT;
select * from EXCHANGE_RATE;