
package praktikum.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UserApiClient {

    private static final String BASE_URL =
            "https://stellarburgers.education-services.ru";

    private static final int TIMEOUT = 30000;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String accessToken;

    public int createUser(String email, String password, String name)
            throws IOException {

        URL url = new URL(BASE_URL + "/api/auth/register");

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        connection.setDoOutput(true);

        String json = objectMapper.createObjectNode()
                .put("email", email)
                .put("password", password)
                .put("name", name)
                .toString();

        try {
            byte[] data = json.getBytes(StandardCharsets.UTF_8);

            try (OutputStream output = connection.getOutputStream()) {
                output.write(data);
            }

            int statusCode = connection.getResponseCode();

            if (statusCode == HttpURLConnection.HTTP_OK) {
                try (InputStream input = connection.getInputStream()) {
                    JsonNode response = objectMapper.readTree(input);
                    accessToken = response.path("accessToken").asText(null);
                }
            } else {
                System.err.println(
                        "Ошибка создания пользователя. HTTP: " + statusCode
                );
            }

            return statusCode;

        } finally {
            connection.disconnect();
        }
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void deleteUser() throws IOException {

        if (accessToken == null || accessToken.isEmpty()) {
            return;
        }

        URL url = new URL(BASE_URL + "/api/auth/user");

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization", accessToken);
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);

        try {
            int statusCode = connection.getResponseCode();

            if (statusCode != HttpURLConnection.HTTP_ACCEPTED) {
                throw new IOException(
                        "Не удалось удалить пользователя. HTTP: "
                                + statusCode
                );
            }

            accessToken = null;

        } finally {
            connection.disconnect();
        }
    }
}

