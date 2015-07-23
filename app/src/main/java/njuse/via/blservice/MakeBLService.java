package njuse.via.blservice;

import java.util.ArrayList;

import njuse.via.po.Screen;
import njuse.via.po.ScreenSet;
import njuse.via.po.TreasureSet;

/**
 * 制作时候的操作
 * Created by cylong on 2015-07-10 0010
 */
public interface MakeBLService{

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

    /**当前制作的一个作品
     * @return Screenset
     */
    public ScreenSet getScreenSet();

    /**
     *
     * 保存当前制作的序列化文件
     *需传入保存的文件名 fileName
     */
    public void  saveWork(String fileName);

    public  Screen getNextScreen(int screen_id);            //获取当前幕的下一幕

    public Screen getPreviousScreen(int screen_id);         //获取当前幕的下一幕

    public  Screen getNewScreen();                          //获取一个新的幕，并添加到当前的作品中

    public Screen getScreenByID(int screen_id);                          // 根据幕的id获取幕

//    public void setTreasure(TreasureSet set);
}
