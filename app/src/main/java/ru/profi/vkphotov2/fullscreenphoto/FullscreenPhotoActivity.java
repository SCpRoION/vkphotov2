package ru.profi.vkphotov2.fullscreenphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import ru.profi.vkphotov2.R;

public class FullscreenPhotoActivity extends AppCompatActivity implements PhotoView {

    public static final String EXTRA_PHOTO_ID = "photo_id";     // Ключ для передаваемого в интенте идентификатора фотографии
    public PhotoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);

        presenter = new PhotoPresenter(this);

        // Настройка свапйпинга
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ImagePagerAdapter adapter = new ImagePagerAdapter(presenter);
        viewPager.setAdapter(adapter);

        // Показать выбранную фотографию
        int photoId = getIntent().getIntExtra(EXTRA_PHOTO_ID, -1);
        viewPager.setCurrentItem(photoId);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getActiveWidth() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    @Override
    public void showPhoto(Bitmap photo, int id) {
        ImageView imageView = (ImageView) findViewById(id);
        if (imageView != null) {
            imageView.setImageBitmap(photo);
        }
    }

    private class ImagePagerAdapter extends PagerAdapter {

        PhotoPresenter presenter;

        public ImagePagerAdapter(PhotoPresenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public int getCount() {
            return presenter.photoCount();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Context context = FullscreenPhotoActivity.this;
            ImageView imageView = new ImageView(context);
            imageView.setId(position);
            container.addView(imageView, 0);
            presenter.instantiateView(position);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
}
