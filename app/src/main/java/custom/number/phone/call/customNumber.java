package custom.number.phone.call;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Locale;

public class customNumber extends AppCompatActivity  implements RewardedVideoAdListener {
    ImageView askRegister ;
    private RewardedVideoAd mmmVidio;
    private InterstitialAd mInterstitialAd;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_number);
        String strin = Locale.getDefault().getDisplayLanguage();
        LocaleHelper.getLanguage(this);
        Resources res = this.getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(LocaleHelper.getLanguage(this))); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
        mmmVidio = MobileAds.getRewardedVideoAdInstance(this);
        mmmVidio.setRewardedVideoAdListener(this);
        loadRewardedVideoAd();

        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        askRegister=(ImageView)findViewById(R.id.askRegister);
        askRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
                Intent intent = new Intent(getApplicationContext(),AskRegistration.class);
                startActivity(intent);
            }
        });
    }


    private void loadRewardedVideoAd() {
        if (!mmmVidio.isLoaded()) {
             mmmVidio.loadAd("ca-app-pub-1858974607441283/4278555043", new AdRequest.Builder().build());
            mmmVidio.setImmersiveMode(false);
            if (!mmmVidio.isLoaded()) {
                mmmVidio.show();
            }
        }

    }

    @Override
    public void onRewardedVideoAdLoaded() {

        if (mmmVidio.isLoaded()) {
            mmmVidio.show();
        }

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        if (mmmVidio.isLoaded()) {
             mmmVidio.destroy(this);
        }

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        Toast.makeText(getApplicationContext(),"faild",Toast.LENGTH_LONG).show();

    }


    @Override
    protected void onResume() {
         mmmVidio.resume(this);
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onPause() {
        mmmVidio.pause(this);
         super.onPause();

    }

    @Override
    public void onDestroy() {
        mmmVidio.destroy(this);
        super.onDestroy();
    }

    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {

            }

            @Override
            public void onAdClosed() {
            }
        });
        return interstitialAd;
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(customNumber.this,MainActivity.class);
        startActivity(setIntent);
        finish();
    }
    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }
}
