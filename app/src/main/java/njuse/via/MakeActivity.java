package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;

import njuse.via.bl.MakeBL;
import njuse.via.blservice.MakeBLService;
import njuse.via.po.Option;
import njuse.via.po.Screen;

/**
 * 制作界面
 * Created by cylong on 2015-07-09
 */
public class MakeActivity extends Activity {


    private int screenWidth;
    private int screenHeight;
    private int statusBarHeight;
    private MakeBLService makeBL = new MakeBL();

    public Screen screen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        getScreenInfo(); // 获得屏幕信息
        initPhotoViewLoc();
        screen=makeBL.getNewScreen();
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

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
    }

    /**
     * 初始化中间放图片和文字的的组件位置
     */
    public void initPhotoViewLoc() {
        ImageView photoView = (ImageView) findViewById(R.id.photoView); // 显示图片的view
        EditText explainEdit = (EditText) findViewById(R.id.explain); // 获得输入文字的组件
        double imgH = 850.0;
        double imgW = 720.0;
        int softHeight = screenHeight - statusBarHeight; // 去掉状态栏的高度
        int photoHeight = (int) (softHeight * 17.0 / 23); // 显示图片组件的高

        double magniscaleW = screenWidth / imgW; // 宽缩放的比例
        double magniscaleH = softHeight / imgH; // 高缩放比例
        int viewX;
        int viewY;
        int viewW;
        int viewH;

        int explainX;
        int explainY;
        int explainW;
        int explainH;

        if(magniscaleW < magniscaleH) { // 图片相对于显示图片的view较宽
            double temp = (photoHeight - imgH * magniscaleW) / 2;
            viewX = (int) (magniscaleW * 68.0);
            viewY = (int) (61.0 * magniscaleW + temp + dpTopx(28));
            viewW = (int) (magniscaleW * 583.0);
            viewH = viewW;

            explainX = (int) (magniscaleW * 68.0);
            explainY = (int) (672.0 * magniscaleW + temp + dpTopx(28));
            explainW = (int) (magniscaleW * 583.0);
            explainH = (int) (explainW * (122.0 / 583.0));
            Log.i("height", explainX + " " + explainY + " " + explainW + " " + explainH);
        } else {
            // TODO 横屏
            viewX = 0;
            viewY = 0;
            viewW = 0;
            viewH = 0;

            explainX = 0;
            explainY = 0;
            explainW = 0;
            explainH = 0;
        }
        // 设置放置图片view的位置
        RelativeLayout.LayoutParams paramsView = new RelativeLayout.LayoutParams(viewW, viewH);
        paramsView.setMargins(viewX, viewY, 0, 0);
        photoView.setLayoutParams(paramsView);
        // 设置文字输入框的位置
        RelativeLayout.LayoutParams paramsEdit = new RelativeLayout.LayoutParams(explainW, explainH);
        paramsEdit.setMargins(explainX, explainY, 0, 0);
        explainEdit.setLayoutParams(paramsEdit);
    }

    public void initScreen(){
        EditText edit=(EditText) findViewById(R.id.explain);
        String text = screen.getText();
        edit.setText(text);
        ImageView img=(ImageView) findViewById(R.id.photoView);
        if(screen.getBackGroundURL()!=null){
            img.setImageURI(Uri.parse(screen.getBackGroundURL()));
        }else{
            img.setImageResource(R.drawable.cat);
        }
    }

    /**
     * dp转化成px
     *
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
     *
     * @param view
     */
    public void selectPhotoListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, SelectPhotoActivity.class);
        intent.putExtra("type", "camera");
        this.startActivityForResult(intent, 1);
    }


    /**
     * 返回主菜单监听
     *
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
     * 滤镜按钮监听
     *
     * @param view
     */
    public void filterListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, FilterActivity.class);
        intent.putExtra("path",screen.getBackGroundURL());
        this.startActivity(intent);
    }

    /**
     * 保存按钮监听
     *
     * @param view
     */
    public void saveListener(View view) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String date = sDateFormat.format(new java.util.Date());
        String workName="via_"+date;
        makeBL.saveWork(workName);
        Toast.makeText(this,"保存文件成功！",Toast.LENGTH_SHORT).show();
    }

    /**
     * 裁剪按钮监听
     *
     * @param view
     */
    public void cropListener(View view) {
//        if(screen.getBackGroundURL()!=null) {
        String path = screen.getBackGroundURL().replace("crop","copy");
        if (path != null) {
            Intent intent = new Intent();
            intent.setClass(this, CropPicActivity.class);
            intent.putExtra("path", path);
            this.startActivityForResult(intent, 0);
        } else {
            Toast.makeText(this, "没有导入图片", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            ImageView mImageView = (ImageView) findViewById(R.id.photoView);
            String path = data.getStringExtra("bitmap");
            Bitmap bitmap = decodeUriAsBitmap(Uri.parse(path));
            if (bitmap != null) {

                mImageView.setImageBitmap(bitmap);
                System.gc();
            }

            screen.setBackGroundURL(path);
        }
        if (resultCode == 2) {
            //Log.e("back", "back to make");
//            screen.setBackGroundURL(data.getStringExtra("bitmap"));
            String path = data.getStringExtra("path");
            screen.setBackGroundURL(path);

           Uri uri = Uri.parse(path);
            Bitmap bitmap = decodeUriAsBitmap(uri);

            if (bitmap != null) {
                ImageView mImageView = (ImageView) findViewById(R.id.photoView);
                mImageView.setImageBitmap(bitmap);
                System.gc();
            }
        }
        if (resultCode == 98) {
            Bundle b = data.getExtras();
            screen.setOption((Option) b.get("roption"));
        }


    }

    /*
    滤镜结束之后调用这个方法
     */
    public void setImgAfterFilter(){
        Bitmap bitmap = decodeUriAsBitmap(Uri.parse(screen.getBackGroundURL()));
        ImageView mImageView = (ImageView) findViewById(R.id.photoView);
        if(bitmap!=null) {
            mImageView.setImageBitmap(bitmap);
        }
    }


    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 贴图按钮监听
     * @param view
     */
    public void pasterListener(View view) {

    }

    /**
     * 选项按钮监听
     * @param view
     */
    public void selectListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, OptionActivity.class);
        Bundle b=new Bundle();
        b.putSerializable("option",screen.getOption());
        intent.putExtras(b);
        this.startActivityForResult(intent, 98);
    }

    /**
     * 新增 一幕监听
     * @param view
     */
    public void newScreenListener(View view) {
        EditText edit = (EditText) findViewById(R.id.explain);
        String text = edit.getText().toString(); // 获得用户输入的文本
        screen.setText(text);
        // 新建一幕
        screen = makeBL.getNewScreen();
        initScreen();
    }

}

