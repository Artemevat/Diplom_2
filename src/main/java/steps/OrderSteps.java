package steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import object.Order;
import org.junit.Assert;

import static constants.OrderAPI.ORDERS_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class OrderSteps extends Specification {

    @Step("Create order with token")
    public ValidatableResponse createOrderWithToken(String accessToken, Order order) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDERS_URL)
                .then().log().all();
    }
    @Step("Create order without token")
    public ValidatableResponse createOrderWithoutToken(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(ORDERS_URL)
                .then().log().all();
    }

    @Step("Get list orders without token")
    public ValidatableResponse getListOrdersWithoutToken() {
        return given()
                .spec(getSpec())
                .body("")
                .when()
                .get(ORDERS_URL)
                .then().log().all();
    }

    @Step("Get list orders with token")
    public ValidatableResponse getListOrdersWithToken(String accessToken) {
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .body("")
                .when()
                .get(ORDERS_URL)
                .then().log().all();
    }

    @Step("answer create order without ingredients")
    public void checkAnswerWithoutIngredients(ValidatableResponse validatableResponse) {
        validatableResponse
                .body("success", is(false))
                .statusCode(400);
        String actualMessage = validatableResponse.extract().path("message").toString();
        Assert.assertEquals("Ingredient ids must be provided", actualMessage);
    }

    @Step("answer create order with incorrect ingredient hash")
    public void checkAnswerWithIncorrectHash(ValidatableResponse validatableResponse) {
        validatableResponse
                .statusCode(500);
    }

    @Step("answer create order without token")
    public void checkAnswerGetListWithoutToken(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .body("success", is(false))
                .and().statusCode(401);
        String actualMessage = validatableResponse.extract().path("message").toString();
        Assert.assertEquals("You should be authorised", actualMessage);
    }

}
