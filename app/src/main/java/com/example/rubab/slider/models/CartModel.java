package com.example.rubab.slider.models;

public class CartModel {
    private String id;
    private String p_id;
    private String c_id;
    private String product_name;
    private String product_price;
    private String qty;
    private String product_image;
    private String product_detail;

    public CartModel(String id, String p_id, String c_id, String product_name, String product_price, String qty, String product_image, String product_detail) {
        this.id = id;
        this.p_id = p_id;
        this.c_id = c_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.qty = qty;
        this.product_image = product_image;
        this.product_detail = product_detail;
    }

    public CartModel(String id, String product_price, String qty) {
        this.id = id;
        this.product_price = product_price;
        this.qty = qty;
    }

    public CartModel() {
    }

    public CartModel(String p_id, String c_id, String product_name, String product_price, String qty, String product_image, String product_detail) {
        this.p_id = p_id;
        this.c_id = c_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.qty = qty;
        this.product_image = product_image;
        this.product_detail = product_detail;
    }

    public String getProduct_detail() {
        return product_detail;
    }

    public void setProduct_detail(String product_detail) {
        this.product_detail = product_detail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }
}
