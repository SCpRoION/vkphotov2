package ru.profi.vkphotov2.fullscreenphoto;

import android.support.v4.app.LoaderManager;
import android.content.Context;
import android.graphics.Bitmap;

import ru.profi.vkphotov2.BasePhotoPresenter;
import ru.profi.vkphotov2.profilephotos.ProfilePhotos;

/**
 * Created by Kamo Spertsyan on 24.02.2017.
 */
public class PhotoPresenter extends BasePhotoPresenter {

    private PhotoView view;   /** Активити для отображения */

    public PhotoPresenter(PhotoView view) {
        this.view = view;
    }

    /**
     * Получить количество фотографий профиля
     * @return количество фотографий профиля
     */
    public int photoCount() {
        return ProfilePhotos.getProfilePhotoCount();
    }

    /**
     * Инициализировать фотографию
     * @param id идентификатор фотографии
     */
    public void instantiateView(int id) {
        Photo photo = ProfilePhotos.getProfilePhoto(id);
        String url = photo.getBiggest();
        uploadPhoto(url, id, view.getActiveWidth());
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    protected Context getContext() {
        return view.getContext();
    }

    @Override
    public LoaderManager getLoaderManager() {
        return view.loaderManager();
    }

    @Override
    protected void photoUploaded(Bitmap photo, int photoId) {
        view.showPhoto(photo, photoId);
    }
}
