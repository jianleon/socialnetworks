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
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.instagram.instagramapi.engine.InstagramEngine;
import com.kogi.socialnetworks.R;
import com.kogi.socialnetworks.Utils.Configuration;
import com.kogi.socialnetworks.Utils.Helpers;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    MenuItem logoutInstagram;
    MenuItem logoutTwitter;

    /**
     * Inicializador de componentes de la UI
     */
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

        logoutInstagram = navigationView.getMenu().findItem(R.id.nav_logout_instagram);
        logoutTwitter= navigationView.getMenu().findItem(R.id.nav_logout_twitter);

        if (!Helpers.getBooleanPreference(getApplicationContext(),"INSTAGRAM_IS_LOGGED"))
            logoutInstagram.setVisible(false);

        if (!Helpers.getBooleanPreference(getApplicationContext(),"TWITTER_IS_LOGGED"))
            logoutTwitter.setVisible(false);

        if (Configuration.loadView.equals("instagram") && Helpers.getBooleanPreference(getApplicationContext(),"INSTAGRAM_IS_LOGGED")) {
            Helpers.replaceFragment(this, R.id.content_main, new InstagramFragment());
        } else if (Configuration.loadView.equals("twitter") && Helpers.getBooleanPreference(getApplicationContext(),"TWITTER_IS_LOGGED")) {
            Helpers.replaceFragment(this, R.id.content_main, new TwitterFragment());
        } else
            Helpers.replaceFragment(this, R.id.content_main, new LoginFragment());
    }

    /**
     * Funcionalidad del botón 'Back'. Cuando el Drawer está abierto, lo cierra.
     * De lo contrario, hace la funcionalidad del sistema por defecto.
     */
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
     * Cambio de Fragments desde el NavView y cerrado de sesiones
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_twitter && Helpers.getBooleanPreference(getApplicationContext(), "TWITTER_IS_LOGGED")) {
            Helpers.replaceFragment(this, R.id.content_main, new TwitterFragment());
        } else if (id == R.id.nav_instagram && Helpers.getBooleanPreference(getApplicationContext(), "INSTAGRAM_IS_LOGGED")) {
            Helpers.replaceFragment(this, R.id.content_main, new InstagramFragment());
        } else if (id == R.id.nav_logout_twitter) {
            logoutTwitter();
        } else if (id == R.id.nav_logout_instagram) {
            logoutInstagram();
        } else
            Helpers.replaceFragment(this, R.id.content_main, new LoginFragment());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Cerrado de sesión de Twitter
     */
    private void logoutTwitter() {
        if (Helpers.getBooleanPreference(this, "TWITTER_IS_LOGGED")) {
            CookieSyncManager.createInstance(this);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeSessionCookie();
            Twitter.getSessionManager().clearActiveSession();
            Twitter.logOut();
            Helpers.setBooleanPreference(this, "TWITTER_IS_LOGGED", false);
            Helpers.replaceFragment(this, R.id.content_main, new LoginFragment());
            logoutTwitter.setVisible(false);
        }
    }

    /**
     * Cerrado de sesión de Instagram
     */
    private void logoutInstagram() {
        if (Helpers.getBooleanPreference(this, "INSTAGRAM_IS_LOGGED")) {
            InstagramEngine.getInstance(this).logout(this, RESULT_OK);
            Helpers.setBooleanPreference(this, "INSTAGRAM_IS_LOGGED", false);
            Helpers.replaceFragment(this, R.id.content_main, new LoginFragment());
            logoutInstagram.setVisible(false);
        }
    }

    /**
     * Obtiene la respuesta de la autenticación de twitter e inicia los componentes
     * de la sesión
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        TwitterLoginButton twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        super.onActivityResult(requestCode, resultCode, data);
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }
}
