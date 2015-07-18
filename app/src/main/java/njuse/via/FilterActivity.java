package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import njuse.via.filter.ColorToneFilter;
import njuse.via.filter.ComicFilter;
import njuse.via.filter.FeatherFilter;
import njuse.via.filter.FilmFilter;
import njuse.via.filter.GlowingEdgeFilter;
import njuse.via.filter.IceFilter;
import njuse.via.filter.LomoFilter;
import njuse.via.filter.MoltenFilter;
import njuse.via.filter.SoftGlowFilter;
import njuse.via.filter.ZoomBlurFilter;

public class FilterActivity extends Activity {

    private String filename = "D://108.jpg";
    private ImageView myImageView;
    private File path;
    private Bitmap bmpDefaultPic = null,bitmap;
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        path = Environment.getExternalStorageDirectory() ; //获得SDCard目录
        iv = (ImageView)findViewById(R.id.photo);
        if(bmpDefaultPic==null) {
//            bmpDefaultPic = BitmapFactory.decodeFile(path + "/Pictures/108.jpg", null);
            bmpDefaultPic = BitmapFactory.decodeResource(getResources(), R.drawable.filter);
        }
        iv.setImageBitmap(bmpDefaultPic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filter, menu);
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

    /**
     * @param view
     * 无效果，返回原图
     */
    public void NoListener(View view) {
//        bmpDefaultPic = BitmapFactory.decodeFile(path + "/Pictures/108.g", null);
        bmpDefaultPic = BitmapFactory.decodeResource(getResources(), R.drawable.filter);
        iv.setImageBitmap(bmpDefaultPic);
    }

    public void IceListener(View view) {
        IceFilter filter = new IceFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void MoltenListener(View view) {
        MoltenFilter filter = new MoltenFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void ComicListener(View view) {
        ComicFilter filter = new ComicFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void SoftGlowListener(View view) {
        SoftGlowFilter filter = new SoftGlowFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void GlowingEdgeListener(View view) {
        GlowingEdgeFilter filter = new GlowingEdgeFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void FeatherListener(View view) {
        FeatherFilter filter= new FeatherFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void ZoomBlurListener(View view) {
        ZoomBlurFilter filter = new ZoomBlurFilter(bmpDefaultPic,20);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void LomoListener(View view) {
        LomoFilter filter = new LomoFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void FilmListener(View view) {
        FilmFilter filter = new FilmFilter(bmpDefaultPic,20);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void RedToneListener(View view) {
        ColorToneFilter filter = new ColorToneFilter(bmpDefaultPic,0xFF0000,192);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }
    public void BlueToneListener(View view) {
        ColorToneFilter filter = new ColorToneFilter(bmpDefaultPic,0x0000FF,192);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
    }

    /**
     * @param view
     * 确认滤镜
     */
    public void ensureFilter(View view) throws IOException{
//        //设置切换动画，从左边进入，右边退出
//        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        Intent intent = new Intent();
        intent.setClass(this, MakeActivity.class);
        this.startActivity(intent);
        new Thread() { // 防止切换闪屏
            public void run() {
                try {
                    saveMyBitmap("helloFilter");
                    //TODO 保存图片
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void saveMyBitmap(String bitName) throws IOException {
        File f = new File(path + "/Pictures/"+bitName+"108.png");
        f.createNewFile();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
