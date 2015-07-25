package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
        layout = (LinearLayout) findViewById(R.id.read_layout); // 整体的线性布局
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        int mergin = (int) getResources().getDimension(R.dimen.read_list_margin);
        params.setMargins(mergin, mergin, 0, 0);
        /* 获取屏幕宽高 */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        params.height = (screenWidth - mergin) / 2 - mergin;

        for (int i = 0; i < dirNames.length; i += 2) {
            RelativeLayout relativeLayout_1 = createProjectView(i);
            LinearLayout subLinear = new LinearLayout(this); // 每个子线性布局中放置水平两个项目
            subLinear.addView(relativeLayout_1, 0);
            if(!(i == dirNames.length - 1)) { // 最后一个
                RelativeLayout relativeLayout_2 = createProjectView(i + 1);
                subLinear.setOrientation(LinearLayout.HORIZONTAL);
                subLinear.addView(relativeLayout_2, 1);
            }
            layout.addView(subLinear, params);
        }
    }

    /**
     * 显示每一个项目的view
     * @return ImageView 图片放置上去
     */
    private ImageView createImageView(int i) {
        ImageView imageView = new ImageView(this);
        File projectDir = new File(PathConfig.WEB_PROJECT + "/" + dirNames[i]);
        File[] files = projectDir.listFiles();
        boolean hasbgImg = false;
        for (int j = 0; j < files.length; j++) {
            if (files[j].getName().startsWith("picture_1")) { // 找到第一张图片显示
                Bitmap bm = BitmapFactory.decodeFile(files[j].getAbsolutePath());
                imageView.setImageBitmap(bm);
                hasbgImg = true;
                break;
            }
        }
        if (!hasbgImg) {
            imageView.setImageResource(R.drawable.cat); // 设置默认图片
        }
        imageView.setId(i);
        imageView.setOnClickListener(clickListener);
        LinearLayout.LayoutParams imgViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageView.setLayoutParams(imgViewParams);
        return imageView;
    }

    private TextView createViewTitle(int i) {
        TextView titleView = new TextView(this);
        String[] name = dirNames[i].split("_");
        Date date = null;
        try {
            date = CommonConfig.sDateFormat.parse(name[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        titleView.setText(name[1] + "\n" + dateFormat.format(date));
        titleView.setBackgroundResource(R.color.text_background);
        titleView.setTextColor(Color.WHITE);
        titleView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleView.setLayoutParams(textParams);
        return titleView;
    }

    /**
     * 每一个项目显示放置到一个RelativeLayout中
     * @return RelativeLayout
     */
    private RelativeLayout createProjectView(int i) {
        ImageView imageView = createImageView(i);
        TextView titleView = createViewTitle(i);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.addView(imageView);
        relativeLayout.addView(titleView);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.weight = 1;
        int margins = (int) getResources().getDimension(R.dimen.read_list_margin);
        params.setMargins(0, 0, margins, 0);
        relativeLayout.setLayoutParams(params);
        return relativeLayout;
    }

    private ImageView.OnClickListener clickListener = new ImageView.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            String projectPath = PathConfig.WEB_PROJECT + "/" + dirNames[id];
            String htmlPath = projectPath + "/" + dirNames[id].split("_")[1] + ".html";
            Intent intent = new Intent();
            intent.setClass(ReadActivity.this, ShowActivity.class);
            intent.putExtra("url", htmlPath);
            ReadActivity.this.startActivity(intent);
        }
    };

    private String[] readFile(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        String[] dirNames = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            dirNames[i] = files[i].getName();
        }
        Arrays.sort(dirNames);
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
