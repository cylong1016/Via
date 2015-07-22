package njuse.via.po;

import android.os.Parcel;
import android.os.Parcelable;

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

    public OptionItem getOptionItem(int location){
        /*根据位置获取到对应的 选项*/
        return optionList.get(location);
    }

    public OptionItem getOptionItem(String text){
        /*根据文字的内容是否相等来获取 选项*/
        for(int i=0;i<optionList.size();i++){
            if(optionList.get(i).getText().equals(text)){
                return optionList.get(i);
            }
        }
        return null;
    }

    public  OptionItem update(int loc,OptionItem item){
        if(loc>optionList.size()){
            return null;
        }else {
            return optionList.set(loc,item);
        }
    }

    public void removeOptionItem(String text){
        /**
         * 按指定内容删除其中一个选项
         * */
        for(int i=0;i<optionList.size();i++){
            if(optionList.get(i).getText().equals(text)){
                optionList.remove(i);
                return;
            }
        }
    }

    public  int getItemLength(){
        return optionList.size();
    }

    public int getTrueLocation(){
        for(int i=0;i<optionList.size();i++) {
            if (optionList.get(i).getValue() == true) {
                return (1 + i);
            }
        }
        return 0;   ///当没有正确选项的情况下就返回 0
    }

    public LinkedList<OptionItem> getOptionList() {
        return optionList;
    }

    //    @Override
//    public int describeContents() {
//        return 0;
//    }
//    /************************************/
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//            dest.writeValue(optionList);
//    }
//
//    public static final Parcelable.Creator<Option> CREATOR = new Creator<Option>(){
//
//        public Option createFromParcel(Parcel source) {
//            // TODO Auto-generated method stub
//            Option option = new Option();
//            option.optionList=(LinkedList<OptionItem>) source.readValue(option.getClass().getClassLoader());
//            return option;
//        }
//
//        public Option[] newArray(int size) {
//            // TODO Auto-generated method stub
//            return new Option[size];
//        }
//    };


}
