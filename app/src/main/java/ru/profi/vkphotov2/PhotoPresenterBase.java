package ru.profi.vkphotov2;

import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.content.AsyncTaskLoader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;

import ru.profi.vkphotov2.fullscreenphoto.Photo;
import ru.profi.vkphotov2.profilephotos.ProfilePhotos;

/**
 * Created by Kamo Spertsyan on 26.02.2017.
 */
public abstract class PhotoPresenterBase implements LoaderManager.LoaderCallbacks<Bitmap> {

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
     * @param finalSize ширина конечного изображения
     */
    protected void uploadPhoto(String url, int id, int finalSize) {
        Bundle args = new Bundle();
        args.putString("url", url);
        args.putInt("size", finalSize);
        args.putInt("id", id);
        Loader<Bitmap> loader = getLoaderManager().initLoader(uniqueLoaderId(id, finalSize), args, this);
        loader.forceLoad();
    }

    private int uniqueLoaderId(int photoId, int size) {
        return (photoId << 8) + size;
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        UploadPhotoTask loader = new UploadPhotoTask(getContext(), args.getInt("id"), args.getInt("size"), args.getString("url"));
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> loader, Bitmap data) {
        int photoId = ((UploadPhotoTask)loader).photoId;
        photoUploaded(data, photoId);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {

    }

    protected abstract Context getContext();

    public abstract LoaderManager getLoaderManager();

    /**
     * Загрузка фотографии завершена
     * @param photo загруженная фотография
     * @param photoId идентификатор загруженной фотографии
     */
    protected abstract void photoUploaded(Bitmap photo, int photoId);

    private static class UploadPhotoTask extends AsyncTaskLoader<Bitmap> {

        private int photoId;                    /** Идентификатор загружаемой фотографии */
        private int size;                       /** Размер конечного изображения */
        private String url;                     /** Ссылка на скачивание */

        public UploadPhotoTask(Context context, int photoId, int size, String url) {
            super(context);
            this.photoId = photoId;
            this.size = size;
            this.url = url;
        }

        @Override
        public Bitmap loadInBackground() {
            Bitmap img = null;
            try {
                // Попытаться получить закешированное изображение по ссылке
                Photo photo = ProfilePhotos.getProfilePhoto(photoId);
                img = photo.getCached(url + size);
                // Если закешированного изображения нет, то загрузить его
                if (img == null) {
                    // Загружаем размеры изображения
                    InputStream in = new java.net.URL(url).openStream();
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
                    in = new java.net.URL(url).openStream();
                    options.inJustDecodeBounds = false;
                    img = BitmapFactory.decodeStream(in, null, options);
                    photo.addToCache(url + size, img);
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
    }
}
