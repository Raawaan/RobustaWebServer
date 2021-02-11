package util;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RequestTestUtils {

    public String sendRequest(String requestUri) throws IOException {

        String hostName = "localhost";
        int portNumber = 8282;
        StringBuilder requestBuilder = new StringBuilder();

        Socket s = new Socket(hostName, portNumber);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeBytes(requestUri+" HTTP/1.1\n");
            dout.writeBytes("Host: localhost:8282\n");
            dout.writeBytes("Connection: keep-alive\n");
            dout.writeBytes("\n");
            dout.flush();
            String fromServer;

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()))) {

                while ((fromServer = in.readLine()) != null) {
                    requestBuilder.append(fromServer).append("\r\n");
                    if (fromServer.equals("\r\n"))
                        break;
                }
            } catch (Exception e) {
//                log.error(e.getLocalizedMessage());
            }
        return requestBuilder.toString();
    }
}