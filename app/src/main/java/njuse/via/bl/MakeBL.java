package njuse.via.bl;

import java.util.ArrayList;
import java.util.LinkedList;

import njuse.via.blservice.MakeBLService;
import njuse.via.data.MakeData;
import njuse.via.dataservice.MakeDataService;
import njuse.via.po.Screen;
import njuse.via.po.ScreenSet;
import njuse.via.po.TreasureSet;

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
    public ScreenSet getScreenSet(){
        return screenList;
    }

    @Override
    public void saveWork(String fileName) {
        MakeDataService mkd=new MakeData();
        mkd.saveMakeRes(screenList,fileName);
    }

    @Override
    public Screen getNextScreen(int screen_id) {
        return screenList.getNextScreen(screen_id);
    }

    @Override
    public Screen getPreviousScreen(int screen_id) {
        return screenList.getBeforeScreen(screen_id);
    }

    @Override
    public Screen getNewScreen() {
        return screenList.getNewScreen();
    }

    @Override
    public Screen getScreenByID(int screen_id) {
        return screenList.getScrrenByID(screen_id);
    }


}
