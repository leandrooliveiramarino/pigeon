package pigeon.cursoandroid.com.pigeon.application;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by leandromarino on 15/05/16.
 */
public class CustomApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
