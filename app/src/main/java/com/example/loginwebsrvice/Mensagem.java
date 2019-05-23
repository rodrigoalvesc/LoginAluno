package com.example.loginwebsrvice;

import android.app.AlertDialog;
import android.content.Context;

public class Mensagem {
    private Context _context;

    public Mensagem(Context context) {
        this._context = context;
    }

    // construtor com icone
    public void show(String titulo, String texto, String icone) {
        int icon = 0;

        AlertDialog.Builder msg = new AlertDialog.Builder(_context);
/*
        if (icone.equals("aviso")) {
            icon = R.drawable.ic_launcher;
        } else if (icone.equals("erro")) {
            icon = R.drawable.ic_launcher;
        } else if (icone.equals("atencao")) {
            icon = R.drawable.ic_launcher;
        } else if (icone.equals("ic_uninove")) {
            icon = R.drawable.ic_uninove;
        } else {
            icon = R.drawable.ic_launcher;
        }
*/
        //msg.setIcon(icon);
        msg.setTitle(titulo);
        msg.setMessage(texto);
        msg.setNeutralButton("OK", null);
        msg.show();
    }

    // construtor sem icone
    public void show(String titulo, String texto) {
        AlertDialog.Builder msg = new AlertDialog.Builder(_context);
        //msg.setIcon(R.drawable.ic_launcher);
        msg.setTitle(titulo);
        msg.setMessage(texto);
        msg.setNeutralButton("OK", null);
        msg.show();
    }
}