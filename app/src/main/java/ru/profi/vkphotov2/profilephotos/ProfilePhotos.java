package ru.profi.vkphotov2.profilephotos;

import java.util.ArrayList;

import ru.profi.vkphotov2.fullscreenphoto.Photo;

/**
 * Пользователь социальной сети
 * Created by Kamo Spertsyan on 24.02.2017.
 */
public class ProfilePhotos {

    private static ArrayList<Photo> profilePhotos = new ArrayList<>();  /** Фотографии профиля */

    /**
     * Добавить фотографию профиля
     * @param photo фотография
     */
    public static void addProfilePhoto(Photo photo) {
        profilePhotos.add(photo);
    }

    /**
     * Получить фотографии профиля пользователя
     * @return фотографии профиля пользователя
     */
    public static ArrayList<Photo> getProfilePhotos() {
        return (ArrayList<Photo>) profilePhotos.clone();
    }

    /**
     * Получить фотографию профиля пользователя по индексу
     * @param ind индекс фотографии
     * @return фотография профиля пользователя
     */
    public static Photo getProfilePhoto(int ind) {
        return profilePhotos.get(ind);
    }

    /**
     * Узнать количество фотографий профиля пользователя
     * @return количество фотографий
     */
    public static int getProfilePhotoCount() {
        return profilePhotos.size();
    }
}
