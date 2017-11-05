package custom.number.phone.call;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Area extends AppCompatActivity {
    Button cancle ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area);
        cancle =(Button)findViewById(R.id.cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Area.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
