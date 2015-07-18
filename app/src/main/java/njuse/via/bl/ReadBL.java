package njuse.via.bl;

import java.util.ArrayList;

import njuse.via.blservice.ReadBLService;
import njuse.via.data.MakeData;
import njuse.via.dataservice.MakeDataService;
import njuse.via.po.ScreenSet;

/**
 * Created by zucewei on 2015/7/18.
 */
class ReadBL implements ReadBLService{
    @Override
    public ScreenSet getMakeResource(String fileName){
        MakeDataService mkd=new MakeData();
        return mkd.readMakeRes(fileName);
    }

    @Override
    public ArrayList<ScreenSet> getAllMakeRes(){
        MakeDataService mkd=new MakeData();
        return mkd.readAllMakeRes();
    }
}
