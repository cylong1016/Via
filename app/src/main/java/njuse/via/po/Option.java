package njuse.via.po;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by zucewei on 2015/7/16.
 *
 * ------  一组选项的数据结构  ------
 */

public class Option implements Serializable {
    private LinkedList<OptionItem> optionList = new LinkedList<>();

    public void insert(OptionItem item){
        optionList.add(item);
    }
    public void insert(int location,OptionItem item){
        optionList.add(location,item);
    }

    public void remove(int location){
        if(location>optionList.size()){
            return ;
        }
        optionList.remove(location);
    }

    public void remove(OptionItem item){
        optionList.remove(item);
    }

    public  OptionItem update(int loc,OptionItem item){
        if(loc>optionList.size()){
            return null;
        }else {
            return optionList.set(loc,item);
        }
    }

}
