package njuse.via.po;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by zucewei on 2015/7/16.
 */
public class ScreenSet implements Serializable {
    int num=0;
    private LinkedList<Screen> screenList = new LinkedList<>();	//作品的每一幕信息
    private int templateID;	//这一套作品使用的模板号
    private String workName;	//制作的作品名称


    public  ScreenSet(int templateID){
        this.templateID=templateID;
        this.workName="default_work";
    }
    public ScreenSet(){}

    public int getTemplateID(){
        return templateID;
    }

    public boolean insert(Screen screen) {
        return screenList.add(screen);
    }


    public void insert(int loc, Screen screen) {
        screenList.add(loc, screen);
    }


    public Screen remove(int loc) {
        return screenList.remove(loc);
    }

    public Screen getNewScreen(){

        Screen screen=new Screen(num);
        screenList.add(screen);
        num++;
        return screen;
    }

    public boolean remove(Screen screen) {
        return screenList.remove(screen);
    }


    public Screen update(int loc, Screen screen) {
        return screenList.set(loc, screen);
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

    public Screen getBeforeScreen(int id){
        for(int i=screenList.size();i>=0;i--){
            if(i!=0&&screenList.get(i).getID()==id){
                return screenList.get(i-1);
            }
        }
        return null;//当找不到的时候，直接返回null
    }

    public Screen getNextScreen(int id){
        int max=screenList.size();
        for(int i=0;i<max;i++){
            if(i!=(max-1)&&screenList.get(i).getID()==id){
                return screenList.get(i+1);
            }
        }
        return null;
    }

    public Screen getScrrenByID(int id){
        int max=screenList.size();
        for(int i=0;i<max;i++){
            if(screenList.get(i).getID()==id){
                return screenList.get(i);
            }
        }
        return null;

    }
}
