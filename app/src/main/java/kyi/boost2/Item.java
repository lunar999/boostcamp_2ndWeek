package kyi.boost2;

/**
 * Created by Kyu on 2017-07-10.
 */

public class Item {
    private int image;
    private long abstime;
    private double popular, distance;
    private boolean check;
    private String name, content;

    public Item(int image, double distance, long abstime, double popular, boolean check, String name, String content) {
        this.image = image;
        this.distance = distance;
        this.abstime = abstime;
        this.popular = popular;
        this.check = check;
        this.name = name;
        this.content = content;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public long getAbstime() {
        return abstime;
    }

    public void setAbstime(long abstime) {
        this.abstime = abstime;
    }

    public double getPopular() {
        return popular;
    }

    public void setPopular(double popular) {
        this.popular = popular;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
