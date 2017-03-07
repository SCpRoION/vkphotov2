package ru.profi.vkphotov2.profilephotos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.ArrayList;

import ru.profi.vkphotov2.PhotoPresenterBase;
import ru.profi.vkphotov2.fullscreenphoto.FullscreenPhotoActivity;
import ru.profi.vkphotov2.fullscreenphoto.Photo;
import ru.profi.vkphotov2.social.APIManager;

/**
 * Created by Kamo Spertsyan on 26.02.2017.
 */
public class ProfilePhotosPresenter extends PhotoPresenterBase {

    private ProfilePhotosView view;   /** Вьюха для отображения */

    public ProfilePhotosPresenter(ProfilePhotosView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
        // Если фотографии уже были загружены, то не надо этого делать снова
        if (ProfilePhotos.getProfilePhotoCount() > 0) {
            photoInfoUploadFinished();
            return;
        }

        // Загрузить информацию о фотографиях
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                APIManager.getAPIImpl().uploadPhotos();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                photoInfoUploadFinished();
            }
        }.execute();
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
    protected void photoUploaded(Bitmap photo, int id) {
        view.showPhoto(photo, id);
    }

    /**
     * Выбрана фотография из перечня
     * @param id идентификатор выбранной фотографии
     */
    public void photoSelected(int id) {
        Intent intent = new Intent(view.getContext(), FullscreenPhotoActivity.class);
        intent.putExtra(FullscreenPhotoActivity.EXTRA_PHOTO_ID, id);
        view.startActivity(intent);
    }

    /**
     * Загрузка информации о фотографиях проифля завершена
     */
    public void photoInfoUploadFinished() {
        ArrayList<Photo> photos = ProfilePhotos.getProfilePhotos();

        // Если нет фотографий - выдать сообщение
        if (photos.size() == 0) {
            view.showNoPhotoMessage();
            return;
        }

        // Иначе отобразить все фотографии
        view.clearGrid();
        int imageWidth = (int) (view.getGridActiveWidth() / (float) view.getGridColumnCount());
        int id = 0;
        for (Photo photo : photos) {
            String url = photo.getFirstHigher(imageWidth);
            view.addImageView(id);
            uploadPhoto(url, id++, imageWidth);
        }
    }
}
