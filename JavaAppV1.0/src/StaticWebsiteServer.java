import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class StaticWebsiteServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", new MyHandler());
        server.start();
        System.out.println("Server started on port " + port);
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String requestPath = exchange.getRequestURI().getPath();

            String filePath = "resources" + requestPath;
            if (requestPath.equals("/")) {
                filePath = "resources/index.html";
            }

            String contentType = getContentType(filePath);

            byte[] responseBytes;
            try {
                responseBytes = Files.readAllBytes(Paths.get(filePath));
            } catch (IOException e) {
                // If file not found, serve 404.html
                filePath = "resources/404.html";
                contentType = "text/html";
                responseBytes = Files.readAllBytes(Paths.get(filePath));
            }

            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }

        private String getContentType(String filePath) {
            if (filePath.endsWith(".html")) {
                return "text/html";
            } else if (filePath.endsWith(".css")) {
                return "text/css";
            }
            return "text/plain";
        }
    }
}
