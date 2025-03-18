package praktikum.courier;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.EnvConfig;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

public class CourierLoginTest {

    private final CourierClient client = new CourierClient();
    private final CourierChecks check = new CourierChecks();
    private int courierId;

    @After
    public void deleteCourier() {
        if (courierId > 0) {
            client.deleteCourier(courierId);
        }
    }

    @Test
    public void loginSuccessTest(){
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        var creds = Credentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.loginSuccess(loginResponse);

        assertNotEquals(0, courierId);
    }

    @Test
    public void loginNonexistentTest(){
        var creds = new Credentials (EnvConfig.LOGIN,EnvConfig.PASS);
        client.logIn(creds)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_NOT_FOUND)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void loginWithoutLoginTest(){
        var creds = new Credentials ("",EnvConfig.PASS);
        client.logIn(creds)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void loginWithoutPassTest(){
        var creds = new Credentials (EnvConfig.LOGIN,"");
        client.logIn(creds)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

}