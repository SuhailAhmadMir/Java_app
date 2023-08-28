static class MyHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestPath = exchange.getRequestURI().getPath();
        Path filePath = Paths.get("resources", requestPath);

        // Check if the requested file is within the 'components' or 'public' subdirectories
        if (requestPath.startsWith("/components/") || requestPath.startsWith("/public/")) {
            filePath = Paths.get("resources" + requestPath);
        } else if (!Files.exists(filePath)) {
            // If file not found, serve 404.html
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
        }
        return "text/plain";
    }
}
