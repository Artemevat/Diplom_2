import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import object.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import steps.OrderSteps;
import steps.UserSteps;

import java.util.List;

import static constants.IngredientsData.*;
import static constants.UserRandomDate.*;

public class CreateOrderTest {
    private UserSteps userSteps;
    private Order order;
    private OrderSteps orderSteps;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        orderSteps = new OrderSteps();
        userSteps.createUser(EMAIL, PASSWORD, NAME);
        ValidatableResponse responseLogin = userSteps.login(EMAIL, PASSWORD);
        accessToken = userSteps.getToken(responseLogin);
    }

    @After
    public void close() {
        userSteps.deleteUserDataTests(accessToken);
    }

    //тесты с авторизацией
    @Test
    @DisplayName("Create order with token, with ingredients")
    @Description("Create order, check successful")
    public void createOderWithTokenAndIngredientsSuccessful() {
        order = new Order(List.of(BUN_ID, SAUCE_ID, FILLING_ID));
        ValidatableResponse responseCreateAuth = orderSteps.createOrderWithToken(accessToken, order);
        userSteps.checkAnswerSuccess(responseCreateAuth);
    }
    @Test
    @DisplayName("Create order with token without ingredients")
    @Description("Create order, check error")
    public void createOderWithTokenWithoutIngredientsError() {
        order = new Order();
        ValidatableResponse responseCreateAuth = orderSteps.createOrderWithToken(accessToken, order);
        orderSteps.checkAnswerWithoutIngredients(responseCreateAuth);
    }
    @Test
    @DisplayName("Create order with token, with incorrect hash")
    @Description("Create order, check error")
    public void createOderWithTokenWithIncorrectHashError() {
        order = new Order(List.of(INCORRECT_INGREDIENT));
        ValidatableResponse responseCreateAuth = orderSteps.createOrderWithToken(accessToken, order);
        orderSteps.checkAnswerWithIncorrectHash(responseCreateAuth);
    }

    //тесты без авторизации
    @Test
    @DisplayName("Create order without token, with ingredients")
    @Description("Create order, check successful")
    public void createOderWithoutTokenWithIngredientsSuccessful() {
        order = new Order(List.of(BUN_ID, SAUCE_ID, FILLING_ID));
        ValidatableResponse responseCreateAuth = orderSteps.createOrderWithoutToken(order);
        userSteps.checkAnswerSuccess(responseCreateAuth);
    }

    @Test
    @DisplayName("Create order without token, without ingredients")
    @Description("Create order, check error")
    public void createOderWithoutTokenWithoutIngredientsError() {
        order = new Order();
        ValidatableResponse responseCreateAuth = orderSteps.createOrderWithoutToken(order);
        orderSteps.checkAnswerWithoutIngredients(responseCreateAuth);
    }

    @DisplayName("Create order without token, with incorrect hash")
    @Description("Create order, check error")
    public void createOderNonAuthWithWrongHashInternalServerError() {
        order = new Order(List.of(INCORRECT_INGREDIENT));
        ValidatableResponse responseCreateAuth = orderSteps.createOrderWithoutToken(order);
        orderSteps.checkAnswerWithIncorrectHash(responseCreateAuth);
    }
}
