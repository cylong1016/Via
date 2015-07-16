package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 制作界面
 * Created by cylong on 2015-07-09
 */
public class MakeActivity extends Activity {

    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        getScreenInfo(); // 获得屏幕信息
        initComponent(); // 初始化部分组件的位置
        initListener(); // 初始化按钮的监听
    }

    public void initListener() {
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnTouchListener(touchListener);
        ImageView save = (ImageView) findViewById(R.id.save);
        save.setOnTouchListener(touchListener);
        ImageView selectPhoto = (ImageView) findViewById(R.id.selectPhoto);
        selectPhoto.setOnTouchListener(touchListener);
        ImageView crop = (ImageView) findViewById(R.id.crop);
        crop.setOnTouchListener(touchListener);
        ImageView filter = (ImageView) findViewById(R.id.filter);
        filter.setOnTouchListener(touchListener);
        ImageView paster = (ImageView) findViewById(R.id.paster);
        paster.setOnTouchListener(touchListener);
        ImageView select = (ImageView) findViewById(R.id.select);
        select.setOnTouchListener(touchListener);
        ImageView newScreen = (ImageView) findViewById(R.id.newScreen);
        newScreen.setOnTouchListener(touchListener);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(Color.BLACK);
            } else if(event.getAction() == MotionEvent.ACTION_UP){
                v.setBackgroundColor(getResources().getColor(R.color.tool_bar_background));
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getScreenInfo() {
        /* 获取屏幕宽高 */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    /**
     * 初始化部分组件的位置
     */
    private void initComponent() {

        int photoHeight = (int) (screenHeight * 17.0 / 23); // 装载图片组件的高

        EditText explainEdit = (EditText) findViewById(R.id.explain); // 获得输入文字的组件
        int imgH = 850;
        int imgW = 720;
        int explainX = (int) (screenWidth * (31.0 / imgW));
        int explainY = (int) (photoHeight * (609.0 / imgH));
        int explainW = (int) (screenWidth * (657.0 / imgW));
        int explainH = (int) (photoHeight * (146.0 / imgH));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(explainW, explainH);
        params.setMargins(explainX, explainY, 0, 0);
        explainEdit.setLayoutParams(params);
    }

    /**
     * 预览图是否打开
     */
    private boolean previewOn = false;

    public void previewListener(View view) {
        previewOn = !previewOn;
        TextView preview = (TextView) findViewById(R.id.preview); // 获取预览图组件
        ImageView expend = (ImageView) findViewById(R.id.expand); // 获取扩大按钮图片

        RelativeLayout.LayoutParams preParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 0);

        int expendH = expend.getLayoutParams().height;
        RelativeLayout.LayoutParams expParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, expendH);

        if (previewOn) {
            int previewMaxH = preview.getLayoutParams().height * 3;
            preParams.height = previewMaxH;
            preview.setLayoutParams(preParams);

            expParams.topMargin = previewMaxH;
            expend.setLayoutParams(expParams);
            expend.setImageResource(R.mipmap.icon_collapse);
        } else {
            int previewMinH = preview.getLayoutParams().height / 3;
            preParams.height = previewMinH;
            preview.setLayoutParams(preParams);

            expParams.topMargin = 0;
            expend.setLayoutParams(expParams);
            expend.setImageResource(R.mipmap.icon_expand);
        }
    }

    /**
     * 选择图片监听
     * @param view
     */
    public void selectPhotoListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, SelectPhotoActivity.class);
        this.startActivity(intent);
    }


    /**
     * 返回主菜单监听
     * @param view
     */
    public void backToMainListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        //设置切换动画，从左边进入，右边退出
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
    /**
     * 滤镜的按钮的监听
     * @param view
     */
    public void filterListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, FilterActivity.class);
        this.startActivity(intent);
    }

    /**
     * 保存按钮的监听
     * @param view
     */
    public void saveListener(View view) {

    }

    /**
     * 裁剪按钮的监听
     * @param view
     */
    public void cropListener(View view) {

    }

    /**
     * 贴图按钮监听
     * @param view
     */
    public void pasterListener(View view) {

    }

    /**
     * 添加选项按钮监听
     * @param view
     */
    public void selectListener(View view) {

    }

    /**
     * 新建按钮监听
     * @param view
     */
    public void newScreenListener(View view) {

    }
}
