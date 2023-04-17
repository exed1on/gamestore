1. You can browse games using endpoint "/games" and http method GET, getting JSON list of games.
2. You can search for a game using endpoint "/games/search" with param "name" and http method GET, getting JSON with one game (example: /games/search?name=The Witcher 3).

For the endpoints below you need add Basic Auth to your request: username "admin" and password "password" (only admins have access to adding, updating and deleting games)

3. You can add game using endpoint "/games/add" with Request Body and http method POST, getting JSON of added game.

Example of Request Body (JSON):
{
"name": "The Witcher 3",
"company": "CD Projekt RED",
"price": 19.99,
"description": "A story-driven, open-world RPG set in a dark fantasy universe.",
"releaseDate": "2015-05-19"
}

4. You can update game using endpoint "/games/update" with Request Body and http method PUT (updating is based on game name), getting JSON of updated game.

Example of Request Body (JSON):
{
"name": "The Witcher 3",
"company": "CD Projekt RED",
"price": 9.99,
"description": "A story-driven, open-world RPG set in a dark fantasy universe.",
"releaseDate": "2015-05-19"
}

5. You can delete game using endpoint "/games/delete" with with param "name" and http method DELETE (example: /games/delete?name=The Witcher 3).
