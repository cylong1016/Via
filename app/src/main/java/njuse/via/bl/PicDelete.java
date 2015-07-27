package njuse.via.bl;

import android.net.Uri;
import android.util.Log;

import java.io.File;

/**
 * Created by lenovo on 2015/7/27.
 */
public class PicDelete{
    public void delete(String uri){
        File file = new File(uri);
        if(file.exists()){
            file.delete();
        }
        else{
            Log.e("not found","file not found");
        }
    }
}
