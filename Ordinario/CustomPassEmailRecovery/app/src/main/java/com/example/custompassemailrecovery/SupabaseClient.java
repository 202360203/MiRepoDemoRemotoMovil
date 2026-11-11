package com.example.custompassemailrecovery;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SupabaseClient {

    public static final String URL =
            "URL";

    public static final String KEY =
            "KEY";

    public static final String REDIRECT_URL =
            "custompass://reset";

    private static final OkHttpClient client =
            new OkHttpClient();

    private static final MediaType JSON =
            MediaType.get("application/json; charset=utf-8");

    public interface ResultCallback {
        void onSuccess(JSONObject response);
        void onError(String error);
    }

    private static void request(
            String method,
            String endpoint,
            JSONObject data,
            String accessToken,
            ResultCallback callback) {

        try {
            RequestBody body = data == null ? null :
                    RequestBody.create(data.toString(), JSON);

            Request.Builder builder = new Request.Builder()
                    .url(URL + endpoint)
                    .addHeader("apikey", KEY)
                    .addHeader("Authorization", "Bearer " +
                            (accessToken == null ? KEY : accessToken));

            if (method.equals("POST")) {
                builder.post(body);
            } else if (method.equals("PUT")) {
                builder.put(body);
            }

            client.newCall(builder.build()).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    mainError(callback, e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response)
                        throws IOException {

                    String result = response.body() != null
                            ? response.body().string() : "";

                    if (response.isSuccessful()) {
                        try {
                            JSONObject json = result.isEmpty()
                                    ? new JSONObject()
                                    : new JSONObject(result);

                            new Handler(Looper.getMainLooper()).post(
                                    () -> callback.onSuccess(json));

                        } catch (Exception e) {
                            mainError(callback, "Respuesta inválida");
                        }

                    } else {
                        mainError(callback, result);
                    }

                    response.close();
                }
            });

        } catch (Exception e) {
            mainError(callback, e.getMessage());
        }
    }

    private static void mainError(
            ResultCallback callback, String error) {

        new Handler(Looper.getMainLooper()).post(
                () -> callback.onError(error));
    }

    public static void register(
            String email, String password,
            ResultCallback callback) {

        try {
            JSONObject data = new JSONObject();
            data.put("email", email);
            data.put("password", password);

            request("POST", "/auth/v1/signup",
                    data, null, callback);

        } catch (Exception e) {
            mainError(callback, e.getMessage());
        }
    }

    public static void login(
            String email, String password,
            ResultCallback callback) {

        try {
            JSONObject data = new JSONObject();
            data.put("email", email);
            data.put("password", password);

            request("POST",
                    "/auth/v1/token?grant_type=password",
                    data, null, callback);

        } catch (Exception e) {
            mainError(callback, e.getMessage());
        }
    }

    public static void recover(
            String email, ResultCallback callback) {

        try {
            JSONObject data = new JSONObject();
            data.put("email", email);

            String redirect = URLEncoder.encode(
                    REDIRECT_URL,
                    StandardCharsets.UTF_8.name());

            request("POST",
                    "/auth/v1/recover?redirect_to=" + redirect,
                    data, null, callback);

        } catch (Exception e) {
            mainError(callback, e.getMessage());
        }
    }

    public static void verifyRecovery(
            String tokenHash, ResultCallback callback) {

        try {
            JSONObject data = new JSONObject();
            data.put("token_hash", tokenHash);
            data.put("type", "recovery");

            request("POST", "/auth/v1/verify",
                    data, null, callback);

        } catch (Exception e) {
            mainError(callback, e.getMessage());
        }
    }

    public static void updatePassword(
            String accessToken,
            String password,
            ResultCallback callback) {

        try {
            JSONObject data = new JSONObject();
            data.put("password", password);

            request("PUT", "/auth/v1/user",
                    data, accessToken, callback);

        } catch (Exception e) {
            mainError(callback, e.getMessage());
        }
    }
}

