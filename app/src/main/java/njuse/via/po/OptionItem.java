package njuse.via.po;

import java.io.Serializable;

/**
 * Created by zucewei on 2015/7/16.
 *
 * ----ÿһ��ѡ������ݽṹ-----
 */
public class OptionItem implements Serializable {
    String text;        //ѡ�������

    boolean value;      //�Ƿ�ΪҪѡ�е�Ŀ��

    public OptionItem(String text,boolean value){
        this.text=text;
        this.value=value;
    }

    public void setText(String text){
        this.text=text;
    }

    public void setValue(){
        this.value=value;
    }

    public String getText(){
        return text;
    }

    public  boolean getValue(){
        return value;
    }

   public void update(String text){
       this.text=text;
   }
    public void update(boolean value){
        this.value=value;
    }
}
