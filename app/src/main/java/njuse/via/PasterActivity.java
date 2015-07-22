package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
 * Created by Administrator on 2015/7/19 0019.
 */
public class PasterActivity extends Activity {
    ArrayList<SingleTouchView> pasters = new ArrayList<SingleTouchView>();
    private ImageView iv;
    private Bitmap bmpDefaultPic = null,bitmap;
    private String url;//原图的图片，original
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_paster);
        iv = (ImageView)findViewById(R.id.PastePhoto);
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

    public void pasterListener(View v){
        ImageButton view = (ImageButton)v;

        ImageView mImageView = (ImageView) findViewById(R.id.PastePhoto);
        RelativeLayout mLayout = (RelativeLayout) mImageView.getParent();
        SingleTouchView singleTouchView = new SingleTouchView(PasterActivity.this);
        singleTouchView.setImageDrawable(view.getDrawable());
        mLayout.addView(singleTouchView);
        pasters.add(singleTouchView);
    }

    public void cancelPasterBtnListener(View view) {
        this.finish();
    }

    public void confirmPasterBtnListener(View view) {

        for(int i = 0;i<pasters.size();i++){
            pasters.get(i).setEditable(false);
        }

        Bitmap result = createNewPhoto();

        try {
            saveMyBitmap(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        setResult(3,intent);
        intent.setClass(this, MakeActivity.class);
        finish();

    }



    public Bitmap createNewPhoto(){
        int left = iv.getPaddingLeft();
        int top = iv.getPaddingTop();

        ArrayList<Bitmap> maps = new ArrayList<Bitmap>();
        ArrayList<PointF> points = new ArrayList<PointF>();
        for(int i = 0 ;i<pasters.size();i++) {
            if(pasters.get(i).getVisibility()!=View.GONE) {
                maps.add(pasters.get(i).createNewPhoto());
                points.add(pasters.get(i).getLocation());
            }
        }
        for(int i = 0;i<points.size();i++){
            PointF point = new PointF(points.get(i).x-left,points.get(i).y-top);
            points.get(i).set(point);
        }
        Bitmap src = bitmap;
        if( src == null ) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();

        //create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap( w, h, Bitmap.Config.ARGB_8888 );//创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas( newb );
        //draw src into
        cv.drawBitmap( src, 0, 0, null );//在 0，0坐标开始画入src
        //draw pasters into
        for(int i = 0;i<maps.size();i++) {
            cv.drawBitmap(maps.get(i), points.get(i).x, points.get(i).y, null);//在src上覆盖图片
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





}

