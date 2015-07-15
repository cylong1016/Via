package njuse.via.dataservice;

import java.util.LinkedList;

import njuse.via.po.Screen;

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
    void saveMakeRes(LinkedList<Screen> screenList);

    /**
     * 读取制作的数据
     *
     * @return LinkedList<Screen>
     */
    LinkedList<Screen> readMakeRes();
}
