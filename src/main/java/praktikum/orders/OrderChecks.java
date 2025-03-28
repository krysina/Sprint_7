package praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import java.net.HttpURLConnection;

public class OrderChecks {

    @Step("успешный track заказа")
    public int orderCreateSuccess(ValidatableResponse orderResponse) {
        int track = orderResponse
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_CREATED)
                .extract()
                .path("track")
                ;
        return track;
    }



}
