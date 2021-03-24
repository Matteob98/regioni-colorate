package com.italianappsw.regionicolorate;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.italianappsw.regionicolorate.screens.country_map.CountryMapFragment;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView mBottomNav;
    private ConstraintLayout mInfoSnackbarMainActivity;
    private final String urlGoverno = "http://www.governo.it/";
    private Timer checkConnectionTimer;
    private Snackbar noConnectionSnackbar;
    private View parentLayout;

    @SuppressLint({"JavascriptInterface", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parentLayout = findViewById(android.R.id.content);

        mInfoSnackbarMainActivity = findViewById(R.id.infoSnackbarMainActivity);
        //Ora è reso invisibile da xml
        bottomNavSettings();

        checkInternetConnection();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().
                    replace(R.id.frameLayoutItineraryActivity, new CountryMapFragment()).commit();
        }
    }

    private void bottomNavSettings() {
        mBottomNav = findViewById(R.id.bottomNavigationMainActivity);
        mBottomNav.setOnNavigationItemSelectedListener(this);
        mBottomNav.getMenu().getItem(1).setChecked(true);
    }

    private void checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        //Viene eseguito solo la prima volta, dopo viene eseguito solo l'else
        if(!isConnected && checkConnectionTimer== null) {
            mInfoSnackbarMainActivity.setVisibility(View.INVISIBLE);
            noConnectionSnackbar = Snackbar.make(
                    parentLayout,
                    "Connessione assente. Impossibile caricare i dati",
                    BaseTransientBottomBar.LENGTH_INDEFINITE);
            noConnectionSnackbar.setBackgroundTint(getResources().getColor(R.color.red));
            noConnectionSnackbar.setTextColor(getResources().getColor(R.color.white));
            noConnectionSnackbar.show();

            //Faccio partire un timer che ogni due secondi controlla che sia tornata la connessione
            checkConnectionTimer = new Timer();
            checkConnectionTimer.schedule(new TimerTask() {
                public void run() {
                    checkInternetConnection();

                }
            }, 2000, 1000);
        }
        //Se c'è la connessione ed è mai stato avviato il timer
        else if(isConnected && checkConnectionTimer != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mInfoSnackbarMainActivity.setVisibility(View.VISIBLE);
                }
            });
            noConnectionSnackbar.dismiss();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        Fragment selectedFragment = null;

        switch (id){
            case R.id.faq_item:
                selectedFragment = new FAQFragment();
                break;
            case R.id.info_item:
            case R.id.home_item:
                selectedFragment = new CountryMapFragment();
                break;
            default:
                return false;
        }
        getSupportFragmentManager().beginTransaction().
                replace(R.id.frameLayoutItineraryActivity, selectedFragment).commit();
        return true;
    }

    public void goToGovernmentWebsite(View view) {
        Intent viewIntent =
                new Intent("android.intent.action.VIEW",
                        Uri.parse(urlGoverno));
        startActivity(viewIntent);
    }
}