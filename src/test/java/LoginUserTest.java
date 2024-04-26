import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static constants.UserRandomDate.*;

public class LoginUserTest {

    private UserSteps userSteps;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
        ValidatableResponse responseCreate = userSteps.createUser(EMAIL, PASSWORD, NAME);
        accessToken = userSteps.getToken(responseCreate);
    }

    @After
    public void close() {
        userSteps.deleteUserDataTests(accessToken);
    }

    @Test
    @DisplayName("Successful login user")
    @Description("login user, check returns token")
    public void loginUserSuccessful() {
        ValidatableResponse responseLogin = userSteps.login(EMAIL, PASSWORD);
        userSteps.checkAnswerSuccess(responseLogin);
    }

    @Test
    @DisplayName("Error login with incorrect email")
    @Description("login with incorrect email, check error")
    public void loginUserWithIncorrectEmailError() {
        ValidatableResponse responseLogin = userSteps.login("email@yandex.ru", PASSWORD);
        userSteps.checkAnswerWithIncorrectData(responseLogin);
    }

    @Test
    @DisplayName("Error login with incorrect password")
    @Description("login with incorrect password, check error")
    public void loginUserWithIncorrectPasswordError() {
        ValidatableResponse responseLogin = userSteps.login(EMAIL, "987654");
        userSteps.checkAnswerWithIncorrectData(responseLogin);
    }
}
