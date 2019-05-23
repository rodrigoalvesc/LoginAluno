package com.example.loginwebsrvice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

public class LoginSucesso extends AppCompatActivity {
    TextView nome;
    Button btVoltar, btnMedia, btnDados;
    ListView listaContatos;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_sucesso);

        // Configura a barra superior para apresentar um botão de voltar (<-)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Lista de Contatos");

        Intent it = getIntent();
        Bundle params = it.getExtras();

        System.out.println(params);
        final ArrayList<Aluno> alunos = (ArrayList<Aluno>) params.getSerializable("objContatos");
        ArrayList<String> lstContatos = new ArrayList<>();

        for (Aluno aluno : alunos) {
            lstContatos.add("Nota 1: " + aluno.getNOTA1());
            lstContatos.add("Nota 2: " + aluno.getNOTA2());
            lstContatos.add("Nota 3: " + aluno.getNOTA3());
            lstContatos.add("Nota 4: " + aluno.getNOTA4());
        }
        Collections.sort(lstContatos);

        final ArrayAdapter<String> meuAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_expandable_list_item_1,
                        lstContatos
                );

        listaContatos = findViewById(R.id.listaContatos);
        listaContatos.setAdapter(meuAdapter);

        btnDados = findViewById(R.id.btnDados);
        btnDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(LoginSucesso.this, Detalhe.class);
                Bundle b = new Bundle();

                b.putSerializable("objContatos", alunos);

                it.putExtras(b);
                startActivity(it);
            }
        });

        btnMedia = findViewById(R.id.btnMedia);
        btnMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadDados().execute();
            }
        });
    }

    // Método para finalizar a aplicação
    @Override
    public void finish() {
        System.runFinalizersOnExit(true);
        super.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    // Botão na ActionBar (Barra Superior)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class DownloadDados extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            load = ProgressDialog.show(LoginSucesso.this, "Aguarde", "Calculando média...");
        }

        @Override
        protected String doInBackground(Void... params) {
            Intent it = getIntent();
            Bundle param = it.getExtras();

            final ArrayList<Aluno> alunos = (ArrayList<Aluno>) param.getSerializable("objContatos");

            String ra;

            ra = String.valueOf(alunos.get(0).getId());

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                String link = "https://aula1505.000webhostapp.com/query_media.php?ra=" + ra;

                URL url = new URL(link);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String linha;
                StringBuffer buffer = new StringBuffer();
                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                    buffer.append("\n");
                }

                return buffer.toString();

            } catch (Exception e) {
                e.printStackTrace();

                if (urlConnection != null) {
                    urlConnection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String dados) {
            super.onPostExecute(dados);

            try {
                JSONObject jsonObject = new JSONObject(dados);
                String media = jsonObject.getString("media");

                Intent it = new Intent(LoginSucesso.this, Media.class);
                Bundle b = new Bundle();
                b.putString("media", media);
                it.putExtras(b);
                startActivity(it);

                load.cancel();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}