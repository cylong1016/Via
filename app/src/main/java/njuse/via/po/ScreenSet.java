package njuse.via.po;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by zucewei on 2015/7/16.
 */
public class ScreenSet implements Serializable {
    private LinkedList<Screen> screenList = new LinkedList<>();
    private int templateID;                    //模板的id，表示使用选中的模板

    public  ScreenSet(int templateID){
        this.templateID=templateID;
    }

    public int getTemplateID(){
        return templateID;
    }

    public LinkedList<Screen> getScreenList(){
        return screenList;
    }
}
