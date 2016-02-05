package com.hotgirl.zeptomobile.hotgirl.flickr;

/**
 * Created by beesian on 18/01/2016.
 */


import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

public class FlickrGetImagesResponse {

    public String id;

    List<FlickrImage> photo;

    public FlickrGetImagesResponse(Parcel in) {

        id = in.readString();
        this.photo = new ArrayList<FlickrImage>();

    }

    public List<FlickrImage> getPhotos() {
        return photo;
    }


}

