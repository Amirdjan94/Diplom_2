package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.data.User;
import ru.praktikum.data.UserGenerator;
import ru.praktikum.order.CreateOrder;
import ru.praktikum.user.UserAction;
import ru.praktikum.user.UserCreate;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum.data.Const.*;

public class CreateOrderTest {
    private User user;
    private UserAction userAction;
    private UserCreate createUser;
    private Response response;
    private CreateOrder createOrder;
    private ArrayList<String> ingredients;

    @Before
    public void setUp() {
        user = UserGenerator.getUser();
        userAction = new UserAction();
        createUser = new UserCreate();
        createOrder = new CreateOrder();
        ingredients = new ArrayList<>();
    }

    @Test
    @DisplayName("Check create order with authorization user and with ingredient")
    public void createOrderWithAuthWithIngredientTest(){
        response = createOrder.createOrderWithAuthWithIngredient(user);
        userAction.deleteUser(userAction.login(user));
        response.then()
                .assertThat().body("success", equalTo(true))
                .and().assertThat().body("order.owner.email", equalTo(user.getEmail()))
                .and().assertThat().body("order.ingredients", notNullValue())
                .and().statusCode(SC_OK);
    }
    @Test
    @DisplayName("Check create order without authorization user and with ingredient")
    public void createOrderWithoutAuthWithIngredientTest(){
        response = createOrder.createOrderWithoutAuthWithIngredient();
        response.then()
                .assertThat().body("success", equalTo(true))
                .and().assertThat().body("order.number", notNullValue())
                .and().statusCode(SC_OK);
    }
    @Test
    @DisplayName("Check create order with authorization user and without ingredient")
    public void createOrderWithAuthWithoutIngredientTest(){
        response = createOrder.createOrderWithAuthWithoutIngredient(user);
        userAction.deleteUser(userAction.login(user));
        response.then()
                .assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo(CREATE_ORDER_WITHOUT_INGREDIENT_ERROR))
                .and().statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Check create order without authorization user and without ingredient")
    public void createOrderWithoutAuthWithoutIngredientTest(){
        response = createOrder.createOrderWithoutAuthWithoutIngredient();
        response.then()
                .assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo(CREATE_ORDER_WITHOUT_INGREDIENT_ERROR))
                .and().statusCode(SC_BAD_REQUEST);
    }
    @Test
    @DisplayName("Check create order without authorization user and with wrong ingredient")
    public void createOrderWithoutAuthWithWrongIngredientTest(){
        response = createOrder.createOrderWithoutAuthWithWrongIngredient();
        response.then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
    @Test
    @DisplayName("Check create order with authorization user and with wrong ingredient")
    public void createOrderWithAuthWithWrongIngredientTest(){
        response = createOrder.createOrderWithAuthWithWrongIngredient(user);
        userAction.deleteUser(userAction.login(user));
        response.then().statusCode(SC_INTERNAL_SERVER_ERROR);
    }
}

