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



### P.S I am not quite good at English, so I use chatgpt to translate my text.
