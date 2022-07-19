# wikitabia-server
Сервер проекта Wikitabia.

## Эпилог
Стажировочный проект компании Интабия.
Приглашаются все желающие попробовать свои силы в OpenSource разработке,
получить обратную свзяь от профессиональных разработчиков, составить себе
обьектитвное портфолио к резюме начинающего Java-разработчика. С чего начать смотри [тут](./CONTRIBUTING.md).

## О чём проект
Wikitabia - веб сервер для сохранения полезных ссылок на технические материалы
с возможностью маркировать ссылку некоторым тэгом и в дальнейшем получать по тэгам
полезные материалы.

## Что уже можем

Веб сервер для ведения полезных веб ссылок, который может:
1) Регистрировать новых пользователей
2) Авторизировать текущих пользователей
3) Предоставляет административный интерфейс для CRUD операций над веб ссылками и пользователями
4) Предоставляет CRUD API для клиентских приложений, таких как [telegram wikibot](https://github.com/intabia-intership/wikitabia-telegram-bot) и веб сайт.

## Видение развития проекта

1) Стабилизировать первую рабочию версию проекта для разворачивания полноценного продакшен-реди сервера
2) Настроить правила модерации материалов и участников с их уровнем доступа. Ограничить возможность добавления материалов только для ответсвенных участников и открыть доступ к материалам для всех.
3) Открыть развёрнутое продакшен решение для всех
4) Развивать продукт по обратной свзяи от пользователей

## Как собрать и запустить

Подготовь окружение:
1) Jdk 11
2) Maven 3
3) Docker 20.10.16 + docker-compose-plugin

Собери проект
1) `mvn clean install`

Собери docker образ
1) `docker build . -t wikitabia`

Отредактируй переменные окружения в файле [.env](.env) , если необходимо:
1) DB_NAME - наименование БД
2) DB_USER - логин пользователя БД
3) DB_PASS - пароль пользователя БД
4) DB_PORT - порт БД
5) WIKITABIA_SERVER_IMAGE_NAME - наименование docker образа
6) WIKITABIA_SERVER_IMAGE_TAG - тэг docker образа
7) KEYCLOAK_USER - логин пользователя keycloak
8) KEYCLOAK_PASS - пароль пользователя keycloak
9) KEYCLOAK_PORT - порт keycloak

Подними docker контейнеры
1) `docker compose up -d`

Настрой keycloak
1) Перейди на localhost:{KEYCLOAK_PORT}/auth/admin/master/console/
2) Зайди, используя KEYCLOAK_USER и KEYCLOAK_PASS из файла [.env](.env)
3) Создай новый realm с названием wikitabia-realm
4) Перейди на Configure -> Clients -> Create и создай нового клиента с Client ID= wikitabia-server и Client Protocol= openid-connect
5) В настройках клиента выбери Access Type= confidential
6) Включи Authorization Enabled
7) Задай Root URL как http://localhost:8080
8) Задай Valid Redirect URIs как http://localhost:8080/* (звездочка обязательна)
9) Сохрани настройки
10) Перейди на вкладку Credentials, скопируй Secret и вставь его в [application-dev.yml](src/main/resources/application-dev.yml) в keycloak.credentials.secret
11) Задай необходимые роли через Configure -> Roles -> Add (сейчас это USER и ADMIN)
12) Добавь пользователей через Manage -> Users -> Add user, обязательно задай Username, Credentials -> Password (выключив Temporary), а также назначь роли через Role Mapping

## Как проверить, что после моих изменений ничего не сломалось

1) Добавь тест на новые изменения
2) Собери проект с запуском тестов

## CI/CD
Реализован через Github Actions
1) Workflow [cборка с тестами](.github/workflows/verify.yml)  
При push и создании pull request в любую ветку, кроме `main` - прогоняется сборка с тестами.
2) Workflow [синхронизация с гитлаб](.github/workflows/gitlab_sync.yml)  
При push в любую ветку, кроме `main` - выполняется синхронизация с гитлаб.
3) Workflow [сборка и доставка](.github/workflows/build-and-delivery-dev.yml)  
При push в ветку `main`:
- прогоняется сборка с тестами
- выполняется сборка docker образа и его публикация в docker registry
- выполняется синхронизация с гитлаб  

Deploy на стенд производится через GitLab CI/CD.

## Переменные среды
### Для [docker-compose.yaml](docker-compose.yaml)
| Переменная    | Описание                     |
|---------------|------------------------------|
| DB_USER       | логин пользователя БД        |
| DB_PASS       | пароль пользователя БД       |
| DB_NAME       | имя БД                       |
| DB_PORT       | порт БД                      |
| KEYCLOAK_USER | логин пользователя keycloak  |
| KEYCLOAK_PASS | пароль пользователя keycloak |
| KEYCLOAK_PORT | порт keycloak                |

### Для [application.yml](src/main/resources/application.yml)
| Переменная             | Описание                                                   |
|------------------------|------------------------------------------------------------|
| WIKITABIA_SERVER_PORT  | порт сервера викитабии                                     |
| DB_ADDR                | адрес БД (jdbc:postgresql://{ADDRESS}:{DB_PORT}/{DB_NAME}) |
| DB_USER                | логин пользователя БД                                      |
| DB_PASS                | пароль пользователя БД                                     |
| KEYCLOAK_REALM         | название keycloak realm'a                                  |
| KEYCLOAK_RESOURCE      | имя клиента, созданного в keycloak                         |
| KEYCLOAK_URL           | адрес keycloak (http://{ADDRESS}:{KEYCLOAK_PORT}/auth)     |
| KEYCLOAK_CLIENT_SECRET | секрет, сгенерированный keycloak для непубличного клиента  |