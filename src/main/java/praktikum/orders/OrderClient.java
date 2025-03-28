package praktikum.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import praktikum.Client;

import java.util.Map;

public class OrderClient extends Client {

    private static final String ORDER = "orders";

    @Step("создать заказ")
    public ValidatableResponse createOrder(Order order) {
        return spec()
                .body(order)
                .when()
                .post(ORDER)
                .then().log().all();
    }

    @Step("отменить заказ")
    public ValidatableResponse cancelOrder(int track) {
        return spec()
                .body(Map.of("track", track))
                .when()
                .put(ORDER + "/cancel?track=" + track)
                .then().log().all();
    }


    @Step("получить список всех заказов")
    public ValidatableResponse getOrders() {
        return spec()
                .when()
                .get(ORDER)
                .then().log().all();
    }

}
