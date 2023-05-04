package ru.praktikum.user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.praktikum.data.User;
import ru.praktikum.data.UserCreds;
import ru.praktikum.data.UserInfo;

import static io.restassured.RestAssured.given;
import static ru.praktikum.data.Const.*;
import static ru.praktikum.data.Const.CHANGE_USER_PATH;

public class UserChange {
    private Response response;
    private String accessToken;
    private UserCreds userCreds;
    private UserInfo userInfo;
    private UserAction userAction = new UserAction();
    private UserCreate userCreate = new UserCreate();

    public UserChange() {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Send request for change email user with authorization")
    public Response changeEmailUserWithAuth (User user) {
        // Создаю пользователя и получаю токен используя десериализацию
        response = userCreate.createUser(user);
        userCreds = response.as(UserCreds.class);
        accessToken = userCreds.getAccessToken().replaceFirst("Bearer ", "");

        // Создаю объект для сериализации для отправки запроса на изменение данных пользователя
        userInfo = userCreds.getUser();
        // Меняю email пользователя
        userInfo.setEmail("test" + userInfo.getEmail());

        // Отправляю запрос на изменение данных пользователя
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(userInfo)
                .when()
                .patch(CHANGE_USER_PATH);
    }

    @Step("Send request for change name user with authorization")
    public Response changeNameUserWithAuth (User user) {
        // Создаю пользователя и получаю токен используя десериализацию
        response = userCreate.createUser(user);
        userCreds = response.as(UserCreds.class);
        accessToken = userCreds.getAccessToken().replaceFirst("Bearer ", "");

        // Создаю объект для сериализации для отправки запроса на изменение данных пользователя
        userInfo = userCreds.getUser();
        // Меняю name пользователя
        userInfo.setName("test" + userInfo.getName());

        // Отправляю запрос на изменение данных пользователя
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(userInfo)
                .when()
                .patch(CHANGE_USER_PATH);
    }

    @Step("Send request for change email user without authorization")
    public Response changeEmailUserWithoutAuth (User user) {
        // Создаю пользователя
        response = userCreate.createUser(user);
        userCreds = response.as(UserCreds.class);

        // Создаю объект для сериализации для отправки запроса на изменение данных пользователя
        userInfo = userCreds.getUser();
        // Меняю name пользователя
        userInfo.setName("test" + userInfo.getName());

        // Отправляю запрос на изменение данных пользователя
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userInfo)
                .when()
                .patch(CHANGE_USER_PATH);
    }

    @Step("Send request for change name user without authorization")
    public Response changeNameUserWithoutAuth (User user) {
        // Создаю пользователя и получаю токен используя десериализацию
        response = userCreate.createUser(user);
        userCreds = response.as(UserCreds.class);

        // Создаю объект для сериализации для отправки запроса на изменение данных пользователя
        userInfo = userCreds.getUser();
        // Меняю name пользователя
        userInfo.setName("test" + userInfo.getName());

        // Отправляю запрос на изменение данных пользователя
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(userInfo)
                .when()
                .patch(CHANGE_USER_PATH);
    }

    @Step("Send request for change exist email with authorization")
    public Response changeExistEmailWithAuth (User user, User userSecond, String email) {
        // Создаю 2 пользователя и получаю токен первого пользователя используя десериализацию
        userCreate.createUser(userSecond);
        response = userCreate.createUser(user);

        userCreds = response.as(UserCreds.class);
        accessToken = userCreds.getAccessToken().replaceFirst("Bearer ", "");

        // Создаю объект для сериализации для отправки запроса на изменение данных пользователя
        userInfo = userCreds.getUser();
        // Меняю email пользователя
        userInfo.setEmail(userSecond.getEmail());

        // Отправляю запрос на изменение данных пользователя
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(userInfo)
                .when()
                .patch(CHANGE_USER_PATH);
    }
}
