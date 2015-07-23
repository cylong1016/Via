package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
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
 * Created by Administrator on 2015/7/19 0019
 */
public class PasterActivity extends Activity {
    ArrayList<SingleTouchView> pasters = new ArrayList<>();
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
        ImageView view = (ImageView)v;

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
        Bitmap src = bitmap;
        if( src == null ) {
            return null;
        }
        int actualWidth = src.getWidth();
        int actualHeight = src.getHeight();
        int ivWidth = iv.getWidth();
        int ivHeight = iv.getHeight();
        float scale = (float)actualWidth/ivWidth;
        System.out.println(scale);
        float margain = (ivHeight-actualHeight/scale)/2;
        System.out.println("margain = "+margain);
        ArrayList<Bitmap> maps = new ArrayList<>();
        ArrayList<PointF> points = new ArrayList<>();
        for(int i = 0 ;i<pasters.size();i++) {
            if(pasters.get(i).getVisibility()!=View.GONE) {
                maps.add(pasters.get(i).createNewPhoto());
                points.add(pasters.get(i).getLocation());
            }
        }
        for(int i = 0;i<points.size();i++){
            PointF point = new PointF((points.get(i).x)*scale,(points.get(i).y-margain)*scale);
            points.get(i).set(point);
        }


        //create the new blank bitmap
        Canvas cv = new Canvas( newb );
        //draw src into
        cv.drawBitmap( src, 0, 0, null );
        //draw pasters into
        for(int i = 0;i<maps.size();i++) {
            cv.drawBitmap(maps.get(i), points.get(i).x, points.get(i).y, null);
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

