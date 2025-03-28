package praktikum.orders;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertNotEquals;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    private final OrderClient client = new OrderClient();
    private final OrderChecks check = new OrderChecks();
    private int track;

    @After
    public void cancelOrder() {
        if (track > 0) {
            client.cancelOrder(track);
        }
    }

    @Parameterized.Parameter()
    public String[] color;

    @Parameterized.Parameters
    public static Object[][] getParameters() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK","GREY"}},
                {new String[]{}}
        };
    }

    @Test
    public void createOrderTest(){
        Order order = new Order("firstName","lastName","address","metro","phone",5,"2025-03-18","comment",color);
        ValidatableResponse orderResponse = client.createOrder(order);
        track = check.orderCreateSuccess(orderResponse);
        assertNotEquals(0, track);

    }

}