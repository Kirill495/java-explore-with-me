# java-explore-with-me

Дипломный проект. Приложение ExploreWithMe.
Оно позволит пользователям делиться информацией об интересных событиях и находить компанию для участия в них. Ссылка на PR: <https://github.com/Kirill495/java-explore-with-me/pull/5>

## Endpoints

### Public: Подборки событий

- :one: GET /compilations
- :two: GET /compilations/{compId}

### Admin: Категории событий

- :one: POST /admin/categories
- :two: PATCH /admin/categories/{catId}
- :three: DELETE /admin/categories/{catId}

### Private: События

- :one: GET /users/{userId}/events
- :two: POST /users/{userId}/events
- :three: GET /users/{userId}/events/{eventsId}
- :four: PATCH /users/{userId}/events/{eventId}
- :five: GET /users/{userId}/events/{eventId}/requests
- :six: PATCH /users/{userId}/events/{eventId}/requests
