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
     * Методы жизненного цикла
     */
    public abstract void onCreate();
    public abstract void onPause();
    public abstract void onResume();
    public abstract void onDestroy();

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
                img = photo.getCached(url[0] + size);
                // Если закешированного изображения нет, то загрузить его
                if (img == null) {
                    // Загружаем размеры изображения
                    InputStream in = new java.net.URL(url[0]).openStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    img = BitmapFactory.decodeStream(in, null, options);
                    int imageWidth = options.outWidth;

                    // Масштабируем ширину загружаемого изображения
                    int inSampleSize = 1;
                    if (imageWidth > size) {
                        final int halfWidth = imageWidth / 2;
                        while (halfWidth / inSampleSize >= size) {
                            inSampleSize *= 2;
                        }
                    }
                    in.close();

                    // Загружаем изображение
                    in = new java.net.URL(url[0]).openStream();
                    options.inJustDecodeBounds = false;
                    img = BitmapFactory.decodeStream(in, null, options);
                    photo.addToCache(url[0] + size, img);
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
