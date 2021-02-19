import org.junit.BeforeClass;
import org.junit.Test;
import robusta.web.server.RobustaApp;
import util.RequestTestUtils;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertTrue;

public class RobustaAppTest {

    private RequestTestUtils requestTestUtils = new RequestTestUtils();

    @BeforeClass
    public static void setup() {
        new Thread(() -> RobustaApp.main(new String[]{})).start();
    }

    @Test
    public void simpleGetRequest() throws Exception {
        String response = requestTestUtils.sendRequest("GET /");
        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("Boom!"));
    }
    @Test
    public void gtRequest() throws Exception {
        String response = requestTestUtils.sendRequest("GET /hello");
        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("Boom!"));
    }

    @Test
    public void customGetRequest() throws Exception {
        String response = requestTestUtils.sendRequest("GET /?custom-message=helloo");
        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("helloo"));
    }

    @Test
    public void postRequest() throws Exception {
        String response = requestTestUtils.sendRequest("POST /?custom-message=helloo");
        assertTrue(response.contains("400 Bad robusta.web.server.request"));
        assertTrue(response.contains("Sorry we serve 'GET' requests only!"));
    }

    @Test
    public void postGetRequest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(3);

        startThread("GET /?custom-message=helloo",
                "200 OK",
                "helloo",
                countDownLatch);

        startThread("GET /",
                "200 OK",
                "Boom!",
                countDownLatch);

        startThread("POST /?custom-message=helloo",
                "400 Bad robusta.web.server.request",
                "Sorry we serve 'GET' requests only!",
                countDownLatch);

        boolean isCompleted = countDownLatch.await(100, TimeUnit.MILLISECONDS);
        assertTrue(isCompleted);

    }

    private void startThread(String requestUri, String responseCode, String responseMsg, CountDownLatch countDownLatch) {
        new Thread(() -> {
            System.out.println(String.format("Thread %s started", Thread.currentThread().getName()));
            try {
                String response = requestTestUtils.sendRequest(requestUri);
                assertTrue(response.contains(responseCode));
                assertTrue(response.contains(responseMsg));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
            System.out.println(String.format("Thread %s will terminate", Thread.currentThread().getName()));
            countDownLatch.countDown();

        }).start();
    }
}