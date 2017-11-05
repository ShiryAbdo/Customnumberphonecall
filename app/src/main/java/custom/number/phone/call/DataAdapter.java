package custom.number.phone.call;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.reward.RewardedVideoAd;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by falcon on 04/11/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<PhoneData> androidList;
    private Context context;
    private int lastPosition=-1;
    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mmmVidio;

    public DataAdapter(ArrayList<PhoneData> android, Context c) {
        this.androidList = android;
        this.context=c;
    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout. contact_items_listview, viewGroup, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, final int i) {


        viewHolder.name.setText(androidList.get(i).getName());
        viewHolder.phone.setText(androidList.get(i).getPhone());
        viewHolder.makAcall.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View view) {
                mInterstitialAd = newInterstitialAd();
                loadInterstitial();

                    final Dialog okdialog = new Dialog(context, R.style.custom_dialog_theme);
                okdialog.setContentView(R.layout.dialog);
                String strin = Locale.getDefault().getDisplayLanguage();
                LocaleHelper.getLanguage(context);
                Resources res = context.getResources();
// Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.setLocale(new Locale(LocaleHelper.getLanguage(context))); // API 17+ only.
// Use conf.locale = new Locale(...) if targeting lower versions
                res.updateConfiguration(conf, dm);
                Button privateid = (Button)okdialog.findViewById(R.id.privateid);
                Button Custom = (Button)okdialog.findViewById(R.id.Custom);
                privateid.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showInterstitial();
                        Intent intent = new Intent(context,AskRegistration.class);
                        context.startActivity(intent);

                    }
                });
                Custom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context,customNumber.class);
                        context.startActivity(intent);
                    }
                });

                okdialog.show();
//

            }
        });


    }






    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount()
    {
        return androidList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name  ,phone;
 ImageView makAcall ;
         public ViewHolder(View view) {
            super(view);
            name=(TextView)view.findViewById(R.id.name);
             phone=(TextView)view.findViewById(R.id.phone);
             makAcall =(ImageView)view.findViewById(R.id.makAcall);




        }
    }



    private InterstitialAd newInterstitialAd() {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(context.getString(R.string.interstitial_ad_unit_id));
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
             }

            @Override
            public void onAdFailedToLoad(int errorCode) {
             }

            @Override
            public void onAdClosed() {
                Intent intent = new Intent(context,AskRegistration.class);
                context.startActivity(intent);
             }
        });
        return interstitialAd;
    }

    private void showInterstitial() {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(context, "Ad did not load", Toast.LENGTH_SHORT).show();
         }
    }

    private void loadInterstitial() {
        // Disable the next level button and load the ad.
         AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);
    }



}