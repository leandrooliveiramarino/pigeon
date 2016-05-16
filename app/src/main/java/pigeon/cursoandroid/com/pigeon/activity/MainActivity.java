package pigeon.cursoandroid.com.pigeon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

import pigeon.cursoandroid.com.pigeon.R;

public class MainActivity extends AppCompatActivity {

    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://whatsappcourse.firebaseio.com/mensagens");
        firebase.setValue("Teste do firebase");
        */
    }
}
