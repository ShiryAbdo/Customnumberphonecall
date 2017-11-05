package custom.number.phone.call;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tour extends AppCompatActivity {
    Button takeTour ,regist ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
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
