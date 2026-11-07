
package praktikum.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import praktikum.model.LoginUserRequest;
import praktikum.model.RegisterUserRequest;

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

    @Step("Создание пользователя через API")
    public int createUser(String email, String password, String name)
            throws IOException {

        accessToken = null;

        URL url = new URL(BASE_URL + "/api/auth/register");

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        connection.setDoOutput(true);

        RegisterUserRequest request =
                new RegisterUserRequest(email, password, name);

        String json = objectMapper.writeValueAsString(request);

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

    @Step("Авторизация пользователя через API")
    public int loginUser(String email, String password)
            throws IOException {

        accessToken = null;

        URL url = new URL(BASE_URL + "/api/auth/login");

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        connection.setDoOutput(true);

        LoginUserRequest request =
                new LoginUserRequest(email, password);

        String json = objectMapper.writeValueAsString(request);

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
                        "Ошибка авторизации пользователя. HTTP: " + statusCode
                );
            }

            return statusCode;

        } finally {
            connection.disconnect();
        }
    }

    @Step("Получение токена авторизации пользователя")
    public String getAccessToken() {
        return accessToken;
    }

    @Step("Удаление пользователя через API")
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



