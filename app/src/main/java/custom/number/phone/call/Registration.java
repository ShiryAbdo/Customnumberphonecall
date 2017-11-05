package custom.number.phone.call;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Locale;

public class Registration extends AppCompatActivity  implements RewardedVideoAdListener {
    Button register ;
    private RewardedVideoAd mmmVidio;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String strin = Locale.getDefault().getDisplayLanguage();
        LocaleHelper.getLanguage(this);
        setContentView(R.layout.activity_registration);

        Resources res = this.getResources();
         DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(LocaleHelper.getLanguage(this))); // API 17+ only.
         res.updateConfiguration(conf, dm);


        register =(Button)findViewById(R.id.register);
        mmmVidio = MobileAds.getRewardedVideoAdInstance(this);
        mmmVidio.setRewardedVideoAdListener(this);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRewardedVideoAd();
                Intent intent = new Intent(Registration.this,PayMent.class);
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
}
