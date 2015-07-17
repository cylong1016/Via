package njuse.via.bl;

import java.util.LinkedList;

import njuse.via.blservice.MakeBLService;
import njuse.via.po.Screen;
import njuse.via.po.ScreenSet;

/**
 * 制作时候的操作
 * Created by cylong on 2015-07-10 0010
 */
public class MakeBL implements MakeBLService {

    private ScreenSet screenList = new ScreenSet();


    @Override
    public void setTemplateID(int id) {
        screenList.setTemplateID(id);
    }



    @Override
    public boolean insert(Screen screen) {
        return screenList.getScreenList().add(screen);
    }

    @Override
    public void insert(int loc, Screen screen) {
        screenList.getScreenList().add(loc, screen);
    }

    @Override
    public Screen remove(int loc) {
        return screenList.getScreenList().remove(loc);
    }

    @Override
    public boolean remove(Screen screen) {
        return screenList.getScreenList().remove(screen);
    }

    @Override
    public Screen update(int loc, Screen screen) {
        return screenList.getScreenList().set(loc, screen);
    }

    @Override
    public ScreenSet readMakeRes(){
        return screenList;
    }

}
