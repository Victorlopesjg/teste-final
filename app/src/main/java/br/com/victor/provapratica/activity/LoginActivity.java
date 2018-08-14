package br.com.victor.provapratica.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.subhrajyoti.passwordview.PasswordView;

import br.com.victor.provapratica.R;
import br.com.victor.provapratica.servico.ServicoApp;

public class LoginActivity extends AppCompatActivity {

    private EditText edEmail;
    private PasswordView edSenha;
    private TextView signUp;
    private Button btnLogin;

    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        signUp = findViewById(R.id.link_signup);
        btnLogin = findViewById(R.id.btnLogin);

        if (savedInstanceState != null) {
            email = savedInstanceState.getString(getString(R.string.key_save_email));
            senha = savedInstanceState.getString(getString(R.string.key_save_senha));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (email != null && !email.isEmpty())
            edEmail.setText(email);
        if (senha != null && !senha.isEmpty())
            edSenha.setText(senha);

        btnLogin.setOnClickListener(listenerLogin);
        signUp.setOnClickListener(listenerSignUp);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(getString(R.string.key_save_email), email);
        outState.putString(getString(R.string.key_save_senha), senha);
    }

    private boolean camposPreenchidos() {
        return (email != null && !email.isEmpty()) && (senha != null && !senha.isEmpty());
    }

    private View.OnClickListener listenerLogin = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            email = edEmail.getText().toString();
            senha = edSenha.getText().toString();

            if (camposPreenchidos()) {
                LoginAsyncTask asyncTask = new LoginAsyncTask();
                asyncTask.execute(email, senha);
            } else {
                Toast.makeText(LoginActivity.this, getString(R.string.erro_campos_nao_preenchidos), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener listenerSignUp = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    };

    private class LoginAsyncTask extends AsyncTask<String, Boolean, Boolean> {

        private ProgressDialog pDialog;
        private String erro;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Logando no app");
            pDialog.setCancelable(false);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return ServicoApp.getInstance().login(params[0], params[1]);
            } catch (Exception e) {
                erro = e.getMessage();
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            } else {
                if (erro != null) {
                    Toast.makeText(LoginActivity.this, erro, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}