package com.kotbros.android_app;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.kotbros.core.ShakeListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigation;
    private BMIFragment bmiFragment;
    private DonationFragment donationFragment;

    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private ShakeListener shakeListener;
    private PlaybackService playbackService;
    private boolean bound = false;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlaybackService.LocalBinder binder = (PlaybackService.LocalBinder) service;
            playbackService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bmiFragment = BMIFragment.newInstance();
        donationFragment = DonationFragment.newInstance();
        donationFragment.setCallback(this::playCatSound);

        Intent intent = new Intent(this, PlaybackService.class);
        startService(intent);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        shakeListener = new ShakeListener(this::playSensorSound);

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnItemSelectedListener(this);
        bottomNavigation.setBackgroundColor(0);
        bottomNavigation.setSelectedItemId(R.id.bmi_calc_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity_container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        addBackStackChangeListener();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        } else if (id == R.id.action_about) {
            Intent i = new Intent(this, AboutActivity.class);
            startActivity(i);
            return true;
        } else if (id == R.id.action_privacy) {
            Intent i = new Intent(this, AboutActivity.class);
            i.putExtra(AboutActivity.EXTRA_PRIVACY_POLICY, true);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);
        final int id = item.getItemId();
        if (id == R.id.bmi_calc_menu) {
            showFragment(bmiFragment);
            return true;
        } else if (id == R.id.donation_menu) {
            showFragment(donationFragment);
            playDonationSound();
            return true;
        }

        return false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlaybackService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (bound) {
            unbindService(connection);
            bound = false;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(shakeListener, sensorAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(shakeListener);
    }

    private void addBackStackChangeListener() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.addOnBackStackChangedListener(() -> {
            if (getSupportActionBar() != null) {
                boolean showBackButton = fragmentManager.getBackStackEntryCount() > 0;
                getSupportActionBar().setDisplayHomeAsUpEnabled(showBackButton);
            }

            updateBottomNavigation(fragmentManager.findFragmentById(R.id.flFragment));
        });
    }

    private void updateBottomNavigation(Fragment fragment) {
        if (fragment instanceof BMIFragment) {
            bottomNavigation.setSelectedItemId(R.id.bmi_calc_menu);
        } else if (fragment instanceof DonationFragment) {
            bottomNavigation.setSelectedItemId(R.id.donation_menu);
        }
    }

    private void showFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragment, fragment);

        transaction.commit();
    }


    private void playDonationSound() {
        playSound(R.raw.donation_please);
    }
    private void playSensorSound() {
        playSound(R.raw.shake_sound);
    }
    private void playCatSound() {
        playSound(R.raw.cat_meow);
    }

    private void playSound(int resId) {
        if (bound && playbackService != null) {
            playbackService.playSound(this, resId);
        } else {
            Log.e("PlaybackService", "Service not yet bound!");
        }
    }



}