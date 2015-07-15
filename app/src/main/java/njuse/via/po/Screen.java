package njuse.via.po;

import java.io.Serializable;

/**
 * 每一幕的数据结构
 * Created by cylong on 2015-07-09 0009
 */
public class Screen implements Serializable {

    /**
     * 当前幕ID
     */
    protected int ID;
    /**
     * 幕的类型，详见ScreenEnum
     */
    protected ScreenEnum screenEnum;
    /**
     * 文字
     */
    protected String text;
    /**
     * 背景图片URL
     */
    protected String backGroundURL;

    public Screen(ScreenEnum screenEnum) {
        this.screenEnum = screenEnum;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setBackGroundURL(String backGroundURL) {
        this.backGroundURL = backGroundURL;
    }

    public String getBackGroundURL() {
        return backGroundURL;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public ScreenEnum getScreenEnum() {
        return screenEnum;
    }
}
