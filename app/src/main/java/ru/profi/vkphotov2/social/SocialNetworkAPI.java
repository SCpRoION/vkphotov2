package ru.profi.vkphotov2.social;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

/**
 * API социальной сети
 * Created by Kamo Spertsyan on 23.02.2017.
 */
public abstract class SocialNetworkAPI {

    private ArrayList <AuthorizationListener> authorizationListeners;   /** Слушатели событий авторизации */
    private boolean initialized = false;                                /** Флаг пройденной инициализации API */

    public SocialNetworkAPI() {
        authorizationListeners = new ArrayList<>();
        initialize();
    }

    /**
     * Инициализировать API
     */
    private void initialize() {
        if (!initialized && tryInitialization()) {
            initialized = true;
        }
    }

    /**
     * Попробовать инициализировать API
     * @return признак успешной инициализации
     */
    protected abstract boolean tryInitialization();

    /**
     * Получить слушателя браузера
     * @return слушатель браузера, адаптированный под API
     */
    public abstract WebViewClient getWebViewClient();

    /**
     * Авторизоваться
     * @param webView браузер для авторизации
     */
    public abstract void authorize(WebView webView);

    /**
     * Загрузить фотографии пользователя
     */
    public abstract void uploadPhotos();

    /**
     * Добавить слушателя событий авторизации
     * @param al слушатель событий авторизации
     */
    public void addAuthorizationListener(AuthorizationListener al) {
        authorizationListeners.add(al);
    }

    /**
     * Сообщить слушателям об успешной авторизации
     */
    protected void fireAuthorizationSuccess() {
        for (AuthorizationListener al : authorizationListeners) {
            al.authorizationSuccess();
        }
    }

    /**
     * Сообщить слушателям об ошибке при авторизации
     */
    protected void fireAuthorizationError() {
        for (AuthorizationListener al : authorizationListeners) {
            al.authorizationError();
        }
    }
}
