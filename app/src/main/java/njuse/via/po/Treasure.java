package njuse.via.po;

/**
 * Created by zucewei on 2015/7/23.
 * 每一个宝藏的数据结构
 */
public class Treasure {
    private double x;
    private double y;
    private int ID;
    public Treasure(int id){
        this.ID=id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
