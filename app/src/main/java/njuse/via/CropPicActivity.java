package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import njuse.via.bl.PicCompress;
import njuse.via.config.PathConfig;
import njuse.via.crop.ClipImageLayout;
import njuse.via.crop.ShowImageActivity;

/**
 * Created by zr on 2015/7/17
 */
public class CropPicActivity extends Activity {
    public static String picPath = null;
    private ClipImageLayout mClipImageLayout;
    private String uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picPath = getIntent().getStringExtra("path").replace("crop", "copy");
        setContentView(R.layout.activity_crop);
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cropPic:
                Bitmap bitmap = mClipImageLayout.clip();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datas = baos.toByteArray();

                Intent intent = new Intent(this, ShowImageActivity.class);
                intent.putExtra("bitmap", datas);
                startActivity(intent);

                break;
        }
        return super.onOptionsItemSelected(item);
    }



    public void cropPic(View v){
        String[] temptype = picPath.split("\\.");
        String type = temptype[temptype.length-1];
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        switch (type) {
            case "jpg":
                break;
            case "png":
                format = Bitmap.CompressFormat.PNG;
                break;
        }
        Bitmap bitmap = mClipImageLayout.clip();
        PicCompress pc = new PicCompress();
        bitmap = pc.comp(bitmap,format);

        saveMyBitmap(bitmap,format);


        Intent intent = new Intent();
        intent.putExtra("bitmap", uri);
        setResult(1, intent);
        finish();
    }

    public void rotateClock(View v) {
        mClipImageLayout.rotate();
    }

    private void saveMyBitmap(Bitmap mBitmap,Bitmap.CompressFormat format)  {
        String temp = picPath;

        String[] arr = temp.split("/");
        temp = arr[arr.length - 1];
        String bitName = temp;
        File f = new File(PathConfig.IMG_CROP + "/" + bitName);
        File file = new File(PathConfig.IMG_CROP);
        uri = "file://" + PathConfig.IMG_CROP + "/" + bitName;
        FileOutputStream fOut = null;

        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(format, 100, fOut);
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
