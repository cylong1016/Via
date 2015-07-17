package njuse.via.blservice;

import njuse.via.po.Screen;
import njuse.via.po.ScreenSet;

/**
 * 制作时候的操作
 * Created by cylong on 2015-07-10 0010
 */
public interface MakeBLService {

    /**
     * 设定一套作品的使用模板
     */
    void setTemplateID(int id);
    /**
     * 在末尾添加一幕
     *
     * @param screen
     * @return 总是true
     */
    boolean insert(Screen screen);

    /**
     * 在指定位置添加一幕
     *
     * @param loc
     * @param screen
     */
    void insert(int loc, Screen screen);

    /**
     * 删除指定位置的幕
     *
     * @param loc
     * @return 删除的元素
     */
    Screen remove(int loc);

    /**
     * 删除传进来的指定的幕
     *
     * @param screen
     * @return
     */
    boolean remove(Screen screen);

    /**
     * 更新指定位置的幕
     *
     * @param loc
     * @param screen
     * @return 前一个元素的索引
     */
    Screen update(int loc, Screen screen);

    /**
     * @return 制作好的一个作品
     */
    ScreenSet readMakeRes();

}
