package ru.profi.vkphotov2.fullscreenphoto;

import android.graphics.Bitmap;

/**
 * Created by Kamo Spertsyan on 07.03.2017.
 */
public interface PhotoView {

    /**
     * Узнать ширину активной области окна
     * @return ширина активной области окна
     */
    int getActiveWidth();

    /**
     * Показать фотографию с заданным идентификатором
     * @param photo фотография
     * @param id идентификатор фотографии
     */
    void showPhoto(Bitmap photo, int id);
}
