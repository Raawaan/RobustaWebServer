import org.junit.Before;
import org.junit.Test;
import util.RequestTestUtils;

import static org.junit.Assert.assertTrue;

public class RobustaAppTest {

    private RequestTestUtils requestTestUtils = new RequestTestUtils();

    @Before
    public void setup() {
        new Thread(() -> RobustaApp.main(new String[]{})).start();
    }

    @Test
    public void simpleGetRequest() throws Exception {
        String response = requestTestUtils.sendRequest("GET /");
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
        assertTrue(response.contains("400 Bad request"));
        assertTrue(response.contains("Sorry we serve 'GET' requests only!"));
    }
}