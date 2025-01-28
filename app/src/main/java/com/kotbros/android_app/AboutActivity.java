package com.kotbros.android_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.danielstone.materialaboutlibrary.ConvenienceBuilder;
import com.danielstone.materialaboutlibrary.MaterialAboutFragment;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense;

public class AboutActivity extends AppCompatActivity {
    public final static String EXTRA_PRIVACY_POLICY = "privacy_policy";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        if (savedInstanceState == null) {
            Fragment f;
            if (getIntent().getBooleanExtra(EXTRA_PRIVACY_POLICY, false)) {
                f = new ShowPrivacyPolicyFragment();
            } else {
                f = new MyMaterialAboutFragment();
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.about_container, f)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class ShowPrivacyPolicyFragment extends Fragment {
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        }

        @Override
        public void onResume() {
            super.onResume();
            TextView privacyPolicy = requireView().findViewById(R.id.privacy_policy);
            privacyPolicy.setText(R.string.privacy_policy_content);
        }
    }

    public static class MyMaterialAboutFragment extends MaterialAboutFragment {
        private static String PLAY_STORE_LINK;


        @Override
        protected MaterialAboutList getMaterialAboutList(final Context activityContext) {
            PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=" + activityContext.getPackageName();
            MaterialAboutCard.Builder firstCard = new MaterialAboutCard.Builder();

            firstCard.addItem(new MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .icon(R.mipmap.ic_launcher)
                .build());

            firstCard.addItem(new MaterialAboutActionItem.Builder()
                .text(activityContext.getString(R.string.rate_in_play_store))
                .icon(R.drawable.ic_star_teal_24dp)
                .setOnClickAction(() -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_STORE_LINK));
                    startActivity(intent);
                })
                .build());

            firstCard.addItem(new MaterialAboutActionItem.Builder()
                .text(activityContext.getString(R.string.share_with_friends))
                .icon(R.drawable.ic_share_teal_24dp)
                .setOnClickAction(new MaterialAboutItemOnClickAction() {
                    @SuppressLint("StringFormatMatches")
                    @Override
                    public void onClick() {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT,
                            String.format(activityContext.getString(R.string.check_out),
                                R.string.app_name, PLAY_STORE_LINK));
                        sendIntent.setType("text/plain");
                        startActivity(sendIntent);
                    }
                })
                .build());

            MaterialAboutCard.Builder legalCard = new MaterialAboutCard.Builder();
            legalCard.title(activityContext.getString(R.string.legal));

            legalCard.addItem(new MaterialAboutActionItem.Builder()
                .text(activityContext.getString(R.string.privacy_policy))
                .icon(R.drawable.ic_security_teal_24dp)
                .setOnClickAction(() -> {
                    ShowPrivacyPolicyFragment f = new ShowPrivacyPolicyFragment();
                    requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.about_container, f)
                        .addToBackStack(null)
                        .commit();
                })
                .build());

            Drawable codeDrawable = activityContext.getResources().getDrawable(R.drawable.ic_code_teal_24dp, null);

            MaterialAboutCard materialAboutLibraryLicenseCard = ConvenienceBuilder
                .createLicenseCard(activityContext, codeDrawable,
                    "material-about-library", "2016", "Daniel Stone",
                    OpenSourceLicense.APACHE_2);

            MaterialAboutCard iconLicenseCard = ConvenienceBuilder
                .createLicenseCard(activityContext, codeDrawable,
                    "Material Design icons", "2018", "Google",
                    OpenSourceLicense.APACHE_2);

            return new MaterialAboutList.Builder()
                .addCard(firstCard.build())
                .addCard(legalCard.build())
                .addCard(materialAboutLibraryLicenseCard)
                .addCard(iconLicenseCard)
                .build();
        }
    }
}