package com.kotbros.android_app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
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

import java.util.Objects;

public class MainActivity extends AppCompatActivity
        implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigation;
    private BMIFragment bmiFragment;
    private DonationFragment donationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bmiFragment = BMIFragment.newInstance();
        donationFragment = DonationFragment.newInstance();

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
        MediaPlayer mp = MediaPlayer.create(this, R.raw.donation_please);
        mp.start();
    }
}