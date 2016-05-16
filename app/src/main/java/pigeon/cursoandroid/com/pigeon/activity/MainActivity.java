package pigeon.cursoandroid.com.pigeon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;

import pigeon.cursoandroid.com.pigeon.R;
import pigeon.cursoandroid.com.pigeon.application.ConfiguracaoFirebase;

public class MainActivity extends AppCompatActivity {

    private Firebase firebase;
    private Button botaoSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebase = ConfiguracaoFirebase.getFirebase();

        botaoSair = (Button) findViewById(R.id.btn_sair);
        botaoSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebase.unauth();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
