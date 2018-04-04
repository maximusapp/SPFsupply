package com.example.maximus09.spfsupply;


import android.widget.Button;
import android.widget.CheckBox;

public class ItemSlider {

    private int imageView;
   private CheckBox checkBoxCustom;


    public ItemSlider(int imageView, CheckBox checkBoxCustom) {
        this.imageView = imageView;
        this.checkBoxCustom = checkBoxCustom;

    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public Button getCheckBoxCustom() {
        return checkBoxCustom;
    }

    public void setCheckBoxCustom(CheckBox checkBoxCustom) {
        this.checkBoxCustom = checkBoxCustom;
    }
}
