package com.hotgirl.zeptomobile.hotgirl.griditems;

/**
 * Created by beesian on 18/01/2016.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.hotgirl.zeptomobile.hotgirl.FullScreenImageActivity;
import com.hotgirl.zeptomobile.hotgirl.R;
import com.hotgirl.zeptomobile.hotgirl.staggeredgridapp.StaggeredDemoApplication;
import com.hotgirl.zeptomobile.hotgirl.flickr.FlickrImage;
import com.mani.view.StaggeredGridViewItem;

public class GridItem extends StaggeredGridViewItem {

    private Context mContext;
    private ImageLoader mImageLoader;
    private FlickrImage mImage;
    private View mView;
    private int mHeight;
    private String[] mPhotoUrl;


    public GridItem(Context context, FlickrImage image, String[] photoUrl) {
        mImage = image;
        mContext = context;
        mImageLoader = StaggeredDemoApplication.getImageLoader();
        mPhotoUrl=photoUrl;
    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public View getView(int position, LayoutInflater inflater, ViewGroup parent) {
        // TODO Auto-generated method stub
        mView = inflater.inflate(R.layout.grid_item, null);
        final ImageView image = (ImageView) mView.findViewById(R.id.image);

        mImageLoader.get(mImage.getImageUrl(),
                ImageLoader.getImageListener(image, R.drawable.bg_no_image, android.R.drawable.ic_dialog_alert), parent.getWidth(), 0);
        image.setOnClickListener(new OnImageClickListener(position));//correct position 11,12,13..
        return mView;
    }

    @Override
    public int getViewHeight(LayoutInflater inflater, ViewGroup parent) {
        FrameLayout item_containerFrameLayout = (FrameLayout)mView.findViewById(R.id.container);
        item_containerFrameLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        mHeight = item_containerFrameLayout.getMeasuredHeight();
        return mHeight;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private class OnImageClickListener implements View.OnClickListener {
        int _position;

        public OnImageClickListener(int position) {
            this._position = position;
        }

        @Override
        public void onClick(View v) {
            Intent imageIntent = new Intent(mContext, FullScreenImageActivity.class);
            imageIntent.putExtra("position", _position); //0
            imageIntent.putExtra("array",mPhotoUrl);//check whether is old or new array
           // imageIntent.putExtra("url",mImage.getImageUrl());
            mContext.startActivity(imageIntent);
        }
    }
}

