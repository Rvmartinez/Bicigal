package com.esei.bicigal.Models;

public class ViajeModel {
    private int viajeId;
    private String fecha;
    private String biciName;
    private int imagePosition;

    public ViajeModel(String fecha, String biciName, int imagePosition) {
        this.fecha = fecha;
        this.biciName = biciName;
        this.imagePosition = imagePosition;
    }

    public ViajeModel(int id, String fecha, String biciName, int imagePosition){
        this.viajeId = id;
        this.fecha = fecha;
        this.biciName = biciName;
        this.imagePosition = imagePosition;
    }

    public int getViajeId() {
        return viajeId;
    }

    public void setViajeId(int viajeId) {
        this.viajeId = viajeId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getBiciName() {
        return biciName;
    }

    public void setBiciName(String biciName) {
        this.biciName = biciName;
    }

    public int getImagePosition() {
        return imagePosition;
    }

    public void setImagePosition(int imagePosition) {
        this.imagePosition = imagePosition;
    }
}
