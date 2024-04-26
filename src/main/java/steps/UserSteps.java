package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import object.AuthUser;
import object.Order;
import object.User;
import org.junit.Assert;


import static constants.UserAPI.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class UserSteps extends Specification {

    @Step("create new user")
    public ValidatableResponse createUser(String email, String password, String name) {
        User user = new User(email, password, name);
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(AUTH_REGISTER_USER)
                .then().log().all();

    }

    @Step("delete user")
    public void deleteUser(String accessToken) {
        given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .when()
                .delete(AUTH_USER)
                .then().log().all();
    }

    @Step("authorization with token")
    public ValidatableResponse authorizationWithToken(String accessToken, String email, String password, String name) {
        User user = new User(email, password, name);
        return given()
                .header("authorization", accessToken)
                .spec(getSpec())
                .body(user)
                .when()
                .patch(AUTH_USER)
                .then().log().all();
    }

    @Step("authorization without token")
    public ValidatableResponse authorizationWithoutToken(String email, String password, String name) {
        User user = new User(email, password, name);
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .patch(AUTH_USER)
                .then().log().all();
    }

    @Step("login user")
    public ValidatableResponse login(String email, String password) {
        AuthUser credentials = new AuthUser(email, password);
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(AUTH_LOGIN)
                .then().log().all();
    }

    @Step("get token")
    public String getToken(ValidatableResponse validatableResponse) {
        return validatableResponse.extract().path("accessToken");
    }


    //проверки

    @Step("successful create, update, get info - (success: true) , code 200")
    public void checkAnswerSuccess(ValidatableResponse validatableResponse) {
        validatableResponse
                .body("success", is(true))
                .statusCode(200);
    }

    @Step("duplicate createUser - (success: false) , code 403")
    public void checkAnswerDuplicateCreateUser(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .body("success", is(false))
                .and().statusCode(403);
        String actualMessage = validatableResponse.extract().path("message").toString();
        Assert.assertEquals("User already exists", actualMessage);
    }

    @Step("not enough data createUser - (success: false) , code 403")
    public void checkAnswerNotEnoughData(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .body("success", is(false))
                .and().statusCode(403);
        String actualMessage = validatableResponse.extract().path("message").toString();
        Assert.assertEquals("Email, password and name are required fields", actualMessage);
    }

    @Step("login user with incorrect data - (success: false) , code 401")
    public void checkAnswerWithIncorrectData(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .body("success", is(false))
                .and().statusCode(401);
        String actualMessage = validatableResponse.extract().path("message").toString();
        Assert.assertEquals("email or password are incorrect", actualMessage);
    }

    @Step("update data without token - (success: false) , code 401")
    public void checkAnswerWithoutToken(ValidatableResponse validatableResponse) {
        validatableResponse.assertThat()
                .body("success", is(false))
                .and().statusCode(401);
        String actualMessage = validatableResponse.extract().path("message").toString();
        Assert.assertEquals("You should be authorised", actualMessage);
    }

    @Step("Delete data after test")
    public void deleteUserDataTests(String accessToken) {
        if (accessToken != null) {
            deleteUser(accessToken);
        } else {
            given().spec(getSpec())
                    .when()
                    .delete(AUTH_USER);
        }
    }
}
