import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.UserSteps;

import static constants.UserRandomDate.*;

public class CreateUserTest {

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
    @DisplayName("Successful creating new user")
    @Description("Creating with correct email/password/name, check successful create and check returns token")
    public void createUserSuccessful() {
        ValidatableResponse responseCreate = userSteps.createUser(EMAIL, PASSWORD, NAME);
        userSteps.checkAnswerSuccess(responseCreate);
        accessToken = userSteps.getToken(responseCreate);
    }

    @Test
    @DisplayName("Error creating full duplicate user")
    @Description("Creating user with duplicate email/password/name, check error")
    public void createDuplicationUserError() {
        ValidatableResponse responseCreate = userSteps.createUser(EMAIL, PASSWORD, NAME);
        accessToken = userSteps.getToken(responseCreate);
        ValidatableResponse responseDuplicate = userSteps.createUser(EMAIL, PASSWORD, NAME);
        userSteps.checkAnswerDuplicateCreateUser(responseDuplicate);
    }

    @Test
    @DisplayName("Error creating user without email")
    @Description("Creating user without email, check error")
    public void createUserWithoutEmailError() {
        ValidatableResponse responseCreate = userSteps.createUser("", PASSWORD, NAME);
        userSteps.checkAnswerNotEnoughData(responseCreate);
    }

    @Test
    @DisplayName("Error creating user without password")
    @Description("Creating user without password, check error")
    public void createUserWithoutPasswordError() {
        ValidatableResponse responseCreate = userSteps.createUser(EMAIL, "", NAME);
        userSteps.checkAnswerNotEnoughData(responseCreate);
    }

    @Test
    @DisplayName("Error creating user without name")
    @Description("Creating user without name, check error")
    public void createUserWithoutNameError() {
        ValidatableResponse responseCreate = userSteps.createUser(EMAIL, PASSWORD, "");
        userSteps.checkAnswerNotEnoughData(responseCreate);
    }
}
