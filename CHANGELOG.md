 # Changelog
Все изменения в этом проекте будут документироваться в этом файле.

## [1.0.0] - 2025-01-10 Russian
### Добавлено
- Начальная версия приложения и API.
- Регистрация и аутентификация пользователей.
- Возможность изменение пароля.
- Главная страница пока что без реализации.
- Набросок чата.
- Основные CRUD операции с пользователями.

## [1.0.0] - 2025-01-10 English
### Added
- Initial version of the application and API.
- User registration and authentication.
- Password change functionality.
- Main page (currently without implementation).
- Chat draft.
- Basic CRUD operations with users.

## [1.0.1] - 2025-01-11 Russian
### Обновлен 
- `readme.md`
### Добавлено
- Разный вид для сообщений отправленных пользователем и другими
### Исправлено 
- Баг с возможностью создания 2 пользователей с одинаковым email добавив пробел в конце (Использованием метода .trim())

## [1.0.1] - 2025-01-11 English
### Updated
- `readme.md`

### Added
- Different styles for messages sent by the user and others.

### Fixed
- Bug allowing the creation of two users with the same email by adding a space at the end (fixed by using the `.trim()` method).


## [1.0.2] - 2025-01-12 Russian
### Обновлен 
- Сущность пользователя, добавлены поля для отслеживания мутов, банов, их времени, цвет ника, тега, и сам тег.
### Добавлено
- Возможность отрисовавания тега адаптером, дефолтные значения цвета для тега и имени, проверки на пустые значения, ендпоинты и запросы для тега и цветов.

## [1.0.2] - 2025-01-12 English
### Updated 
- User entity: added fields for tracking mutes, bans, their durations, nickname color, tag, and the tag itself.
### Added
- Ability to render tags using an adapter, default color values for tags and names, checks for empty values, endpoints, and requests for tags and colors.


## [1.0.3] - 2025-01-15 Russian
### Добавлено
- Обработчик события нажатия на сообщение, при нажатии появляется меню с выбором действий над сообщением, кнопки настраиваются, но пока логика не была добавлена, так же было исправленно несколько багов связаных с отображением меню.

## [1.0.3] - 2025-01-15 English
### Added
- An event handler for clicking on a message: when clicked, a menu appears with options for actions on the message. The buttons are customizable, but the logic hasn't been implemented yet. Additionally, several bugs related to menu display have been fixed.


## [1.0.4] - 2025-01-20 Russian
### Добавлено
- Таблица в базе данных с сущностью сообщения. 
- Запросы для отображения сообщений в приложении, и отправки из приложения в БД.
- Автообновление чата раз в 5 секунд
- Ограничение в 50 сообщений 

### Исправлено 
- Баг с пропадающими сообщениями в момент обновления

## [1.0.4] - 2025-01-20 English
### Added
- Table in database with entity of message
- Requests for displaying messages, creating and adding them to database
- Auto updater for chat (every 5 sec)
- Limit for messages (50) 

### Fixed
- Bug with missed messages in moment of update


## [1.0.5] - 2025-01-21 Russian
### Исправлено 
- Пропадающие сообщения когда recyclerview зполнен достаточно 

## [1.0.5] - 2025-01-21 English
### Fixed
- Missing messages when recyclerview full enough 

## [1.0.6] - 2025-01-28 Russian
### Добавлено
- Автопрокрутка вниз при загрузке чата
- Временная кнопка очистить чат

### Исправлено 
- Апдейтер для чата, теперь он действительно обновляет лист сообщений, из-за этого много прошлых багов исчезло

## [1.0.6] - 2025-01-28 English
### Added
- Autoscroll to the end, when the chat-page loads
- Button for cleaning chat

### Fixed 
- Chat adapter, now it update message, instead of removing all old mmessages and adding new, a lot of bugs dessapeared because of this decision


## [1.0.7] - 2025-02-08 Russian
### Добавлено
- Кнопки "Изменить", "Удалить", "Копировать" для управления сообщениями, а так-же запросы для БД для работы кнопок

### Исправлено 
- Апдейтер для чата, переписан на более удобную версию

## [1.0.7] - 2025-02-08 English
### Added
- Buttons "Edit," "Delete," and "Copy" for managing messages, as well as database queries to support button functionality.

### Fixed 
- Chat updater rewritten to a more convenient version.

## [1.0.8] - 2025-03-30 Russian
### Добавлено
- Конопка "закрепить", и вся логика для её работы, так-же обновлена АПИ для работы с закреплёнными сообщениями

### Исправлено 
- Пару незначительных багов

## [1.0.8] - 2025-03-30 English
### Added
- Button "Pin" was implemented 

### Fixed 
- Small bugs was fixed

