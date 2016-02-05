package com.hotgirl.zeptomobile.hotgirl;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.hotgirl.zeptomobile.hotgirl.staggeredgridapp.StaggeredDemoApplication;
import com.hotgirl.zeptomobile.hotgirl.utils.SharedPreference;
import com.hotgirl.zeptomobile.hotgirl.utils.TouchImageView;

/**
 * Created by beesian on 18/01/2016.
 */
public class FullScreenImageActivity extends Activity {

    ViewPager viewPager;
    ImageView favBtn;
    private SharedPreference sharedPreference;
    Activity context = this;


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen_image); //TBS

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent i = getIntent();
        int position = i.getIntExtra("position", 0);
        String[] arr=i.getStringArrayExtra("array");

        sharedPreference = new SharedPreference();

        viewPager = (ViewPager) findViewById(R.id.slider);
        viewPager.setAdapter(new GalleryViewPagerAdapter(this, arr, position));
        viewPager.setCurrentItem(position);


    }


    class GalleryViewPagerAdapter extends PagerAdapter {

        private Context context;
        LayoutInflater inflater;
        private String[] imageArrayList;
        private  int mPosition;
        private ProgressDialog mProgress;


        public GalleryViewPagerAdapter(Context _context, String[] imageArrayList, int position) {
            context = _context;
            this.imageArrayList = imageArrayList;
            this.mPosition=position;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return imageArrayList.length;
        }


        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            showProgress();

            favBtn = (ImageView) findViewById(R.id.btn_favourite);

            SharedPreferences settings;
            String favoriteUrl;

            settings = context.getSharedPreferences("PREFS_FAV_URL", Context.MODE_PRIVATE);
            favoriteUrl = settings.getString("PREFS_URL_KEY", null);

            if(imageArrayList[position].equals(favoriteUrl))
            {
                 favBtn.setColorFilter(Color.argb(255, 249, 0, 0));//red
             }
            else
             {
                 favBtn.setColorFilter(Color.argb(255, 192, 192, 192));
             }

            favBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    SharedPreferences settings;
                    String favoriteUrl;
                    settings = context.getSharedPreferences("PREFS_FAV_URL", Context.MODE_PRIVATE);
                    favoriteUrl = settings.getString("PREFS_URL_KEY", null);

                    if(imageArrayList[position].equals(favoriteUrl)){
                        sharedPreference.removeValue(context);
                        favBtn.setColorFilter(Color.argb(255, 192, 192, 192));//grey

                    } else {
                        sharedPreference.save(context, imageArrayList[mPosition]);
                        Toast.makeText(context,
                                "Added to Favourite!",
                                Toast.LENGTH_SHORT).show();
                        favBtn.setColorFilter(Color.argb(255, 249, 0, 0));//red
                    }


                }


            });

            View photoRow = inflater.inflate(R.layout.item_fullscreen_image, container,
                    false);

            TouchImageView fullScreenImg;
            fullScreenImg =(TouchImageView)photoRow.findViewById(R.id.img_flickr);

           // added imageloader for better performance
            StaggeredDemoApplication.getImageLoader().get(imageArrayList[position],
                    ImageLoader.getImageListener(fullScreenImg, R.drawable.bg_no_image, android.R.drawable.ic_dialog_alert), container.getWidth(), 0);
            ((ViewPager) container).addView(photoRow);
            stopProgress();

            return photoRow;

        }

        private void stopProgress() {

            mProgress.cancel();
        }

        private void showProgress() {
            mProgress = ProgressDialog.show(FullScreenImageActivity.this, "", "Loading...");
        }


        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((RelativeLayout) object);

        }

    }
    @Override
    public void onResume() {

        super.onResume();
    }

}