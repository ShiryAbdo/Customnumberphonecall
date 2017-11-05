package custom.number.phone.call;

import android.app.Application;
import android.content.Context;

/**
 * Created by falcon on 05/11/2017.
 */

public class MyApplication  extends Application {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
