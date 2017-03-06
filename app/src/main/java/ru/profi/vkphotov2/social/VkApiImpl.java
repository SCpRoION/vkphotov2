package ru.profi.vkphotov2.social;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import ru.profi.vkphotov2.HttpSender;
import ru.profi.vkphotov2.fullscreenphoto.Photo;
import ru.profi.vkphotov2.profilephotos.ProfilePhotos;

/**
 * API ВКонтакте
 * Created by Kamo Spertsyan on 23.02.2017.
 */
public class VkApiImpl extends SocialNetworkAPI {

    private final String appId = "5860116";                     /** Идентификатор приложения */
    private final String appSecret = "ZOA9qdFWl51YSdSEDX7J";    /** Секретный ключ приложения */
    private final String redirectUri = "http://profi.ru/";      /** Ссылка для переадресации при авторизации */
    private final String apiVersion = "5.62";                   /** Версия используемого API */
    private final String accessTokenKeyWord = "access_token";   /** Ключевое название токена доступа при авторизации */
    private final String errorKeyWord = "error";                /** Ключевое название ошибки при авторизации */
    private static ArrayList<String> sizes;                     /** Возможные ключи размеров в JSON */
    static {
        sizes = new ArrayList<>();
        sizes.add("photo_75");
        sizes.add("photo_130");
        sizes.add("photo_604");
        sizes.add("photo_808");
        sizes.add("photo_1280");
    }

    private String accessToken;                                 /** Токен доступа авторизованного пользователя */

    @Override
    protected boolean tryInitialization() {
        // Инициализация не требуется
        return true;
    }

    @Override
    public WebViewClient getWebViewClient() {
        return new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                if (url.contains(errorKeyWord)) {
                    fireAuthorizationError();
                }
                else if (url.contains(accessTokenKeyWord)) {
                    parseAccessToken(url);
                    fireAuthorizationSuccess();
                }
            }
        };
    }

    @Override
    public void authorize(WebView webView) {
        webView.setWebViewClient(getWebViewClient());
        // Вывести диалог авторизации
        String authUrl = ("https://oauth.vk.com/authorize?client_id=%1&redirect_uri=%2&scope=photos&response_type=token&v=" + apiVersion)
                .replace("%1", appId)
                .replace("%2", redirectUri);
        webView.loadUrl(authUrl);
    }

    @Override
    public void uploadPhotos() {
        // Загрузить информацию о фотографиях
        String url = ("https://api.vk.com/method/photos.get?album_id=profile&access_token=%1&v=" + apiVersion)
                .replace("%1", accessToken);
        String response = HttpSender.getRequest(url);
        // Распарсить информацию о фотографиях
        try {
            parsePhotos(new JSONObject(response));
        }
        catch (JSONException e)
        {
            handleJSONException(e);
        }
    }

    /**
     * Распарсить список фотографий
     * @param json информация о фотографиях в формате JSON
     */
    public static void parsePhotos(JSONObject json) throws JSONException
    {
        JSONArray array = json.getJSONObject("response").getJSONArray("items");
        for (int i = 0; i < array.length(); ++i)
        {
            ProfilePhotos.addProfilePhoto(parseSinglePhoto(array.getJSONObject(i)));
        }
    }

    /**
     * Распарсить одну фотографию
     * @param json информация о фотографии в формате JSON
     * @return распарсенная фотография
     */
    private static Photo parseSinglePhoto(JSONObject json) throws JSONException
    {
        String sizeKeyBeginning = "photo_";
        Photo photo = new Photo();
        Iterator<String> sizes = json.keys();
        while (sizes.hasNext()) {
            // Попытаться получить ссылку на фотографию в текущем размере
            String size = sizes.next();
            if (size.startsWith(sizeKeyBeginning)) {
                String url = json.getString(size);
                int sizeValue = Integer.parseInt(size.substring(sizeKeyBeginning.length()));
                photo.urls.put(sizeValue, url);
            }
        }

        return photo;
    }

    /**
     * Обработать исключение при работе с JSON
     * @param e исключение
     */
    public static void handleJSONException(JSONException e)
    {
        Log.e("JSONParsingException", "Exception while JSON parsing: " + e.getMessage());
    }

    /**
     * Распарсить токен из ссылки
     * @param url ссылка перенаправления, содержащая токен
     */
    private void parseAccessToken(String url) {
        String[] params = url.split("#")[1].split("&");
        // Обычно access_token - первый аргумент, но мало ли.
        for (String keyValuePair : params) {
            if (keyValuePair.startsWith(accessTokenKeyWord)) {
                accessToken = keyValuePair.split("=")[1];
                Log.d("ACCESS TOKEN", accessToken);
                break;
            }
        }
    }
}
