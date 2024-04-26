import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static constants.UserRandomDate.*;

public class UpdateUserDataTest {

    private UserSteps userSteps;
    private String accessToken;

    @Before
    public void setUp() {
        userSteps = new UserSteps();
    }

    @After
    public void close() {
        userSteps.deleteUserDataTests(accessToken);
    }

    @Test
    @DisplayName("Successful update data with authorization")
    @Description("Successful update data")
    public void updateDataWithAuthSuccessful() {
        ValidatableResponse responseCreate = userSteps.createUser(EMAIL, PASSWORD, NAME);
        userSteps.checkAnswerSuccess(responseCreate);
        ValidatableResponse responseLogin = userSteps.login(EMAIL, PASSWORD);
        userSteps.checkAnswerSuccess(responseLogin);
        accessToken = userSteps.getToken(responseLogin);
        ValidatableResponse responseUpdateWithToken = userSteps.authorizationWithToken(accessToken, "update" + EMAIL, "update" + PASSWORD, "update" + NAME);
        userSteps.checkAnswerSuccess(responseUpdateWithToken);
    }

    @Test
    @DisplayName("Error update data without authorization")
    @Description("Update data without authorization, check error")
    public void updateDataWithoutAuthError() {
        ValidatableResponse responseCreate = userSteps.createUser(EMAIL, PASSWORD, NAME);
        userSteps.checkAnswerSuccess(responseCreate);
        ValidatableResponse responseLogin = userSteps.login(EMAIL, PASSWORD);
        userSteps.checkAnswerSuccess(responseLogin);
        accessToken = userSteps.getToken(responseLogin);
        ValidatableResponse responseUpdateWithoutToken = userSteps.authorizationWithoutToken("update" + EMAIL, "update" + PASSWORD, "update" + NAME);
        userSteps.checkAnswerWithoutToken(responseUpdateWithoutToken);
    }
}
