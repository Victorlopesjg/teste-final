package br.com.victor.provapratica.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.com.victor.provapratica.R;
import br.com.victor.provapratica.model.User;
import br.com.victor.provapratica.servico.ServicoApp;

public class SignUpActivity extends AppCompatActivity {

    private EditText edNome;
    private EditText edEmail;
    private EditText edSenha;
    private Button btnCadastrar;

    private String nome;
    private String email;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        edNome = findViewById(R.id.edNome);
        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        btnCadastrar = findViewById(R.id.btnCadastrar);

        if (savedInstanceState != null) {
            nome = savedInstanceState.getString(getString(R.string.key_save_nome));
            email = savedInstanceState.getString(getString(R.string.key_save_email));
            senha = savedInstanceState.getString(getString(R.string.key_save_senha));
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (nome != null && !nome.isEmpty())
            edNome.setText(nome);

        if (senha != null && !senha.isEmpty())
            edSenha.setText(senha);

        if (email != null && !email.isEmpty())
            edEmail.setText(email);

        btnCadastrar.setOnClickListener(listenerCadastrar);
    }

    private View.OnClickListener listenerCadastrar = new View.OnClickListener() {

        @Override
        public void onClick(final View v) {
            email = edEmail.getText().toString();
            senha = edSenha.getText().toString();
            nome = edNome.getText().toString();

            if (camposPreenchidos()) {

            } else {
                Toast.makeText(SignUpActivity.this, getString(R.string.erro_campos_nao_preenchidos), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean camposPreenchidos() {
        return (email != null && !email.isEmpty())
                && (senha != null && !senha.isEmpty())
                && (nome != null && !nome.isEmpty());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CadastrarAsyncTask extends AsyncTask<String, User, User> {

        private ProgressDialog pDialog;
        private String erro;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage("Cadastrando dados");
            pDialog.setCancelable(false);
        }

        @Override
        protected User doInBackground(String... params) {
            try {
                return ServicoApp.getInstance().register(params[0], params[1], params[2]);
            } catch (Exception e) {
                erro = e.getMessage();
            }

            return null;
        }

        @Override
        protected void onPostExecute(User result) {
            super.onPostExecute(result);
            if (result != null) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);

                finish();
            } else {
                if (erro != null) {
                    Toast.makeText(SignUpActivity.this, erro, Toast.LENGTH_SHORT).show();
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
