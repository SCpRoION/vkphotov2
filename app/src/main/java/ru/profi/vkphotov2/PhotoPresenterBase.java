package ru.profi.vkphotov2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

import ru.profi.vkphotov2.fullscreenphoto.Photo;
import ru.profi.vkphotov2.profilephotos.ProfilePhotos;

/**
 * Created by Kamo Spertsyan on 26.02.2017.
 */
public abstract class PhotoPresenterBase {

    /**
     * Загрузить фотографию по ссылке
     * @param url ссылка на фотографию
     * @param id идентификатор фотографии
     */
    protected void uploadPhoto(String url, int id, int finalSize) {
        new UploadPhotoTask(id, finalSize).execute(url);
    }

    /**
     * Загрузка фотографии завершена
     * @param photo загруженная фотография
     * @param photoId идентификатор загруженной фотографии
     */
    protected abstract void photoUploaded(Bitmap photo, int photoId);

    private class UploadPhotoTask extends AsyncTask<String, Void, Bitmap> {

        private int photoId;                    /** Идентификатор загружаемой фотографии */
        private int size;                       /** Размер конечного изображения */

        public UploadPhotoTask(int photoId, int size) {
            this.photoId = photoId;
            this.size = size;
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            Bitmap img = null;
            try {
                // Попытаться получить закешированное изображение по ссылке
                Photo photo = ProfilePhotos.getProfilePhoto(photoId);
                img = photo.getCached(url[0]);
                // Если закешированного изображения нет, то загрузить его
                if (img == null) {
                    InputStream in = new java.net.URL(url[0]).openStream();
                    BitmapFactory.Options finalOptions = new BitmapFactory.Options();
                    finalOptions.inSampleSize = 1;
                    img = BitmapFactory.decodeStream(in, null, finalOptions);
                    photo.addToCache(url[0], img);
                }
                img = scaleBitmap(img, size);
            } catch (Exception e) {
                Log.e("IMAGE_DOWNLOADING_ERROR", e.getMessage());
                e.printStackTrace();
            }
            return img;
        }

        /**
         * Масштабировать изображение
         * @param img битмапа
         * @param width требуемая ширина
         * @return масштабированное изображение
         */
        private Bitmap scaleBitmap(Bitmap img, int width) {
            if (img.getWidth() > width) {
                float scale = width / (float)img.getWidth();
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                return Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
            }
            return img;
        }

        @Override
        protected void onPostExecute(Bitmap img) {
            photoUploaded(img, photoId);
        }
    }
}
