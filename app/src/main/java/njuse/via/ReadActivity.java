package njuse.via;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;

/**
 * 读取制作测作品
 * Created by cylong on 2015-07-09
 */
public class ReadActivity extends Activity {

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initProjectList(); // 初始化已经制作的项目列表
    }

    private void initProjectList() {
        layout = (LinearLayout) findViewById(R.id.read_layout);
        for (int i = 0; i < 5; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int mergin = (int)getResources().getDimension(R.dimen.read_list_margin);
            params.setMargins(mergin, mergin, mergin, mergin);
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.abc);
            imageView.setId(i);
            layout.addView(imageView, params);
        }
    }

    private ImageView.OnClickListener clickListener = new ImageView.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
        }
    };

    private String[] readFile(String path) {
        File dir = new File(path);
        File[] dirList = dir.listFiles();
        String[] dirNames = new String[dirList.length];
        for (int i = 0; i < dirList.length; i++) {
            dirNames[i] = dirList[i].getName();
        }
        return dirNames;
    }

    /**
     * 返回主菜单监听
     * @param view
     */
    public void backListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        //设置切换动画，从左边进入，右边退出
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        this.finish();
    }

}
