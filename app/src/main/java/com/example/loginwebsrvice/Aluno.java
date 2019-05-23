package com.example.loginwebsrvice;

import java.io.Serializable;

public class Aluno implements Serializable {

    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String CURSO;
    private String NOTA1;
    private String NOTA2;
    private String NOTA3;
    private String NOTA4;

    public Aluno() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCURSO() {
        return CURSO;
    }

    public void setCURSO(String CURSO) {
        this.CURSO = CURSO;
    }

    public String getNOTA1() {
        return NOTA1;
    }

    public void setNOTA1(String NOTA1) {
        this.NOTA1 = NOTA1;
    }

    public String getNOTA2() {
        return NOTA2;
    }

    public void setNOTA2(String NOTA2) {
        this.NOTA2 = NOTA2;
    }

    public String getNOTA3() {
        return NOTA3;
    }

    public void setNOTA3(String NOTA3) {
        this.NOTA3 = NOTA3;
    }

    public String getNOTA4() {
        return NOTA4;
    }

    public void setNOTA4(String NOTA4) {
        this.NOTA4 = NOTA4;
    }
}