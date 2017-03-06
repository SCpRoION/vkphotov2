package ru.profi.vkphotov2.fullscreenphoto;

import android.graphics.Bitmap;

import ru.profi.vkphotov2.PhotoPresenterBase;
import ru.profi.vkphotov2.profilephotos.ProfilePhotos;

/**
 * Created by Kamo Spertsyan on 24.02.2017.
 */
public class PhotoPresenter extends PhotoPresenterBase {

    private FullscreenPhotoActivity view;   /** Активити для отображения */

    public PhotoPresenter(FullscreenPhotoActivity view) {
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
    protected void photoUploaded(Bitmap photo, int photoId) {
        view.showPhoto(photo, photoId);
    }
}