package ru.praktikum;

import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.data.User;
import ru.praktikum.data.UserGenerator;
import ru.praktikum.order.OrderList;
import ru.praktikum.user.UserAction;


import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.praktikum.data.Const.USER_DONT_AUTH_ERROR;

public class OrderListTest {
    private User user;
    private UserAction userAction;
    private Response response;
    private OrderList orderList;

    @Before
    public void setUp() {
        user = UserGenerator.getUser();
        userAction = new UserAction();
        orderList = new OrderList();
    }

    @Test
    public void getOrderListWithAuth(){
        response = orderList.getOrderListWithAuth(user);
        userAction.deleteUser(userAction.login(user));
        response.then()
                .assertThat().body("success", equalTo(true))
                .and().assertThat().body("orders", notNullValue())
                .and().statusCode(SC_OK);
    }
    @Test
    public void getOrderListWithoutAuth(){
        response = orderList.getOrderListWithoutAuth();
        response.then()
                .assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo(USER_DONT_AUTH_ERROR))
                .and().statusCode(SC_UNAUTHORIZED);
    }

}
