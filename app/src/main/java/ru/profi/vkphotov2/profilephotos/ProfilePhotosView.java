package ru.profi.vkphotov2.profilephotos;

import android.content.Intent;

import ru.profi.vkphotov2.View;

/**
 * Created by Kamo Spertsyan on 07.03.2017.
 */
public interface ProfilePhotosView extends View {

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
     * Добавить форму для добавления фотографии
     * @param id идентификатор объекта
     */
    void addImageView(int id);
}
