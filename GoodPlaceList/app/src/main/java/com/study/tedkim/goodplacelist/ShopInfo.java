package com.study.tedkim.goodplacelist;

/**
 * Created by tedkim on 2017. 7. 11..
 */

public class ShopInfo {

    private String name, contents;
    private double dist, favor;
    private int recent;
    private int shopImage;
    private boolean checked;

    public ShopInfo(){

        checked = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    public double getFavor() {
        return favor;
    }

    public void setFavor(double favor) {
        this.favor = favor;
    }

    public int getRecent() {
        return recent;
    }

    public void setRecent(int recent) {
        this.recent = recent;
    }

    public int getShopImage() {
        return shopImage;
    }

    public void setShopImage(int shopImage) {
        this.shopImage = shopImage;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
