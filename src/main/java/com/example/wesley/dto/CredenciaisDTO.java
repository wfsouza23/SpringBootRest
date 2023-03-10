package com.example.wesley.dto;

public class CredenciaisDTO {
    private static final long serialVersionUID = 1l;

    private String email;
    private String senha;

    public CredenciaisDTO(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
