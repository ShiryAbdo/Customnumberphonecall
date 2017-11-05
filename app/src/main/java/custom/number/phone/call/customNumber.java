package custom.number.phone.call;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class customNumber extends AppCompatActivity {
    ImageView askRegister ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_number);
        askRegister=(ImageView)findViewById(R.id.askRegister);
        askRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),PrivateNumber.class);
                startActivity(intent);
            }
        });
    }
}
