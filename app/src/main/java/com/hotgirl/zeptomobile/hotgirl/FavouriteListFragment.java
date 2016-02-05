package com.hotgirl.zeptomobile.hotgirl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hotgirl.zeptomobile.hotgirl.flickr.FlickrImage;
import com.hotgirl.zeptomobile.hotgirl.griditems.FavouriteGridItem;
import com.hotgirl.zeptomobile.hotgirl.griditems.GridItem;
import com.hotgirl.zeptomobile.hotgirl.utils.SharedPreference;
import com.mani.view.StaggeredGridView;
import com.mani.view.StaggeredGridViewItem;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by beesian on 28/01/2016.
 */
public class FavouriteListFragment extends Fragment {

    public static final String ARG_ITEM_ID = "favorite_list";
    private SharedPreference sharedPreference;
    private StaggeredGridView mStaggeredView;
    String text;
    String favouriteUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite_staggeredgridview, container, false);
        mStaggeredView = (StaggeredGridView) rootView.findViewById(R.id.staggeredview);
        mStaggeredView.setOnScrollListener(scrollListener);
        sharedPreference = new SharedPreference();

        text = sharedPreference.getValue(getActivity());

        sharedPreference.saveFavourite(getActivity(), text);

        String[] photoUrl;
        photoUrl = new String[10];

        if (text == null) {
                    showAlert(
                            getResources().getString(R.string.no_favorites_items),
                            getResources().getString(R.string.no_favorites_msg));
            getActivity().onBackPressed();

        }
        else {
            for (int index = 0; index < photoUrl.length; index++) {

                photoUrl[index] = text;
                StaggeredGridViewItem item;
                item = new FavouriteGridItem(getActivity(), photoUrl); //pass one image of index
                mStaggeredView.addItem(item);
            }

        }

        return rootView;

    }

    private void showAlert(String title, String message) {

        if (getActivity() != null && !getActivity().isFinishing()) {
            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                    .create();
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setCancelable(false);

            // setting OK Button
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                        }
                    });
            alertDialog.show();

        }
    }

    private StaggeredGridView.OnScrollListener scrollListener = new StaggeredGridView.OnScrollListener() {
        public void onTop() {
        }

        public void onScroll() {

        }

        public void onBottom() {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
    }
}
