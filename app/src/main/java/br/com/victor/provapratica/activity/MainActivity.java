package br.com.victor.provapratica.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import br.com.victor.provapratica.R;
import br.com.victor.provapratica.servico.ServicoApp;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddSiteActivity.class));
            }
        });

//        Log.d("MainActivity", ServicoApp.getInstance().token);
//        new LoginAsyncTask().execute();
    }

    private class LoginAsyncTask extends AsyncTask<String, Bitmap, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return ServicoApp.getInstance().logo("facebook.com");
            } catch (Exception e) {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                ImageView image = findViewById(R.id.image);
                image.setImageBitmap(bitmap);
            }
        }
    }
}
