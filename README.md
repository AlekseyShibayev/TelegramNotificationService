# Учебно-прикладной проект TelegramNotificationService

  * [Ссылка на этот проект](https://github.com/AlekseyShibayev/TelegramNotificationService)
  * Проект использует: [telegram-bot-spring-boot-starter](https://github.com/AlekseyShibayev/telegram-bot-spring-boot-starter)
  * [Ссылка на различные обучающие курсы:](https://github.com/AlekseyShibayev/additional-education-info)
  * как пользоваться? найти бота https://t.me/fixeers_bot или @fixeers_bot и написать ему что-нибудь

## Цели проекта:
### 1. Учебная:
Получить продвинутые навыки в разработке приложений на Java + Spring:
  * Java Core: Java 17, Stream API, Lambdas, Optional, Reflection
  * Spring(Core, Boot, Data Jpa, Web, Caching, Scheduling, AOP)
  * Spring Boot Test, JUnit5, TestContainers
  * Refactoring, Patterns, SOLID, DRY, KISS

### 2. Прикладная:
1. Написать приложение с использованием микросервисной архитектуры (будет модульный монолит)
2. Развернуть сервер на одноплатнике Orange Pi 4.
3. Пользоваться благами написанных модулей.
   
## Что уже есть в проекте:
### Модули:
  * модуль core - содержит удобные штуковины, которые будут нужны в каждом микросервисе (вынесены в [telegram-bot-spring-boot-starter](https://github.com/AlekseyShibayev/telegram-bot-spring-boot-starter))
  * модуль telegram - телеграм бот, управление подписками и модулями
  * модуль exchange_rate - отслеживает курс доллара aliexpress (todo: курс ЦБ и биржи)
  * модуль wildberries: 
  - desire_lot - отслеживает желаемые лоты wildberries, по конкретному лоту
  - searcher - отслеживает желаемые лоты wildberries, массовым поиском по производителю
  - knowledge - содержит справочную информацию
### Технологии:
  * java 17 + spring boot + maven

## Вечно забываемые команды docker:
1. docker build --tag "app:0" .
2. docker save -o app.tar app:0
3. docker run -d -p 8080:8080 -p 5005:5005 app:0
4. docker stop {$name}

## Маленькая jira (todo'шки, мысли):
1. Актуальные:
  - добавить отправку в тг через outbox pattern
  - навести порядок в старых модулях, используя новый опыт

  - Написать, что бот включается отправкой любого сообщения, при первом заходе в бота
  - Таки поставить фаерфокс для курса
  - Сделать outbox pattern. Отправлять в ТГ ответ не напрямую, а через фиксирование транзакции в бд. Отправка через event after commit
  - Сделать шедулер, который будет ночью удалять не нужные данные из бд
  - Перенести кнопку про курс в админку. Сделать управление подписками.

2. Прочие:
  - добавить первую регистрацию пользователя в виде переписки с ботом и подтверждением в конце (да, нет)
  - кнопка вкл выкл должна меняться в зависимости от текущего статуса пользователя
  - добавить управление подписками
  - прикрутить flyway, делать DDL не авто, а скриптами
  - добавить @Validate
  - посмотреть io.micrometer.core.annotation.Timed io.micrometer.core.aop.TimedAspect
  - найти или сделать Dockerfile openjre_alpine-musl, выкинув не нужные пакеты из jre, ожидаемый вес < 80 mb [habr](https://habr.com/ru/companies/piter/articles/692992/)
  - в core, сделать админку для сброса спринг кеш