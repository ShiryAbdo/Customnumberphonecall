package custom.number.phone.call;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Locale;

public class PayMent extends AppCompatActivity {
    Button Skip ,Apply ;

    private InterstitialAd mInterstitialAd;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
        String strin = Locale.getDefault().getDisplayLanguage();
        LocaleHelper.getLanguage(this);
        Resources res = this.getResources();
// Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(LocaleHelper.getLanguage(this))); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
        mInterstitialAd = newInterstitialAd();
        loadInterstitial();
        Skip=(Button)findViewById(R.id.Skip);
        Apply=(Button)findViewById(R.id.Apply);
        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progress_spinner = new ProgressDialog(PayMent.this);
                progress_spinner.setMessage("Loading...");
                progress_spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                progress_spinner.setProgress(50);
                progress_spinner.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progress_spinner.cancel();
                        Intent i = new Intent(PayMent.this,  Area.class);
                        startActivity(i);
                     }
                }, 1000);
            }
        });
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInterstitial();
                Intent intent = new Intent(PayMent.this,MainActivity.class);
                startActivity(intent);
            }
        });

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
        Intent setIntent = new Intent(PayMent.this,Registration.class);
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
