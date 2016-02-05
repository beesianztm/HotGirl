package com.hotgirl.zeptomobile.hotgirl.flickr;

/**
 * Created by beesian on 18/01/2016.
 */



        import android.os.Parcel;
        import android.os.Parcelable;

/**
 * Holds the data for Flickr photo that is used to display Flickr Images in ListViews.
 *
 * @author Mani Selvaraj
 *
 */
public class FlickrImage implements Parcelable {

    String id;
    String secret;
    String server;
    String farm;
    String title;
    String owner;

    public FlickrImage(String _id, String _owner, String _secret, String _server, String _title, String _farm) {
        id = _id;
        owner = _owner;
        secret = _secret;
        server = _server;
        title = _title;
        farm = _farm;
    }

    protected FlickrImage(Parcel in) {
        id = in.readString();
        secret = in.readString();
        server = in.readString();
        farm = in.readString();
        title = in.readString();
        owner = in.readString();
    }

    public static final Creator<FlickrImage> CREATOR = new Creator<FlickrImage>() {
        @Override
        public FlickrImage createFromParcel(Parcel in) {
            return new FlickrImage(in);
        }

        @Override
        public FlickrImage[] newArray(int size) {
            return new FlickrImage[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getFarm() {
        return farm;
    }

    public void setFarm(String farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getImageUrl() {
        String imageUrl = "http://farm" + getFarm() + ".static.flickr.com/" + getServer()
                + "/" + getId() + "_" + getSecret() + "_b.jpg";
        return imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(secret);
        dest.writeString(server);
        dest.writeString(farm);
        dest.writeString(title);
        dest.writeString(owner);
    }
}

