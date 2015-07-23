package njuse.via.po;

import java.util.ArrayList;

/**
 * Created by zucewei on 2015/7/23.
 */
public class TreasureSet {
    ArrayList<Treasure> treasuresList=new ArrayList<>();
    public void addTreasure(Treasure t){
        treasuresList.add(t);
    }
    public void deletTreasure(int id){
        for(int i=0;i<treasuresList.size();i++){
            if(treasuresList.get(i).getID()==id){
                treasuresList.remove(i);
                return;
            }
        }
    }
}
