package com.kotbros.android_app;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import pl.droidsonroids.gif.GifImageView;


public class DonationFragment extends Fragment {

    public DonationFragment() {
        // Required empty public constructor
    }

    public static DonationFragment newInstance() {
        return new DonationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_donation, container, false);

        Button btn_donation = view.findViewById(R.id.btn_donation);

        btn_donation.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/paypalme/ovkroog"));
            startActivity(browserIntent);
        });

        GifImageView begging_cat_gif = view.findViewById(R.id.begging_cat_gif);
        begging_cat_gif.setOnClickListener(v -> {
            playCatSound(view);
        });

        return view;
    }



    private void playCatSound(View view) {
        MediaPlayer mp = MediaPlayer.create(view.getContext(), R.raw.cat_meow);
        mp.start();
    }

}