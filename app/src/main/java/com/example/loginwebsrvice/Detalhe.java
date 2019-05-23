package com.example.loginwebsrvice;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class Detalhe extends AppCompatActivity {
    TextView nome, email, telefone, curso, ra;
    Button btVoltar, btnUpdate;
    private ProgressDialog load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhe);

        // Configura a barra superior para apresentar um botão de voltar (<-)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Detalhes do Aluno");

        Intent it = getIntent();
        Bundle params = it.getExtras();
        final ArrayList<Aluno> alunos = (ArrayList<Aluno>) params.getSerializable("objContatos");

        ra = findViewById(R.id.media);
        nome = findViewById(R.id.status);
        email = findViewById(R.id.email);
        telefone = findViewById(R.id.telefone);
        curso = findViewById(R.id.curso);

        ra.setText(String.valueOf(alunos.get(0).getId()));
        nome.setText(alunos.get(0).getNome());
        email.setText(alunos.get(0).getEmail());
        telefone.setText(alunos.get(0).getTelefone());
        curso.setText(alunos.get(0).getCURSO());

        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alunos.get(0).setEmail(email.getText().toString());
                alunos.get(0).setTelefone(telefone.getText().toString());

                new DownloadDados().execute();
            }
        });
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


    class DownloadDados extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            load = ProgressDialog.show(Detalhe.this, "Aguarde", "ATUALIZANDO...");
        }

        @Override
        protected String doInBackground(Void... params) {
            Intent it = getIntent();
            Bundle param = it.getExtras();

            final ArrayList<Aluno> alunos = (ArrayList<Aluno>) param.getSerializable("objContatos");

            String ra = String.valueOf(alunos.get(0).getId());
            String emailValue = email.getText().toString();
            String telefoneValue = telefone.getText().toString();

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                String link = "https://aula1505.000webhostapp.com/query_update.php?ra=" + ra + "&email=" + emailValue + "&telefone=" + telefoneValue;
                System.out.println(link);
                System.out.println("_____________________________________________");
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

                if (jsonObject.get("success").equals("0")){
                    AlertDialog.Builder builder = new AlertDialog.
                            Builder(Detalhe.this);
                    builder.setTitle("AVISO");
                    builder.setMessage("Erro ao atualizar");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    builder.create().show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.
                            Builder(Detalhe.this);
                    builder.setTitle("AVISO");
                    builder.setMessage("Atualizado!!");
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                }
                            });
                    builder.create().show();
                }

                load.cancel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}