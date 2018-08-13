package br.com.victor.provapratica.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import br.com.victor.provapratica.R;
import br.com.victor.provapratica.servico.ServicoApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MainActivity", ServicoApp.getInstance().token);
    }
}
