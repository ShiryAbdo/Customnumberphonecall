package custom.number.phone.call;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PayMent extends AppCompatActivity {
    Button Skip ,Apply ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_ment);
        Skip=(Button)findViewById(R.id.Skip);
        Apply=(Button)findViewById(R.id.Apply);
        Apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayMent.this,Area.class);
                startActivity(intent);
            }
        });
        Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayMent.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
