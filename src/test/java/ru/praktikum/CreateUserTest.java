package ru.praktikum;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.data.User;
import ru.praktikum.user.UserAction;
import ru.praktikum.data.UserGenerator;
import ru.praktikum.user.UserCreate;

import static org.hamcrest.CoreMatchers.equalTo;
import static ru.praktikum.data.Const.CREATE_THE_SAME_USER_ERROR;
import static ru.praktikum.data.Const.CREATE_USER_WITHOUT_FIELD_ERROR;
import static org.apache.http.HttpStatus.*;

public class CreateUserTest {
    private User user;
    private UserAction userAction;
    private UserCreate createUser;
    private Response response;

    @Before
    public void setUp() {
        user = UserGenerator.getUser();
        userAction = new UserAction();
        createUser = new UserCreate();
    }

    @Test
    public void createNewUserTest(){
        response = createUser.createUser(user);
        response.then()
                .assertThat().body("success", equalTo(true))
                .and().statusCode(SC_OK)
                .and().assertThat().body("user.email", equalTo(user.getEmail()))
                .and().assertThat().body("user.name", equalTo(user.getName()));
    }

    @Test
    public void createTheSameUserTest() {
        response = createUser.createUser(user);
        Response response2 = createUser.createUser(user);
        response2.then()
                .assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo(CREATE_THE_SAME_USER_ERROR))
                .and().statusCode(SC_FORBIDDEN);
    }
    @Test
    public void createUserWithoutPassowordTest(){
        response = createUser.createUserWithoutPassoword(user);
        response.then()
                .assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo(CREATE_USER_WITHOUT_FIELD_ERROR))
                .and().statusCode(SC_FORBIDDEN);
    }
    @Test
    public void createUserWithoutEmailTest(){
        response = createUser.createUserWithoutEmail(user);
        response.then()
                .assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo(CREATE_USER_WITHOUT_FIELD_ERROR))
                .and().statusCode(SC_FORBIDDEN);
    }
    @Test
    public void createUserWithoutNameTest(){
        response = createUser.createUserWithoutName(user);
        response.then()
                .assertThat().body("success", equalTo(false))
                .and().assertThat().body("message", equalTo(CREATE_USER_WITHOUT_FIELD_ERROR))
                .and().statusCode(SC_FORBIDDEN);
    }

    @After
    public void tearDown(){
        if ( response.then().extract().path("success") == "true") {
            userAction.deleteUser(response);
        }
    }

}
