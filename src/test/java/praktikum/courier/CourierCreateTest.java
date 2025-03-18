package praktikum.courier;

import static org.junit.Assert.*;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import praktikum.EnvConfig;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.equalTo;

public class CourierCreateTest {

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
    public void createCourierTest(){
        var courier = Courier.random();
        ValidatableResponse createResponse = client.createCourier(courier);
        check.created(createResponse);

        var creds = Credentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.loginSuccess(loginResponse);

        assertNotEquals(0, courierId);

    }

    @Test
    public void createDoubleCourierTest(){
        var courier = Courier.random();
        client.createCourier(courier);
        client.createCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
        var creds = Credentials.fromCourier(courier);
        ValidatableResponse loginResponse = client.logIn(creds);
        courierId = check.loginSuccess(loginResponse);

        assertNotEquals(0, courierId);
    }

    @Test
    public void createCourierWithoutLoginTest(){
        var courier = new Courier("", EnvConfig.PASS, EnvConfig.NAME);
        client.createCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void createCourierWithoutPassTest(){
        var courier = new Courier(EnvConfig.LOGIN, "", EnvConfig.NAME);
        client.createCourier(courier)
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


}