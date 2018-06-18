package com.ml.gcastanon.desafio.Clases;

public class Producto {
    private String title;
    private String price;
    private boolean acceptMp;
    private String urlImag;
    private String idProducto;

    public Producto(String title, String price, boolean acceptMp, String urlImag,String idProducto) {
        this.title = title;
        this.price = price;
        this.acceptMp = acceptMp;
        this.urlImag = urlImag;
        this.idProducto = idProducto;
    }
    public Producto(){}

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getUrlImag() {
        return urlImag;
    }

    public void setUrlImag(String urlImag) {
        this.urlImag = urlImag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isAcceptMp() {
        return acceptMp;
    }

    public void setAcceptMp(boolean acceptMp) {
        this.acceptMp = acceptMp;
    }
}
