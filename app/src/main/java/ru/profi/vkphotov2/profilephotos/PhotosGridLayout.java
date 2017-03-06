package ru.profi.vkphotov2.profilephotos;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridLayout;

/**
 * Created by Kamo Spertsyan on 28.02.2017.
 */
public class PhotosGridLayout extends GridLayout {

    private final int COLUMN_COUNT = 4; /** Количество колонок сетки */

    public PhotosGridLayout(Context context) {
        super(context);
        setColumnCount(COLUMN_COUNT);
    }

    public PhotosGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColumnCount(COLUMN_COUNT);
    }

    public PhotosGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setColumnCount(COLUMN_COUNT);
    }
}