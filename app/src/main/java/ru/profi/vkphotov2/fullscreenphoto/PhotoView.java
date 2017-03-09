package ru.profi.vkphotov2.fullscreenphoto;

import ru.profi.vkphotov2.View;

/**
 * Created by Kamo Spertsyan on 07.03.2017.
 */
public interface PhotoView extends View {

    /**
     * Узнать ширину активной области окна
     * @return ширина активной области окна
     */
    int getActiveWidth();
}
