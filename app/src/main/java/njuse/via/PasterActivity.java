package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import njuse.via.paster.SingleTouchView;

/**
 * Created by Administrator on 2015/7/19 0019
 */
public class PasterActivity extends Activity {
    ArrayList<SingleTouchView> pasters = new ArrayList<>();
    private ImageView iv;
    private Bitmap bmpDefaultPic = null, bitmap;
    private String url;//原图的图片，original
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_paster);
        iv = (ImageView) findViewById(R.id.PastePhoto);
        url = getIntent().getStringExtra("path").substring(7);
        bmpDefaultPic = BitmapFactory.decodeFile(url);
        bitmap = bmpDefaultPic;
        iv.setImageBitmap(bmpDefaultPic);
        iv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                for (int i = 0; i < pasters.size(); i++) {
                    pasters.get(i).setEditable(false);
                }
            }
        });

    }

    public void pasterListener1(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster1);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
        }

    public void pasterListener2(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster2);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener3(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster3);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener4(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster4);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener5(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster5);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener6(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster6);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener7(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster7);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener8(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster8);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener9(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster9);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener10(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster10);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener11(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster11);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener12(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster12);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener13(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster13);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener14(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster14);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener15(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster15);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener16(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster16);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener17(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster17);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener18(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster18);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener19(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster19);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener20(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster20);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener21(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster21);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener22(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster22);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener23(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster23);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener24(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster24);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void pasterListener25(View v){
        RelativeLayout mLayout = (RelativeLayout) iv.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this,new PointF(360,360),0);
        singleTouchView.setImageResource(R.mipmap.paster25);
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void cancelPasterBtnListener(View view) {
        this.finish();
    }

    public void confirmPasterBtnListener(View view) {

        for (int i = 0; i < pasters.size(); i++) {
            pasters.get(i).setEditable(false);
        }

        Bitmap result = createNewPhoto();

        try {
            saveMyBitmap(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        setResult(3, intent);
        intent.setClass(this, MakeActivity.class);
        finish();

    }


    public Bitmap createNewPhoto() {
        Bitmap src = bitmap;
        if (src == null) {
            return null;
        }
        int actualWidth = src.getWidth();
        int actualHeight = src.getHeight();
        int ivWidth = iv.getWidth();
        float scale = (float)actualWidth/ivWidth;
        ArrayList<Bitmap> maps = new ArrayList<>();
        ArrayList<PointF> points = new ArrayList<>();
        for (int i = 0; i < pasters.size(); i++) {
            if (pasters.get(i).getVisibility() != View.GONE) {
                maps.add(pasters.get(i).createNewPhoto());
                points.add(pasters.get(i).getLocation());
            }
        }

        for(int i = 0;i<points.size();i++){
            PointF point = new PointF((points.get(i).x)*scale,(points.get(i).y)*scale);
                points.get(i).set(point);
            }


            //create the new blank bitmap
            Bitmap newb = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
            Canvas cv = new Canvas(newb);
            //draw src into
            cv.drawBitmap(src, 0, 0, null);
            //draw pasters into
            for(int i = 0;i<maps.size();i++) {
                cv.drawBitmap(resizeBmp(maps.get(i), scale), points.get(i).x, points.get(i).y, null);
                }
                //save all clip
                cv.save(Canvas.ALL_SAVE_FLAG);//保存
                //store
                cv.restore();//存储
                return newb;
            }

        public void saveMyBitmap(Bitmap bmpTemp) throws IOException {
            String copyURL = url;
            File f = new File(copyURL);
            f.createNewFile();
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            bmpTemp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
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

        private static Bitmap resizeBmp(Bitmap bitmap,float scale) {
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale); //长和宽放大缩小的比例
            Bitmap resizeBmp = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
            return resizeBmp;
        }
    }

