package com.erning.common.net.bean;

import java.util.List;

public class Order {
    private int id;
    private int student_id;
    private String table;
    private float price;
    private String state;
    private String create_time;
    private List<OrderProduct> oplist;

    public Order() {
    }

    public Order(int student_id, String table, float price, List<OrderProduct> oplist) {
        this.student_id = student_id;
        this.table = table;
        this.price = price;
        this.oplist = oplist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public List<OrderProduct> getOplist() {
        return oplist;
    }

    public void setOplist(List<OrderProduct> oplist) {
        this.oplist = oplist;
    }
}
