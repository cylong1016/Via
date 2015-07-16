package njuse.via.po;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by zucewei on 2015/7/16.
 */
public class ScreenSet implements Serializable {
    private LinkedList<Screen> screenList = new LinkedList<>();
    private int templateID;                    //ģ���id����ʾʹ��ѡ�е�ģ��

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
