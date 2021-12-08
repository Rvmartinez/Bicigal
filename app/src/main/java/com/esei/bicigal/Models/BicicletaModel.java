package com.esei.bicigal.Models;

public class BicicletaModel {

    private String name;
    private String material;
    private String pulgadas;
    private String velocidades;
    private String color;
    private String tipoCuadro;
    private String modelo;
    private int imagePosition;


    public BicicletaModel(String name, String material, String pulgadas, String velocidades, String color, String tipoCuadro, String modelo,int imagePosition) {
        this.name = name;
        this.material = material;
        this.modelo = modelo;
        this.pulgadas = pulgadas;
        this.velocidades = velocidades;
        this.color = color;
        this.tipoCuadro = tipoCuadro;
        this.imagePosition = imagePosition;
    }

    public int getImagePosition() {
        return imagePosition;
    }

    public void setImagePosition(int imagePosition) {
        this.imagePosition = imagePosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPulgadas() {
        return pulgadas;
    }

    public void setPulgadas(String pulgadas) {
        this.pulgadas = pulgadas;
    }

    public String getVelocidades() {
        return velocidades;
    }

    public void setVelocidades(String velocidades) {
        this.velocidades = velocidades;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipoCuadro() {
        return tipoCuadro;
    }

    public void setTipoCuadro(String tipoCuadro) {
        this.tipoCuadro = tipoCuadro;
    }
}
