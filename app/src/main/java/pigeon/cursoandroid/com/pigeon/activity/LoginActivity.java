package pigeon.cursoandroid.com.pigeon.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import pigeon.cursoandroid.com.pigeon.R;
import pigeon.cursoandroid.com.pigeon.application.ConfiguracaoFirebase;
import pigeon.cursoandroid.com.pigeon.helper.Permissao;
import pigeon.cursoandroid.com.pigeon.helper.Preferencias;
import pigeon.cursoandroid.com.pigeon.model.Usuario;


public class LoginActivity extends AppCompatActivity {

    private Firebase firebase;
    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebase = ConfiguracaoFirebase.getFirebase();

        verificarUsuarioLogado();

        email = (EditText) findViewById(R.id.edt_email);
        senha = (EditText) findViewById(R.id.edt_senha);
        botaoLogar = (Button) findViewById(R.id.btn_logar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });

    }

    public void abrirCadastroUsuario(View view) {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void validarLogin(){
        firebase.authWithPassword(
                usuario.getEmail(),
                usuario.getSenha(),
                new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        abrirTelaPrincipal();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(LoginActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void verificarUsuarioLogado(){
        if(firebase.getAuth() != null) {
            abrirTelaPrincipal();
        }
    }
}
