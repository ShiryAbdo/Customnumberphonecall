package custom.number.phone.call;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class Tour extends AppCompatActivity {
    Button takeTour ,regist ;



    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources res = this.getResources();
         DisplayMetrics dm = res.getDisplayMetrics();
        Configuration config = new Configuration();
        config.locale = new Locale(LocaleHelper.getLanguage(this));
//        android.content.res.Configuration conf = res.getConfiguration();
//        conf.setLocale(new Locale(LocaleHelper.getLanguage(this))); // API 17+ only.
         res.updateConfiguration(config, dm);
         this.setContentView(R.layout.activity_tour);




        takeTour=(Button)findViewById(R.id.takeTour);
        regist=(Button)findViewById(R.id.regist);
        takeTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tour.this,MainActivity.class);
                startActivity(intent);
            }
        });
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tour.this,Registration.class);
                startActivity(intent);
            }
        });

    }



}
