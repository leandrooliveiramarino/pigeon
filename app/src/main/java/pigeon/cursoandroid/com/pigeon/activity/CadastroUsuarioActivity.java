package pigeon.cursoandroid.com.pigeon.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

import pigeon.cursoandroid.com.pigeon.R;
import pigeon.cursoandroid.com.pigeon.application.ConfiguracaoFirebase;
import pigeon.cursoandroid.com.pigeon.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome;
    private EditText email;
    private EditText senha;
    private Button botaoCadastrar;
    private Usuario usuario;

    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = (EditText) findViewById(R.id.edt_cadastro_nome);
        email = (EditText) findViewById(R.id.edt_cadastro_email);
        senha = (EditText) findViewById(R.id.edt_cadastro_senha);
        botaoCadastrar = (Button) findViewById(R.id.btn_cadastrar);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();

            }
        });
    }

    private void cadastrarUsuario(){
        firebase = ConfiguracaoFirebase.getFirebase();
        firebase.createUser(
                usuario.getEmail(),
                usuario.getSenha(),
                new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> stringObjectMap) {
                        usuario.setId(stringObjectMap.get("uid").toString());
                        usuario.salvar();
                        finish();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        Toast.makeText(CadastroUsuarioActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }
}
