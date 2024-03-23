# java-explore-with-me

Дипломный проект. Приложение ExploreWithMe.
Оно позволит пользователям делиться информацией об интересных событиях и находить компанию для участия в них. Ссылка на PR: <https://github.com/Kirill495/java-explore-with-me/pull/5>

## Endpoints

### Подборки событий

#### - Public

- :one: GET /compilations
- :two: GET /compilations/{compId}

#### - Admin

- :one: POST /admin/compilations
- :two: PATCH /admin/compilations/{compId}
- :three: DELETE /admin/compilations/{compId}

### Категории событий

#### - Public

- :one: GET /categories
- :two: GET / categories/{compId}

#### - Admin

- :one: POST /admin/categories
- :two: PATCH /admin/categories/{catId}
- :three: DELETE /admin/categories/{catId}

### События

#### - Public

- :one: GET /events
- :two: GET /events/{id}

#### - Private

- :one: GET /users/{userId}/events
- :two: POST /users/{userId}/events
- :three: GET /users/{userId}/events/{eventsId}
- :four: PATCH /users/{userId}/events/{eventId}
- :five: GET /users/{userId}/events/{eventId}/requests
- :six: PATCH /users/{userId}/events/{eventId}/requests

#### - Admin

- :one: GET /admin/events
- :two: PATCH /admin/events/{eventId}

### Пользователи

#### - Admin

- :one: GET /admin/users
- :two: POST /admin/users
- :three: PATCH /admin/users/{userId}

### Запросы на участие в событиях

#### - Private

- :one: GET /users/{userId}/requests
- :two: POST /users/{userId}/requests
- :three: PATCH /users/{userId}/requests/{requestId}/cancel

### Комментарии

#### - Public

- :one: GET /events/{eventId}/comments

#### - Private

- :one: POST /users/{userId}/comments/events/{eventId}
- :two: GET /users/{userId}/comments/{id}
- :three: GET /users/{userId}/comments/
- :four: PATCH /users/{userId}/comments/{id}
- :five: DELETE /users/{userId}/comments/{id}

#### - Admin

- :one: GET /events/{eventId}/comments
