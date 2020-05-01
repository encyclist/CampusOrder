package com.erning.common.net.bean;

public class OrderProduct {
    private Produce product;
    private int num;

    public OrderProduct() {
    }

    public OrderProduct(Produce product, int num) {
        this.product = product;
        this.num = num;
    }

    public Produce getProduct() {
        return product;
    }

    public void setProduct(Produce product) {
        this.product = product;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
