package com.esei.bicigal.Models;

public class UsuarioModel {
    private String nombre;
    private String email;
    private String login;
    private String password;
    private int esAdmin;

    public UsuarioModel(String nombre, String email, String login, String password) {
        this.nombre = nombre;
        this.email = email;
        this.login = login;
        this.password = password;
        this.esAdmin =esAdmin;
    }




    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password){this.password=password;}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                '}';
    }


}
