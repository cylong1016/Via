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

import njuse.via.dataservice.MakeDataService;
import njuse.via.po.Screen;
import njuse.via.po.ScreenSet;

/**
 * Created by wzce on 2015-07-15 0010
 */
public class MakeData implements MakeDataService {
/**
 * �ļ��������л��ͷ����л�
 *
 * */

    String dirpath = "/sdcard/Android/Via/product";
    @Override
    public void saveMakeRes(ScreenSet list,String fileName) {

        //���ļ��в�����ʱ�����ļ���
        File file = new File(dirpath);
        if(!file.exists()){
            try {
                file.mkdirs();
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        String path=dirpath+"/"+fileName+".out";
        serialize2SDcard(list,path);

    }

    @Override    //��ȡ�Ѿ���ɵ���Ʒ
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

        FileOutputStream fos = null;
        ObjectOutputStream o = null;

        try {
            fos = new FileOutputStream(file);
            o = new ObjectOutputStream(fos);

            o.writeObject(target);

        } catch (FileNotFoundException e) {

//			  File path1 = new File(path);
            e.printStackTrace();

            System.out.println("���л�ʱ��û���ҵ��ļ���������");
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

        if(! new File(file).exists()){
            System.out.println("���л��ļ������ڣ�");
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
            System.out.println("û���ҵ��ļ���");
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
