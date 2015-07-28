package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

/**
 * Created by cylong on 2015-07-28 0028
 */
public class DeleteActivity extends Activity {

    String deletePath = null; // 需要删除的文件路径

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        Intent intent = getIntent();
        deletePath = intent.getStringExtra("path");
    }

    /**
     * 递归删除文件和文件夹
     * @param file  要删除的根目录
     */
    private void RecursionDeleteFile(File file){
        if(file.isFile()){
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 删除监听
     * @param view
     */
    public void deleteListener(View view) {
        File file = new File(deletePath);
        RecursionDeleteFile(file);
        this.finish();
    }

    /**
     * 取消监听
     * @param view
     */
    public void cancelListener(View view) {
        this.finish();
    }
}
