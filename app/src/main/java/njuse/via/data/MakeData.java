package njuse.via.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.LinkedList;

import njuse.via.config.PathConfig;
import njuse.via.dataservice.MakeDataService;
import njuse.via.po.Screen;
import njuse.via.po.ScreenSet;

/**
 * Created by wzce on 2015-07-15 0010
 * 文件对象序列化和反序列化
 */
public class MakeData implements MakeDataService {

    String dirpath = PathConfig.DATA_SER;                // 文件的存储路径
    @Override
    public void saveMakeRes(ScreenSet list,String fileName) {

        //当文件夹不存在时创建文件夹
        File file = new File(dirpath);
        if(!file.exists()){
            try {
                file.mkdirs();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        list.setWorkName(fileName);
        WebCreater.createHTML(list);            //同时生成html文件，保存在"/sdcard/Android/Via/web_product"路径下
        String path=dirpath+"/"+fileName+".out";
        serialize2SDcard(list,path);

    }

    @Override    //读取已经完成的作品
    public ScreenSet readMakeRes(String fileName) {
        String file=dirpath+"/"+fileName+".out";
        ScreenSet  set=(ScreenSet)getObjectFromSDcard(file);
        return set;
    }

    @Override
    public ArrayList<ScreenSet> readAllMakeRes() {

        File file=new File(dirpath);
        File[] files=file.listFiles();
        ArrayList<ScreenSet> list=new ArrayList<>();
        for(File f1:files){
            String fileName=dirpath+"/"+f1.getName();
            list.add((ScreenSet)getObjectFromSDcard(fileName));
        }
        return list;
    }


    private void serialize2SDcard(Object target,String file){
            /*
            *
            * 将对象序列化并保存
            * */
        FileOutputStream fos = null;
        ObjectOutputStream o = null;

        try {
            fos = new FileOutputStream(file);
            o = new ObjectOutputStream(fos);

            o.writeObject(target);

        } catch (FileNotFoundException e) {

//			  File path1 = new File(path);
            e.printStackTrace();

            System.out.println("序列化时并没有找到文件！！！！");
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                fos.close();
                o.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    private Object getObjectFromSDcard(String file){
        /*
        * 读取序列化对象
        * */

        if(! new File(file).exists()){
            System.out.println("序列化文件不存在！");
            return null;
        }

        FileInputStream fis = null;
        ObjectInputStream in = null;
        try {
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);

            return in.readObject();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("没有找到文件！");
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally{
            try {
                fis.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
