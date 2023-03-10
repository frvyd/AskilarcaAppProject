package com.example.oopproject;

import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Clothes implements Serializable {

    public final SimpleStringProperty type;
    public final SimpleStringProperty category;
    public final SimpleStringProperty color;
    public final SimpleStringProperty material;
    public final SimpleStringProperty size;

    public Clothes (String type,String category,String color,String material,String size){
        this.type = new SimpleStringProperty(type);
        this.category = new SimpleStringProperty(category);
        this.color = new SimpleStringProperty(color);
        this.material = new SimpleStringProperty(material);
        this.size = new SimpleStringProperty(size);
    }

    public String toString(){
        return type+","+category+","+color+","+material+","+size+"\n";
    }


    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }



    public String getCategory() {
        return category.get();
    }

    public SimpleStringProperty categoryProperty() {
        return category;
    }

    public void setCategory(String category) {
        this.category.set(category);
    }



    public String getColor() {
        return color.get();
    }

    public SimpleStringProperty colorProperty() {
        return color;
    }

    public void setColor(String color) {
        this.color.set(color);
    }



    public String getMaterial() {
        return material.get();
    }

    public SimpleStringProperty materialProperty() {
        return material;
    }

    public void setMaterial(String material) {
        this.material.set(material);
    }



    public String getSize() {
        return size.get();
    }

    public SimpleStringProperty sizeProperty() {
        return size;
    }

    public void setSize(String size) {
        this.size.set(size);
    }
}
