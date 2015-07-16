package njuse.via.po;

import java.io.Serializable;

/**
 * Created by zucewei on 2015/7/16.
 *
 * ----每一条选项的数据结构-----
 */
public class OptionItem implements Serializable {
    String text;        //选项的内容

    boolean value;      //是否为要选中的目标

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
