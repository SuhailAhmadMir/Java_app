import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
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
            Path filePath = Paths.get("resources").resolve(requestPath.substring(1));  // Remove the leading slash

            if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
                filePath = Paths.get("resources/404.html");
            }

            String contentType = getContentType(filePath);

            byte[] responseBytes = Files.readAllBytes(filePath);

            exchange.getResponseHeaders().set("Content-Type", contentType);
            exchange.sendResponseHeaders(200, responseBytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(responseBytes);
            os.close();
        }

        private String getContentType(Path filePath) {
            if (filePath.toString().endsWith(".html")) {
                return "text/html";
            } else if (filePath.toString().endsWith(".css")) {
                return "text/css";
            } else if (filePath.toString().endsWith(".js")) {
                return "application/javascript";
            } else if (filePath.toString().endsWith(".png")) {
                return "image/png";
            } else if (filePath.toString().endsWith(".jpg") || filePath.toString().endsWith(".jpeg")) {
                return "image/jpeg";
            }
            return "text/plain";
        }
    }
}