package njuse.via.po;

import java.util.ArrayList;

/**
 * Created by zucewei on 2015/7/23.
 */
public class TreasureSet {
    int num=0;
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

    public Treasure getNewTreasure(){
        Treasure treasure=new Treasure(num);
        num++;
        return treasure;
    }

    public Treasure getTreasure(int id){
        for(int i=0;i<treasuresList.size();i++){
            if(treasuresList.get(i).getID()==id){
                return  treasuresList.get(i);
            }
        }
        return null;
    }
}
