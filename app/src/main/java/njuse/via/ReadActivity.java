package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import njuse.via.config.CommonConfig;
import njuse.via.config.PathConfig;
import njuse.via.data.MakeData;

/**
 * 读取制作测作品
 * Created by cylong on 2015-07-09
 */
public class ReadActivity extends Activity {

    private LinearLayout layout;
    private String[] dirNames = readFile(PathConfig.WEB_PROJECT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        initProjectList(); // 初始化已经制作的项目列表
    }

    private void initProjectList() {
        layout = (LinearLayout) findViewById(R.id.read_layout);
        for (int i = 0; i < dirNames.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.abc);
            imageView.setId(i);
            imageView.setOnClickListener(clickListener);

            TextView textView = new TextView(this);
            String[] name = dirNames[i].split("_");
            Date date = null;
            try {
                date = CommonConfig.sDateFormat.parse(name[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            textView.setText(dateFormat.format(date) + " " + name[0]);
            textView.setBackgroundResource(R.color.tool_bar_background);
            textView.setTextColor(Color.WHITE);
            LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            textView.setLayoutParams(textParams);

            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.addView(imageView);
            relativeLayout.addView(textView);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int mergin = (int) getResources().getDimension(R.dimen.read_list_margin);
            params.setMargins(mergin, mergin, mergin, mergin);
             /* 获取屏幕宽高 */
            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            params.height = screenWidth / 2;
            layout.addView(relativeLayout, params);
        }
    }

    private ImageView.OnClickListener clickListener = new ImageView.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            String projectPath = PathConfig.WEB_PROJECT + "/" + dirNames[id];
            String htmlPath = projectPath + "/" + dirNames[id].split("_")[0] + ".html";
            Intent intent = new Intent();
            intent.setClass(ReadActivity.this, ShowActivity.class);
            intent.putExtra("url", htmlPath);
            ReadActivity.this.startActivity(intent);
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
     *
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
