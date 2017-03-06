package ru.profi.vkphotov2.social;

/**
 * Слушатель результата авторизации
 * Created by Kamo Spertsyan on 23.02.2017.
 */
public interface AuthorizationListener {
    /**
     * Авторизация прошла успешно
     */
    void authorizationSuccess();

    /**
     * Во время авторизации произошла ошибка
     */
    void authorizationError();
}
