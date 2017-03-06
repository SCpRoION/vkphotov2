package ru.profi.vkphotov2.profilephotos;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ru.profi.vkphotov2.R;

public class ProfilePhotosActivity extends AppCompatActivity implements View.OnClickListener {

    private ProfilePhotosPresenter presenter;
    private PhotosGridLayout grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_grid);

        grid = (PhotosGridLayout) findViewById(R.id.preview_grid);

        presenter = new ProfilePhotosPresenter(this);
        presenter.activityCreated();
    }

    /**
     * Показать сообщение об отсутствии фотографий
     */
    public void showNoPhotoMessage() {
        showMessage(R.string.no_photo_message);
    }

    /**
     * Очистить сетку фотографий
     */
    public void clearGrid() {
        grid.removeAllViews();
    }

    /**
     * Узнать размер активной области сетки в пикселях
     * @return размер активной области сетки в пикселях
     */
    public int getGridActiveWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels -
                grid.getPaddingLeft() - grid.getPaddingRight();
    }

    /**
     * Узнать количество колонок сетки
     * @return количество колонок сетки
     */
    public int getGridColumnCount() {
        return grid.getColumnCount();
    }

    /**
     * Добавить форму для добавления фотографии
     * @param id идентификатор объекта
     */
    public void addImageView(int id) {
        ImageView imageView = new ImageView(ProfilePhotosActivity.this);
        imageView.setId(id);
        imageView.setOnClickListener(this);
        grid.addView(imageView);
    }

    /**
     * Показать фотографию с заданным идентификатором
     * @param photo фотография
     * @param id идентификатор фотографии
     */
    public void showPhoto(Bitmap photo, int id) {
        ImageView imageView = (ImageView) findViewById(id);
        imageView.setImageBitmap(photo);
    }

    @Override
    public void onClick(View v) {
        presenter.photoSelected(v.getId());
    }

    /**
     * Вывести сообщение с информацией
     * @param msgId идентификатор сообщения
     */
    private void showMessage(int msgId) {
        TextView text = new TextView(ProfilePhotosActivity.this);
        text.setText(msgId);
        grid.addView(text);
    }
}
