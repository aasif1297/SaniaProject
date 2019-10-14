package com.example.rubab.slider.models;

public class CartModel {
    private String p_id;
    private String c_id;
    private String product_name;
    private String product_price;
    private String qty;

    public CartModel( String p_id, String c_id, String product_name, String product_price, String qty) {
        this.p_id = p_id;
        this.c_id = c_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.qty = qty;
    }

    public CartModel() {
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }
}
