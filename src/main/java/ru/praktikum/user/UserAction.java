package ru.praktikum.user;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.praktikum.data.TokenForAuth;
import ru.praktikum.data.User;
import ru.praktikum.data.UserCreds;

import static io.restassured.RestAssured.given;
import static ru.praktikum.data.Const.*;

public class UserAction {
    private String accessToken;
    private UserCreds userCreds;
    private TokenForAuth tokenForAuth;

    public UserAction() {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Send request for delete user")
    public Response deleteUser (Response response) {
        userCreds = response.as(UserCreds.class);
        accessToken = userCreds.getAccessToken().replaceFirst("Bearer ", "");
        return given()
                .auth().oauth2(accessToken)
                .when()
                .delete(CHANGE_USER_PATH);
    }

    @Step("Send request for logout user")
    public Response logout (Response response) {
        // Получаю токен используя десериализацию
        UserCreds userCreds = response.as(UserCreds.class);
        tokenForAuth = new TokenForAuth(userCreds.getRefreshToken());
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(tokenForAuth)
                .when()
                .post(LOGOUT_USER_PATH);
    }

    @Step("Send request for login user")
    public Response login (User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_PATH);
    }

    @Step("Send request for delete user with incorrect password")
    public Response loginUserWithIncorrectPassword (User user) {
        user.setPassword(user.getPassword()+"123");
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_PATH);
    }

    @Step("Send request for delete user with incorrect email")
    public Response loginUserWithIncorrectEmail (User user) {
        user.setEmail("123" + user.getEmail());
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_PATH);
    }

}
