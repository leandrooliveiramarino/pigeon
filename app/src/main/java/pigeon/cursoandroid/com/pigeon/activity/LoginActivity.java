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

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;
import java.util.Random;

import pigeon.cursoandroid.com.pigeon.R;
import pigeon.cursoandroid.com.pigeon.helper.Permissao;
import pigeon.cursoandroid.com.pigeon.helper.Preferencias;


public class LoginActivity extends AppCompatActivity {

    private EditText telefone;
    private EditText nome;
    private EditText codArea;
    private EditText codPais;
    private Button btnCadastrar;
    private String[] permissoesNecessarias = new String[] {
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Permissao.validaPermissoes(1, this, permissoesNecessarias);

        telefone        = (EditText) findViewById(R.id.edit_telefone);
        nome            = (EditText) findViewById(R.id.edit_nome);
        codArea         = (EditText) findViewById(R.id.edit_cod_area);
        codPais         = (EditText) findViewById(R.id.edit_cod_pais);
        btnCadastrar    = (Button) findViewById(R.id.btn_cadastrar);

        SimpleMaskFormatter simpleMaskCodPais = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskTelefone = new SimpleMaskFormatter("N.NNNN-NNNN");

        MaskTextWatcher maskTelefone = new MaskTextWatcher(telefone, simpleMaskTelefone);
        MaskTextWatcher maskCodArea = new MaskTextWatcher(codArea, simpleMaskCodArea);
        MaskTextWatcher maskCodPais = new MaskTextWatcher(codPais, simpleMaskCodPais);

        telefone.addTextChangedListener(maskTelefone);
        codArea.addTextChangedListener(maskCodArea);
        codPais.addTextChangedListener(maskCodPais);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeUsuario = nome.getText().toString();
                String telefoneCompleto =
                                codPais.getText().toString() +
                                codArea.getText().toString() +
                                telefone.getText().toString();

                String telefoneSemFormatacao = telefoneCompleto
                        .replace("+", "")
                        .replace("-","")
                        .replace(".","");


                //Gerar token
                Random randomico = new Random();
                int numeroRandomico = randomico.nextInt(9999 - 1000) + 1000;
                String token = String.valueOf(numeroRandomico);
                String mensagemEnvio = "Pigeon código de confirmação: " + token;

                //Salvar os dados para validação
                Preferencias preferencias = new Preferencias(LoginActivity.this);
                preferencias.salvarUsuarioPreferencias(nomeUsuario, telefoneSemFormatacao, token);
                HashMap<String, String> usuario = preferencias.getDadosUsuario();

                //envio do SMS
                boolean enviadoSMS = enviaSMS("+"+telefoneSemFormatacao, mensagemEnvio);

                if(enviadoSMS) {
                    Intent intent = new Intent(LoginActivity.this, ValidadorActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Problema ao enviar o SMS, tente novamente!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    /*Envio de SMS*/
    private boolean enviaSMS(String telefone, String mensagem) {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(telefone,null, mensagem, null, null);

                return true;
            }catch (Exception e) {
                e.printStackTrace();

                return false;
            }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int resultado : grantResults) {
            if(resultado == PackageManager.PERMISSION_DENIED) {
                alertaValidacaoPermissao();
            }
        }
    }

    private void alertaValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões negadas.");
        builder.setMessage("Para utilizar esse app, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
