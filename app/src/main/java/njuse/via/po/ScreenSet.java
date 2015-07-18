package njuse.via.po;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by zucewei on 2015/7/16.
 */
public class ScreenSet implements Serializable {
    private LinkedList<Screen> screenList = new LinkedList<>();
    private int templateID;                    //模板的id，表示使用选中的模板
    private String workName;                    //作品的名称，用于区分不同作品


    public  ScreenSet(int templateID){
        this.templateID=templateID;
        this.workName="default_work";
    }
    public ScreenSet(){}

    public int getTemplateID(){
        return templateID;
    }
    public void setWorkName(String name){
        this.workName=name;
    }
    public String getWorkName(){
        return workName;
    }
    public void setTemplateID(int id){
        this.templateID=id;
    }
    public LinkedList<Screen> getScreenList(){
        return screenList;
    }
}
