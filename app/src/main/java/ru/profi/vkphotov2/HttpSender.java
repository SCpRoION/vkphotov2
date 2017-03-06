package ru.profi.vkphotov2;

import android.util.Log;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Адаптер HTTP запросов
 * Created by Kamo Spertsyan on 24.02.2017.
 */
public class HttpSender {
    private static OkHttpClient client = new OkHttpClient();

    /**
     * Выполнить GET-запрос
     * @param url ссылка
     * @return ответ сервера
     */
    public static String getRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (IOException e) {
            Log.e("GET REQUEST EXCEPTION", e.getMessage());
        }

        return null;
    }
}
