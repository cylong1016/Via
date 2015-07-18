package njuse.via.po;

import android.media.audiofx.BassBoost;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zucewei on 2015/7/16.
 *
 * ----每一个选项的数据结构----
 */
public class OptionItem implements Serializable {
    String text;        //每一个选项的内容
    boolean value;      //每一个选项的值，即是否为正确选项

    public OptionItem(String text,boolean value){
        this.text=text;
        this.value=value;
    }
    public OptionItem(){}

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
       /**
        * 更新选项内容
        * */
       this.text=text;
   }
    public void update(boolean value){
        /**
         * 更新选项的值
         */

        this.value=value;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        boolean v[]={value};
//        dest.writeString(text);
//        int x=0;
//        if (value==true){
//            x=1;
//        }
//        dest.writeInt(x);
//
//    }
//
//
//    public static final Parcelable.Creator<OptionItem> CREATOR = new Creator<OptionItem>(){
//
//        public OptionItem createFromParcel(Parcel source) {
//            // TODO Auto-generated method stub
//            OptionItem item = new OptionItem();
//            item.text = source.readString();
//           // item.value=1;
//            boolean x=false;
//            int value=source.readInt();
//            if(value==1){
//                x=true;
//            }
//            item.value=x;
//            return item;
//        }
//
//        public OptionItem[] newArray(int size) {
//            // TODO Auto-generated method stub
//            return new OptionItem[size];
//        }
//
//    };

}
