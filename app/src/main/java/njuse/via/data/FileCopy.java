package njuse.via.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by zucewei on 2015/7/22.
 *
 */
public class FileCopy {
    /**
     *
     * @param fromFile 被复制的文件
     * @param toFile 复制的目录文件
     * @param rewrite 是否重新创建文件
     *
     * <p>文件的复制操作方法,用于将处理好的图片复制到产物目录中
     */
    public static void copyfile(File fromFile, File toFile,Boolean rewrite ){

        if(!fromFile.exists()){

            System.out.println("要 复制 的图片不存在！");
            return;
        }

        if(!fromFile.isFile()){
            System.out.println("要复制的图片不是一个文件");
            return;
        }
        if(!fromFile.canRead()){
            return;
        }
        if(!toFile.getParentFile().exists()){
            toFile.getParentFile().mkdirs();
        }
        if(toFile.exists() && rewrite){
            toFile.delete();
        }


        try {
            FileInputStream fosfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);

            byte[] bt = new byte[1024];
            int c;
            while((c=fosfrom.read(bt)) > 0){
                fosto.write(bt,0,c);
            }
            //关闭输入、输出流
            fosfrom.close();
            fosto.close();


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
System.out.println("52498489051089409*289056151561561fw15wef1we5f1we4r");
    }

}
