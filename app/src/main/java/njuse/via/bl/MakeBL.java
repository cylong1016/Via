package njuse.via.bl;

import java.util.LinkedList;

import njuse.via.blservice.MakeBLService;
import njuse.via.po.Screen;

/**
 * 制作时候的操作
 * Created by cylong on 2015-07-10 0010
 */
public class MakeBL implements MakeBLService {

    private LinkedList<Screen> screenList = new LinkedList<>();

    @Override
    public boolean insert(Screen screen) {
        return screenList.add(screen);
    }

    @Override
    public void insert(int loc, Screen screen) {
        screenList.add(loc, screen);
    }

    @Override
    public Screen remove(int loc) {
        return screenList.remove(loc);
    }

    @Override
    public boolean remove(Screen screen) {
        return screenList.remove(screen);
    }

    @Override
    public Screen update(int loc, Screen screen) {
        return screenList.set(loc, screen);
    }

}
