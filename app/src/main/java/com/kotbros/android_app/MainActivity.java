package com.kotbros.android_app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    BottomNavigationView bottomNavigation;
    BMIFragment bmiFragment;
    DonationFragment donationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bmiFragment = BMIFragment.newInstance();
        donationFragment = DonationFragment.newInstance();

        bottomNavigation = findViewById(R.id.bottomNavigationView);
        bottomNavigation.setOnItemSelectedListener(this);
        bottomNavigation.setSelectedItemId(R.id.bmi_calc_menu);
        bottomNavigation.setBackgroundColor(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
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

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.bmi_calc_menu) {
            setFrame(bmiFragment, R.string.bmi_calc_menu);
            return true;
        } else if (id == R.id.donation_menu) {
            setFrame(donationFragment, R.string.donation_menu);
            playDonationSound();
            return true;
        }

        return false;
    }

    private void playDonationSound() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.donation_please);
        mp.start();
    }

    private void setFrame(Fragment fragment, int resId) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.flFragment, fragment)
            .commit();
        TextView header = (TextView)this.findViewById(R.id.fragment_header);
        header.setText(getString(resId));
    }
}