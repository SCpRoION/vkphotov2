package ru.profi.vkphotov2;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by Kamo Spertsyan on 09.03.2017.
 */
public interface View {

    Context getContext();

    LoaderManager loaderManager();

    /**
     * Показать фотографию с заданным идентификатором
     * @param photo фотография
     * @param id идентификатор фотографии
     */
    void showPhoto(Bitmap photo, int id);
}
