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

public class GetOrdersListTest {
    private UserSteps userSteps;
    private OrderSteps orderSteps;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        orderSteps = new OrderSteps();
        userSteps.createUser(EMAIL, PASSWORD, NAME);
        ValidatableResponse responseLogin = userSteps.login(EMAIL, PASSWORD);
        accessToken = userSteps.getToken(responseLogin);
        Order order = new Order(List.of(BUN_ID, SAUCE_ID, FILLING_ID));
        orderSteps.createOrderWithToken(accessToken, order);
    }

    @After
    public void close() {
        userSteps.deleteUserDataTests(accessToken);
    }

    @Test
    @DisplayName("Get list orders with token")
    @Description("Get list orders, check successful")
    public void getListOrdersWithTokenSuccessful() {
        ValidatableResponse responseGetList = orderSteps.getListOrdersWithToken(accessToken);
        userSteps.checkAnswerSuccess(responseGetList);
    }

    @Test
    @DisplayName("Get list orders without token")
    @Description("Get list orders, check error")
    public void getListOrdersWithoutTokenError() {
        ValidatableResponse responseGetList = orderSteps.getListOrdersWithoutToken();
        orderSteps.checkAnswerGetListWithoutToken(responseGetList);
    }
}
