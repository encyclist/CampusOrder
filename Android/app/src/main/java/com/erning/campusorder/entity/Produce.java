package com.erning.campusorder.entity;

import com.erning.common.net.Network;

import java.io.Serializable;
import java.util.Objects;

public class Produce implements Serializable {
    private int id;
    private String img;
    private String name;
    private String description;
    private String price;

    private int count = 0;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return Network.ROOT+"IMG/"+img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name==null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description==null ? "" : description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        if (count <0 )
            this.count = 0;
        else
            this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produce produce = (Produce) o;
        return id == produce.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
