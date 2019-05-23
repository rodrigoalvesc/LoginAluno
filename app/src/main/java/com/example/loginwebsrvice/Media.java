package com.example.loginwebsrvice;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class Media extends AppCompatActivity {
    TextView media, status;
    Button btVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media);

        // Configura a barra superior para apresentar um botão de voltar (<-)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Média do Aluno");

        Intent it = getIntent();
        Bundle params = it.getExtras();
        String mediaValue= params.getString("media");

        String statusValues;

        if (Double.parseDouble(mediaValue) > 6) {
            statusValues = "APROVADO";
        } else {
            statusValues = "REPROVADO";
        }

        media = findViewById(R.id.media);
        status = findViewById(R.id.status);

        media.setText(mediaValue);
        status.setText(statusValues);
    }

    // Botão na ActionBar (Barra Superior)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}