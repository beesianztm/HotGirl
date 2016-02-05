package com.hotgirl.zeptomobile.hotgirl;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentFlickrGridImage.OnFragmentInteractionListener, ShareActionProvider.OnShareTargetSelectedListener {

    private boolean viewIsAtRecent;
    private ShareActionProvider share;
    private Intent shareIntent=new Intent(Intent.ACTION_SEND);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            displayView(R.id.recent_picture);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);

        shareIntent.setType("text/plain");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.share);
        share = new ShareActionProvider(this);
        MenuItemCompat.setActionProvider(item, share);


        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.share) {
            onShareAction();
            return true;
        }
        else
        if (id==R.id.info){
            AlertDialog alertDialog = new AlertDialog.Builder(
                    MainActivity.this).create();

            // Setting Dialog Title
            alertDialog.setTitle("App info");

            // Setting Dialog Message
            alertDialog.setMessage("App Version: 1.0");


            // Setting OK Button
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                }
            });

            // Showing Alert Message
            alertDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onShareAction() {
        String yourShareText = "test";
        Intent shareIntent = ShareCompat.IntentBuilder.from(this).setType("text/plain").setText(yourShareText).getIntent();
        // Set the share Intent
        if (share != null) {
            share.setShareIntent(shareIntent);
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        displayView(item.getItemId());
        return true;
    }

    private void displayView(int itemId) {
        Fragment fragment = null;
        String title="";

        switch (itemId){
        case R.id.recent_picture:
            fragment = new FragmentFlickrGridImage("Asian beauties,bikinimodel,lingeriemodel");
            title="Recent Hot Girl";
            viewIsAtRecent=true;
            break;
        case R.id.hot_girl:
            fragment = new FragmentFlickrGridImage("bikinimodel,lingeriemodel");
            title ="Hot Girl";
            viewIsAtRecent=false;
            break;
        case R.id.asian_girl:
            fragment = new FragmentFlickrGridImage("Asian beauties");
            title="Asian Girl";
            viewIsAtRecent=false;
            break;

        case R.id.favourite_girl:
            fragment=new FavouriteListFragment();
            title="Favourite";
            viewIsAtRecent=false;
        break;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        getSupportActionBar().setTitle(title);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtRecent) { //if the current view is not the Recent Hot Girl fragment
           displayView(R.id.recent_picture);

        } else {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener)
                    .setCancelable(false)
                    .setTitle("Exit");
            builder.show();

        }
    }

    @Override
    public boolean onShareTargetSelected(ShareActionProvider source,
                                         Intent intent) {
        Toast.makeText(this, intent.getComponent().toString(),
                Toast.LENGTH_LONG).show();

        return(false);
    }
}