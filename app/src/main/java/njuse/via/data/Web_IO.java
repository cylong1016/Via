package njuse.via.data;

import java.io.File;
import java.io.FileOutputStream;



import njuse.via.config.PathConfig;

/**
 * Created by zucewei on 2015/7/19
 */
public class Web_IO {

    public Web_IO(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void write_file(String fpath, String name, String text) {
        String fileName = fpath + "/" + name;

        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            byte[] bytes = text.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


