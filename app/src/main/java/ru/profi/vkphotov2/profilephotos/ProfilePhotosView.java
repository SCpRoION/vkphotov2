package ru.profi.vkphotov2.profilephotos;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;

/**
 * Created by Kamo Spertsyan on 07.03.2017.
 */
public interface ProfilePhotosView {

    Context getContext();

    LoaderManager getLoaderManager();

    void startActivity(Intent intent);

    /**
     * Показать сообщение об отсутствии фотографий
     */
    void showNoPhotoMessage();

    /**
     * Очистить сетку фотографий
     */
    void clearGrid();

    /**
     * Узнать количество колонок сетки
     * @return количество колонок сетки
     */
    int getGridColumnCount();

    /**
     * Узнать размер активной области сетки в пикселях
     * @return размер активной области сетки в пикселях
     */
    int getGridActiveWidth();

    /**
     * Показать фотографию с заданным идентификатором
     * @param photo фотография
     * @param id идентификатор фотографии
     */
    void showPhoto(Bitmap photo, int id);

    /**
     * Добавить форму для добавления фотографии
     * @param id идентификатор объекта
     */
    void addImageView(int id);
}
