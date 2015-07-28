package njuse.via;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.io.File;

/**
 * 删除一个项目
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
     *
     * @param file 要删除的根目录
     */
    private void RecursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFile(f);
            }
            file.delete();
        }
    }

    /**
     * 删除监听
     *
     * @param view
     */
    public void deleteListener(View view) {
        dialog();
    }

    /**
     * 取消监听
     *
     * @param view
     */
    public void cancelListener(View view) {
        this.finish();
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirm_delete);
        builder.setTitle(R.string.prompt);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deleteAndRefresh();
            }

        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.create().show();
    }

    private void deleteAndRefresh() {
        File file = new File(deletePath);
        RecursionDeleteFile(file);
        // 广播通知
        Intent intent = new Intent();
        intent.setAction("action.refreshList");
        sendBroadcast(intent);
        this.finish();
    }
}
