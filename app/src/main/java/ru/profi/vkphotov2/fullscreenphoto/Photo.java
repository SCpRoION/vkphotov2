package ru.profi.vkphotov2.fullscreenphoto;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Фотография профиля пользователя
 * Created by Kamo Spertsyan on 24.02.2017.
 */
public class Photo {
    public HashMap<Integer, String> urls = new HashMap<>();         // Ссылки на фотографию (ширина - ссылка)
    public HashMap<String, Bitmap> cache = new HashMap<>();         // Уже загруженные изображения

    /**
     * Получит ссылку на первую фотографию, большее по размерам указанной ширины
     * @param width нижняя граница ширины объекта
     * @return ссылка на фотографию
     */
    public String getFirstHigher(int width)
    {
        int firstHigher = -1;
        for (Map.Entry<Integer, String> url : urls.entrySet()) {
            int cursize = url.getKey();
            if (cursize >= width && (firstHigher == -1 || firstHigher != -1 && cursize < firstHigher)) {
                firstHigher = cursize;
            }
        }
        return firstHigher == -1 ? null : urls.get(firstHigher);
    }

    /**
     * Получить ссылку на фотографию в самом большом размере
     * @return ссылка на фотографию
     */
    public String getBiggest()
    {
        Set<Integer> keys = urls.keySet();
        int maxSize = -1;
        for (int key : keys) {
            maxSize = (maxSize < key ? key : maxSize);
        }
        if (maxSize == -1) {
            return null;
        }
        return urls.get(maxSize);
    }

    /**
     * Добавить загруженное изображение в кеш
     * @param url ссылка на изображение
     * @param img само изображение
     */
    public void addToCache(String url, Bitmap img) {
        cache.put(url, img);
    }

    /**
     * Получить закешированное изображение
     * @param url ссылка на изображение
     * @return закешированное изображение, если оно есть, или null
     */
    public Bitmap getCached(String url) {
        if (cache.containsKey(url)) {
            return cache.get(url);
        }
        return null;
    }
}
