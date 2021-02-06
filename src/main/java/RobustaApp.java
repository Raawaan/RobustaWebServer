import request.Request;
import request.RobustaUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.System.out;

public class RobustaApp {
    public static void main(String[] args) {
        int portNumber = 8282;
        RobustaUtils robustaUtils = new RobustaUtils();
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     OutputStream out = clientSocket.getOutputStream()) {
                    Request request = robustaUtils.handleRequest(in);
                    robustaUtils.handleResponse(out, request);
                } catch (Exception e) {
                    out.println("Inner exception" + e.getLocalizedMessage());
                }
            }
        } catch (Exception e) {
            out.println("Exception occurs" + e.getLocalizedMessage());
        }
    }
}
