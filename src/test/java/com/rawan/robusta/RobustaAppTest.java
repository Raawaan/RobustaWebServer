package com.rawan.robusta;

import com.rawan.robusta.request.data.Body;
import com.rawan.robusta.util.RequestTestUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class RobustaAppTest {

    private RequestTestUtils requestTestUtils = new RequestTestUtils();

    @BeforeClass
    public static void setup() throws InterruptedException {
        new Thread(() -> {
                RobustaApp.main(new String[]{});
        }).start();
        Thread.sleep(5000);

    }

    @Test
    public void simpleGetRequest() throws Exception {
        String url = " /";
        String response = requestTestUtils.sendGetRequest(" /");
        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains(String.format("Hello, we don't sever%s",url)));
    }
    @Test
    public void gtRequest() throws Exception {
        String url = " /hello";
        String response = requestTestUtils.sendGetRequest(" /hello");
        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains(String.format("Hello, we don't sever%s",url)));
    }

    @Test
    public void anonymousGetDrinkRequest() throws Exception {
        String response = requestTestUtils.sendGetRequest(" /?name=Rawan");
        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("hello Rawan you didn't add any drinks yet!"));
        assertTrue(response.contains("we recommend flat white!"));
    }
    @Test
    public void anonymousGetNameRequest() throws Exception {
        String response = requestTestUtils.sendGetRequest(" /?drink=flatwhite");
        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("flatwhite is no one choice"));
    }

    @Test
    public void postRequest() throws Exception {
        Body body = new Body("Rawan", "flatwhite");
        String response = requestTestUtils.sendPostRequest(" /",body);
        assertTrue(response.contains("200 OK"));
        assertTrue(response.contains("Thanks"));
    }

    @Test
    public void postGetRequest() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        String url = " /";

        startThread(" /?drink=flatwhite",
                "flatwhite is no one choice",
                countDownLatch);

        startThread(url,
                String.format("Hello, we don't sever%s",url),
                countDownLatch);

        boolean isCompleted = countDownLatch.await(2000, TimeUnit.MILLISECONDS);
        assertTrue(isCompleted);

    }

    private void startThread(String requestUri, String responseMsg, CountDownLatch countDownLatch) {
        new Thread(() -> {
            System.out.println(String.format("Thread %s started", Thread.currentThread().getName()));
            try {
                String response = requestTestUtils.sendGetRequest(requestUri);
                assertTrue(response.contains("200 OK"));
                assertTrue(response.contains(responseMsg));
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }
            System.out.println(String.format("Thread %s will terminate", Thread.currentThread().getName()));
            countDownLatch.countDown();

        }).start();
    }
}