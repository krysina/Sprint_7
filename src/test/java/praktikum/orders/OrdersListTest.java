package praktikum.orders;

import org.junit.Test;

import java.net.HttpURLConnection;

import static org.hamcrest.Matchers.notNullValue;

public class OrdersListTest {

    private final OrderClient client = new OrderClient();

    @Test
    public void getOrdersTest(){
        client.getOrders()
                .assertThat()
                .statusCode(HttpURLConnection.HTTP_OK)
                .body("orders", notNullValue());

    }

}