package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import njuse.via.bl.MakeBL;
import njuse.via.blservice.MakeBLService;
import njuse.via.paster.SingleTouchView;
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


    ArrayList<SingleTouchView> pasters = new ArrayList<SingleTouchView>();

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
            viewX = (int) (magniscaleW * 66.0);
            viewY = (int) (61.0 * magniscaleW + temp + dpTopx(28));
            viewW = (int) (magniscaleW * 587.0);
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
            img.setImageResource(R.mipmap.make_background);
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
        if(screen.getBackGroundURL()!=null) {
            Intent intent = new Intent();
            intent.setClass(this, FilterActivity.class);
            intent.putExtra("path", screen.getBackGroundURL());
            this.startActivityForResult(intent, 16);
        }else{
            Toast.makeText(this,"没有导入图片",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 保存按钮监听
     *
     * @param view
     */
    public void saveListener(View view) {
        screen.setText(((EditText) findViewById(R.id.explain)).getText().toString());
        for(int i = 0;i<pasters.size();i++){
            pasters.get(i).setEditable(false);
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String date = sDateFormat.format(new java.util.Date());
        String workName="via_"+date;
        makeBL.saveWork(workName);
        Toast.makeText(this,"保存文件成功！",Toast.LENGTH_SHORT).show();
//        copyFile(R.raw.blur, "blur.js");
//        copyFile(R.raw.blur_css,"blur_css.css");
//        copyFile(R.raw.global,"global.css");
//        copyFile(R.raw.jquery_easing_1_3,"jquery.easing.1.3.js");
//        copyFile(R.raw.jquery_fullpage,"jquery.fullPage.css");
//        copyFile(R.raw.jquery_1_8_3_min,"jquery.1.8.3.min.js");
//        copyFile(R.raw.jquery_fullpage_min,"jquery.fullPage.min.js");
        Intent intent =new Intent();
        intent.setClass(this,ShowActivity.class);
        intent.putExtra("html",workName);
        startActivity(intent);
    }

    public void copyFile(int id,String name){
        int byteread = 0;
        byte[] buf = new byte[4096];
        FileInputStream inStream = null;
        FileOutputStream fs=null;

        AssetFileDescriptor fd = getResources().openRawResourceFd(R.raw.blur);

        try {
            fs = new FileOutputStream(MainActivity.root+"/web_product/"+name);
            inStream = fd.createInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while((byteread = inStream.read(buf)) != -1)
            {
                fs.write(buf, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 裁剪按钮监听
     *
     * @param view
     */
    public void cropListener(View view) {
        if(screen.getBackGroundURL()!=null) {
            String path = screen.getBackGroundURL().replace("crop","copy");
            if (path != null) {
                Intent intent = new Intent();
                intent.setClass(this, CropPicActivity.class);
                intent.putExtra("path", path);
                this.startActivityForResult(intent, 0);
            } else {
                Toast.makeText(this, "图片路径错误", Toast.LENGTH_SHORT).show();
            }
        }else {
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

        if (resultCode == 3) {
            ImageView mImageView = (ImageView) findViewById(R.id.photoView);
            RelativeLayout mLayout = (RelativeLayout) mImageView.getParent();
            final Drawable d = getResources().getDrawable(R.drawable.paster1);
            SingleTouchView singleTouchView = new SingleTouchView(MakeActivity.this);
            singleTouchView.setImageDrawable(d);
            mLayout.addView(singleTouchView);
            pasters.add(singleTouchView);
        }

        if (resultCode == 98) {
            Bundle b = data.getExtras();
            screen.setOption((Option) b.get("roption"));
        }
        if(resultCode == 16) {
            setImgAfterFilter();
        }

    }

    /*
    滤镜结束之后调用这个方法
     */
    public void setImgAfterFilter(){
        Bitmap bitmap = decodeUriAsBitmap(Uri.parse(screen.getBackGroundURL()));;
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
        Intent intent = new Intent();
        intent.setClass(this, PasterActivity.class);
        this.startActivityForResult(intent, 3);
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
        removeAllpasters();
        EditText edit = (EditText) findViewById(R.id.explain);
        String text = edit.getText().toString(); // 获得用户输入的文本
        screen.setText(text);
        // 新建一幕
        screen = makeBL.getNewScreen();
        initScreen();
    }

    public void removeAllpasters(){
        ImageView mImageView = (ImageView) findViewById(R.id.photoView);
        RelativeLayout mLayout = (RelativeLayout) mImageView.getParent();
        for(int i = 0;i<pasters.size();i++){
            mLayout.removeView(pasters.get(i));
        }
        pasters.clear();

    }

}

