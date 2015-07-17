package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        initTextEditSize(); // 初始化输入框的位置和大小
        initPhotoViewSize(); // 初始化显示图片的view的位置和大小
    }

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
     * 初始化输入框的位置
     */
    private void initTextEditSize() {
        double imgH = 850.0;
        double imgW = 720.0;
        double magniscale = screenWidth / imgW; // 图片放大的比例
        int photoHeight = (int) (screenHeight * 17.0 / 23); // 装载图片组件的高

        EditText explainEdit = (EditText) findViewById(R.id.explain); // 获得输入文字的组件
        int explainX = (int) (magniscale * 68.0);
        int explainY = (int) (photoHeight * (672.0 / imgH));
        int explainW = (int) (magniscale * 583.0);
        int explainH = (int) (explainW * (122.0 / 583.0));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(explainW, explainH);
        params.setMargins(explainX, explainY, 0, 0);
        explainEdit.setLayoutParams(params);
    }

    /**
     * 初始化显示图片组件的位置
     */
    public void initPhotoViewSize() {
        ImageView photoView = (ImageView) findViewById(R.id.photoView); // 获得显示图片的view
        double imgH = 850.0;
        double imgW = 720.0;
        int photoHeight = (int) (screenHeight * 17.0 / 23); // 装载图片组件的高

        double magniscaleW = screenWidth / imgW; // 图片宽放大的比例
        double magniscaleH = photoHeight / imgH; // 图片高放大的比例
        int viewX;
        int viewY;
        int viewW;
        int viewH;

        if(magniscaleW < magniscaleH) { // 模板图片相对于显示图片的view较宽
            double temp = (photoHeight - imgH * magniscaleW) / 2;
            viewX = (int) (magniscaleW * 68.0);
            viewY = (int) (61.0 * magniscaleW + temp + dpTopx(25)); // TODO
            Log.i("height", temp + " " + screenHeight + " " + photoHeight + " " + magniscaleW);
            viewW = (int) (magniscaleW * 583.0);
            viewH = viewW;
        } else {
            viewX = 0;
            viewY = 0;
            viewW = 0;
            viewH = 0;
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(viewW, viewH);
        params.setMargins(viewX, viewY, 0, 0);
        photoView.setLayoutParams(params);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * @param dipValue
     * @return
     */
    public int dpTopx(float dipValue) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
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
