package njuse.via.data;

import android.content.res.AssetFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Objects;

import njuse.via.MainActivity;
import njuse.via.R;

/**
 * Created by zucewei on 2015/7/19.
 */
public class Web_IO {
    String path= PathConfig.WEB;

    public Web_IO(){
        File file = new File(path);
        if(!file.exists()){
            try {
                file.mkdirs();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void write_file(String name,String text){
//        String filePath = "/sdcard/foo2.txt";
        String fileName=path+"/"+name;

        // File file = new File(filePath);
        try{

            FileOutputStream fout = new FileOutputStream(fileName);
            byte [] bytes = text.getBytes();
            fout.write(bytes);
            fout.close();
        }

        catch(Exception e){
            e.printStackTrace();
        }
    }
}


