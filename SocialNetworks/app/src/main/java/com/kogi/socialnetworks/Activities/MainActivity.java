package com.kogi.socialnetworks.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kogi.socialnetworks.R;
import com.kogi.socialnetworks.Utils.Configuration;
import com.kogi.socialnetworks.Utils.Helpers;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Configuration.loadView.equals("instagram") && Helpers.getBooleanPreference(getApplicationContext(),"INSTAGRAM_IS_LOGUED")) {
            Helpers.replaceFragment(this, R.id.content_main, new InstagramFragment());
        } else if (Configuration.loadView.equals("twitter") && Helpers.getBooleanPreference(getApplicationContext(),"TWITTER_IS_LOGUED")) {
            Helpers.replaceFragment(this, R.id.content_main, new TwitterFragment());
        } else
            Helpers.replaceFragment(this, R.id.content_main, new LoginFragment());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Cambio de Fragments desde el NavView
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_twitter) {
            if (Helpers.getBooleanPreference(getApplicationContext(), "TWITTER_IS_LOGUED"))
                Helpers.replaceFragment(this, R.id.content_main, new TwitterFragment());
            else
                Helpers.replaceFragment(this, R.id.content_main, new LoginFragment());
        } else if (id == R.id.nav_instagram) {
            if (Helpers.getBooleanPreference(getApplicationContext(), "INSTAGRAM_IS_LOGUED"))
                Helpers.replaceFragment(this, R.id.content_main, new InstagramFragment());
            else
                Helpers.replaceFragment(this, R.id.content_main, new LoginFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TwitterLoginButton loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
