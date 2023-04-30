package ru.praktikum.user;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.praktikum.data.User;
import ru.praktikum.data.UserCreds;
import ru.praktikum.data.UserInfo;

import static io.restassured.RestAssured.given;
import static ru.praktikum.data.Const.*;

public class UserAction {
    private String accessToken;
    private UserCreds userCreds;
    private UserInfo userInfo;
    public UserAction() {
        RestAssured.baseURI = BASE_URI;
    }
    public Response deleteUser (Response response) {
        userCreds = response.as(UserCreds.class);
        accessToken = userCreds.getAccessToken().replaceFirst("Bearer ", "");
        return given()
                .auth().oauth2(accessToken)
                .when()
                .delete(CHANGE_USER_PATH);
    }
    public Response logout (Response response) {
        // Получаю токен используя десериализацию
        UserCreds userCreds = response.as(UserCreds.class);
        String refreshToken = userCreds.getRefreshToken();
        String json  = "{\"token\": \""+refreshToken+"\"}";
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(LOGOUT_USER_PATH);
    }
    public Response login (User user) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_PATH);
    }
    public Response loginUserWithIncorrectPassword (User user) {
        user.setPassword(user.getPassword()+"123");
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(user)
                .when()
                .post(LOGIN_USER_PATH);
    }
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
