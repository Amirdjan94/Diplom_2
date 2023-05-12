package ru.praktikum.order;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.praktikum.data.IngredientAll;
import ru.praktikum.data.IngredientForCreateOrder;
import ru.praktikum.data.User;
import ru.praktikum.data.UserCreds;
import ru.praktikum.user.UserCreate;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;
import static ru.praktikum.data.Const.*;

public class CreateOrder {
    private Response response;
    private IngredientAll ingredientAll;
    private ArrayList<String> ingredients = new ArrayList<>();
    private String accessToken;
    private UserCreds userCreds;
    private IngredientForCreateOrder ingredientForCreateOrder;
    private UserCreate userCreate = new UserCreate();


    public CreateOrder() {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("Send request for get ingredient ID")
    public IngredientAll getIngredientID() {
        return given()
            .header("Content-type", "application/json")
            .get(GET_INGREDIENT_PATH)
            .body().as(IngredientAll.class);
    }

    @Step("Send request for create order with authorization and with ingredient")
    public Response createOrderWithAuthWithIngredient(User user) {
        // Создаю пользователя и получаю токен используя десериализацию
        response = userCreate.createUser(user);
        userCreds = response.as(UserCreds.class);
        accessToken = userCreds.getAccessToken().replaceFirst("Bearer ", "");
        // Получаю хэш  ингридиентов
        ingredientAll = getIngredientID();
        ingredients.add(ingredientAll.getData().get(0).getId());
        ingredients.add(ingredientAll.getData().get(1).getId());
        // Формирую тело запроса
        ingredientForCreateOrder = new IngredientForCreateOrder(ingredients.toArray(new String[]{}));
        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(ingredientForCreateOrder)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Send request for create order without authorization and with ingredient")
    public Response createOrderWithoutAuthWithIngredient() {

        // Получаю хэш  ингридиентов
        ingredientAll = getIngredientID();
        ingredients.add(ingredientAll.getData().get(0).getId());
        ingredients.add(ingredientAll.getData().get(1).getId());
        // Формирую тело запроса
        ingredientForCreateOrder = new IngredientForCreateOrder(ingredients.toArray(new String[]{}));

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(ingredientForCreateOrder)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Send request for create order with authorization and without ingredient")
    public Response createOrderWithAuthWithoutIngredient(User user) {
        // Создаю пользователя и получаю токен используя десериализацию
        response = userCreate.createUser(user);
        userCreds = response.as(UserCreds.class);
        accessToken = userCreds.getAccessToken().replaceFirst("Bearer ", "");
        // Формирую тело запроса
        ingredientForCreateOrder = new IngredientForCreateOrder();

        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(ingredientForCreateOrder)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Send request for create order without authorization and without ingredient")
    public Response createOrderWithoutAuthWithoutIngredient() {
        ingredientForCreateOrder = new IngredientForCreateOrder();

        return given()
                .header("Content-type", "application/json")
                .auth().oauth2("")
                .and()
                .body(ingredientForCreateOrder)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Send request for create order without authorization and with wrong ingredient")
    public Response createOrderWithoutAuthWithWrongIngredient() {
        // Получаю хэш  ингридиентов
        ingredientAll = getIngredientID();
        ingredients.add(ingredientAll.getData().get(0).getId() + "test");
        ingredients.add(ingredientAll.getData().get(1).getId());
        // Формирую тело запроса.
        ingredientForCreateOrder = new IngredientForCreateOrder(ingredients.toArray(new String[]{}));

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(ingredientForCreateOrder)
                .when()
                .post(ORDER_PATH);
    }

    @Step("Send request for create order with authorization and with wrong ingredient")
    public Response createOrderWithAuthWithWrongIngredient(User user) {
        // Создаю пользователя и получаю токен используя десериализацию
        response = userCreate.createUser(user);
        userCreds = response.as(UserCreds.class);
        accessToken = userCreds.getAccessToken().replaceFirst("Bearer ", "");
        // Получаю хэш  ингридиентов
        ingredientAll = getIngredientID();
        ingredients.add(ingredientAll.getData().get(0).getId() + "test");
        ingredients.add(ingredientAll.getData().get(1).getId());
        // Формирую тело запроса.
        ingredientForCreateOrder = new IngredientForCreateOrder(ingredients.toArray(new String[]{}));

        return given()
                .header("Content-type", "application/json")
                .auth().oauth2(accessToken)
                .and()
                .body(ingredientForCreateOrder)
                .when()
                .post(ORDER_PATH);
    }

}
