package ru.praktikum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.data.User;
import ru.praktikum.data.UserCreds;
import ru.praktikum.data.UserGenerator;
import ru.praktikum.user.*;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.data.Const.CREATE_THE_SAME_USER_ERROR;
import static ru.praktikum.data.Const.LOGIN_USER_ERROR;

public class LoginUserTest {
    private User user;
    private UserAction userAction;
    private Response response;
    private String userEmail;
    private String userPassowrd;
    private UserCreate userCreate = new UserCreate();


    @Before
    public void setUp() {
        user = UserGenerator.getUser();
        userAction = new UserAction();
        userEmail = user.getEmail();
        userPassowrd = user.getPassword();
        userAction.logout(userCreate.createUser(user));
    }

    @Test
    @DisplayName("Check user authorization")
    public void loginUserTest(){
        response = userAction.login(user);
        response.then()
                .assertThat().body("success", equalTo(true))
                .and().statusCode(SC_OK)
                .and().assertThat().body("user.email", equalTo(user.getEmail()))
                .and().assertThat().body("user.name", equalTo(user.getName()));;
    }
    @Test
    @DisplayName("Check user authorization with incorrect password")
    public void loginUserWithIncorrectPasswordTest(){
        response = userAction.loginUserWithIncorrectPassword(user);
        response.then()
                .assertThat().body("success", equalTo(false))
                .and().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("message", equalTo(LOGIN_USER_ERROR));
    }
    @Test
    @DisplayName("Check user authorization with incorrect email")
    public void loginUserWithIncorrectEmailTest(){
        response = userAction.loginUserWithIncorrectEmail(user);
        response.then()
                .assertThat().body("success", equalTo(false))
                .and().statusCode(SC_UNAUTHORIZED)
                .and().assertThat().body("message", equalTo(LOGIN_USER_ERROR));
    }
    @After
    public void tearDown(){
        //Востанавлт=иваю значения полей user для его дальнейшего удаления
        user.setEmail(userEmail);
        user.setPassword(userPassowrd);
        userAction.deleteUser(userAction.login(user));
    }
}
