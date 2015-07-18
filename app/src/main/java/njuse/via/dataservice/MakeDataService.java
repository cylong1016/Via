package njuse.via.dataservice;

import java.util.ArrayList;
import java.util.LinkedList;

import njuse.via.po.Screen;
import njuse.via.po.ScreenSet;

/**
 * 用于保存和读取制作的结果
 * Created by cylong on 2015-07-10 0010
 */
public interface MakeDataService {



    /**
     * 保存制作的数据
     *
     * @param screenList 幕的列表
     */
    void saveMakeRes(ScreenSet screenList,String fileName);



    /**
     * 按作品名读取制作的数据
     *
     * @return ScreenSet
     */
    ScreenSet readMakeRes(String fileName);


    /**
     * 读取制作的所有数据
     *
     * @return ArrayList<ScreenSet>
     */
    ArrayList<ScreenSet> readAllMakeRes();
}
