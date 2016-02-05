package com.hotgirl.zeptomobile.hotgirl;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.hotgirl.zeptomobile.hotgirl.staggeredgridapp.StaggeredDemoApplication;
import com.hotgirl.zeptomobile.hotgirl.flickr.FlickrGetImagesResponse;
import com.hotgirl.zeptomobile.hotgirl.flickr.FlickrImage;
import com.hotgirl.zeptomobile.hotgirl.flickr.FlickrResponsePhotos;
import com.hotgirl.zeptomobile.hotgirl.griditems.GridItem;
import com.hotgirl.zeptomobile.hotgirl.volley.GsonRequest;
import com.mani.view.StaggeredGridView;
import com.mani.view.StaggeredGridViewItem;

@SuppressLint("ValidFragment")
public class FragmentFlickrGridImage extends Fragment implements FragmentManager.OnBackStackChangedListener{
    // TODO: Rename parameter arguments, choose names that match

    public static final String ARG_ITEM_ID = "FLICKR_GRID_IMAGE";
    private StaggeredGridView mStaggeredView;
    private RequestQueue mVolleyQueue;
    private ProgressDialog mProgress;
    private int currPage=1;
    GsonRequest<FlickrResponsePhotos> gsonObjRequest;

    private RelativeLayout mListFooter;
    private boolean isLoading = false;

    private final String TAG_REQUEST = "MY_TAG";

    private StaggeredGridView.OnScrollListener scrollListener = new StaggeredGridView.OnScrollListener() {
        public void onTop() {
        }

        public void onScroll() {

        }

        public void onBottom() {
           loadMoreData();
        }
    };

    private void loadMoreData() {

        if ( isLoading )
            return;

        mListFooter.setVisibility(View.VISIBLE);
        isLoading = true;
        flickerGetImagesRequest();
    }

    private void flickerGetImagesRequest() {
        {
            String url = "https://api.flickr.com/services/rest";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("api_key", "5e045abd4baba4bbcd866e1864ca9d7b");
            //builder.appendQueryParameter("method", "flickr.interestingness.getList"); //TBS
            builder.appendQueryParameter("method", "flickr.photos.search");
            builder.appendQueryParameter("tags",mMenuChoice);
            //builder.appendQueryParameter("sort","relevance");
            builder.appendQueryParameter("format", "json");
            builder.appendQueryParameter("nojsoncallback", "1");
            builder.appendQueryParameter("per_page", "10");
            builder.appendQueryParameter("page", Integer.toString(currPage));

            gsonObjRequest = new GsonRequest<FlickrResponsePhotos>(Request.Method.GET, builder.toString(),
                    FlickrResponsePhotos.class, null, new Response.Listener<FlickrResponsePhotos>() {
                @Override
                public void onResponse(FlickrResponsePhotos response) {
                    try {
                        if(response != null) {
                            parseFlickrImageResponse(response);
                            currPage++;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("JSON parse error");
                    }

                    stopProgress();

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                    // For AuthFailure, you can re login with user credentials.
                    // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                    // In this case you can check how client is forming the api and debug accordingly.
                    // For ServerError 5xx, you can do retry or handle accordingly.
                    if( error instanceof NetworkError) {
                    } else if( error instanceof ClientError) {
                    } else if( error instanceof ServerError) {
                    } else if( error instanceof AuthFailureError) {
                    } else if( error instanceof ParseError) {
                    } else if( error instanceof NoConnectionError) {
                    } else if( error instanceof TimeoutError) {
                    }
                    //mStaggeredView.onRefreshComplete();
                    stopProgress();
                    showToast(error.getMessage());
                }
            });
            gsonObjRequest.setTag(TAG_REQUEST);
            mVolleyQueue.add(gsonObjRequest);
        }
    }

    private void parseFlickrImageResponse(FlickrResponsePhotos response) {
        FlickrGetImagesResponse photos = response.getPhotos(); //pass array of images to Picture Activity
        String[] photoUrl;
        photoUrl = new String[photos.getPhotos().size()];

        for (int index = 0; index < photos.getPhotos().size(); index++) {

            FlickrImage flkrImage = photos.getPhotos().get(index);
            photoUrl[index]=flkrImage.getImageUrl();
            StaggeredGridViewItem item = null;
            item = new GridItem(getActivity(), flkrImage,photoUrl); //pass one image of index
            mStaggeredView.addItem(item);
        }

    }

    private void showToast(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }

    private void stopProgress() {
        isLoading = false;
        mListFooter.setVisibility(View.GONE);
        mProgress.cancel();
    }
    
    // TODO: Rename and change types of parameters

    private OnFragmentInteractionListener mListener;

    private String mMenuChoice;

    public FragmentFlickrGridImage(String menuChoice) {
        // Required empty public constructor
        mMenuChoice=menuChoice;

    }

    // TODO: Rename and change types and number of parameters
    public static FragmentFlickrGridImage newInstance(int page) {
        FragmentFlickrGridImage fragment = new FragmentFlickrGridImage("recentGirl");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(mVolleyQueue==null){
            mVolleyQueue= Volley.newRequestQueue(getActivity());
        }
        mVolleyQueue = StaggeredDemoApplication.getRequestQueue(getActivity());//added this
        showProgress();

        flickerGetImagesRequest();

    }

    private void showProgress() {
        mProgress = ProgressDialog.show(getActivity(), "", "Loading...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_flickr_grid_image, container, false);
        mListFooter = (RelativeLayout) rootView.findViewById(R.id.footer);
        mStaggeredView = (StaggeredGridView) rootView.findViewById(R.id.staggeredview);
        mStaggeredView.setOnScrollListener(scrollListener);

        Toast.makeText(getActivity(),mMenuChoice,Toast.LENGTH_SHORT).show();
        return rootView;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onBackStackChanged() {


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
