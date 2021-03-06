package njuse.via;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import njuse.via.bl.PicCompress;
import njuse.via.config.PathConfig;

/**
 * Created by zr on 2015/7/14
 */
public class PicChooseActivity extends Activity {
    String uri = "";
    Uri camerauri = null;
    private static final int ALBUM = 0;
    private static final int CAMERA = 1;
    private static final int CROP_PIC = 2;
    private String type = "jpeg";
    PicCompress pc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path = "file://" + PathConfig.IMG_ORIGINAL;
        String dirpath = PathConfig.IMG_ORIGINAL;
        //创建文件夹
        pc = new PicCompress();
        File file = new File(dirpath);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //确定这一次图片的时间戳
        String time = String.valueOf(System.currentTimeMillis());
        path = path + "/img_" + time + ".jpg";
        camerauri = Uri.parse(path);
        //String loc = getResources().getString(R.string.pic_location);
        //uri = Uri.parse(path);
        String type = getIntent().getStringExtra("type");
        switch (type) {
            case "camera":
                getCamera();
                break;
            case "album":
                getAlbum();
                break;
            default:
                break;
        }
    }


    public void getCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //图片输出到uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, camerauri);
        startActivityForResult(intent, CAMERA);
    }

    public void getAlbum() {
        //获取相册
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if(resultCode==RESULT_CANCELED){
                    Log.e("canceled","here");
                    finish();
                }
        if(resultCode==RESULT_OK){
            switch(requestCode) {
            case CAMERA:
                Bitmap bitmap = decodeUriAsBitmap(camerauri);
                bitmap = pc.comp(bitmap, Bitmap.CompressFormat.JPEG);

               String temp[] = camerauri.toString().split("/");
                String bitname = temp[temp.length - 1];
                saveMyBitmap(bitmap, bitname, Bitmap.CompressFormat.JPEG);
                saveOriginalBitmap(bitmap, bitname, Bitmap.CompressFormat.JPEG);

                /*
                回收
                * */
                bitmap.recycle();
                System.gc();

                Intent intent = new Intent();
                intent.setClass(this, MakeActivity.class);
                intent.putExtra("path", uri);
                setResult(10, intent);
                Log.e("putextra", "put data");
                finish();
                break;
            case ALBUM:
                //cutpicture(uri,300,300,CROP_PIC);
                Uri tempuri = data.getData();
                Log.e("myuri",tempuri.toString());
                Bitmap bitmap1 = decodeUriAsBitmap(tempuri);



                String temp1[] = tempuri.toString().split("/");
                bitname = temp1[temp1.length - 1].replace("%3A", "_");
                String[] temptype = bitname.split("\\.");
                type = temptype[temptype.length-1];
                Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                switch(type){
                    case "jpg":
                        break;
                    case "png":
                       format = Bitmap.CompressFormat.PNG;
                        break;
                    default:
                        bitname = bitname+".jpg";
                        break;
                }

                bitmap1 = pc.comp(bitmap1, format);
                saveOriginalBitmap(bitmap1,bitname, format);
                saveMyBitmap(bitmap1, bitname, format);

                bitmap1.recycle();
                System.gc();

                Intent inte = new Intent();
                inte.setClass(this, MakeActivity.class);
                //inte.putExtra("bitmap",datas2);
                inte.putExtra("path", uri);
                setResult(10, inte);
                finish();
                break;
            case CROP_PIC:
                if (uri != null) {
                    // myListener.showImage(uri);
                }
            default:
                break;

            }
        }
    }

    private void saveOriginalBitmap(Bitmap mBitmap,String bitName,Bitmap.CompressFormat format)  {
        File f = new File(PathConfig.IMG_ORIGINAL + "/" + bitName);
        File file = new File(PathConfig.IMG_ORIGINAL);
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

    private void saveMyBitmap(Bitmap mBitmap,String bitName,Bitmap.CompressFormat format)  {
        File f = new File(PathConfig.IMG_COPY + "/" + bitName);
        File file = new File(PathConfig.IMG_COPY);
        uri = "file://" + PathConfig.IMG_COPY + "/" + bitName;
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


    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = 2;
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri), null, bitmapOptions);
        } catch (FileNotFoundException e) {
            Log.e("error", "unknown");
            e.printStackTrace();
            Log.e("error", "unknown");
            return null;
        }
        return bitmap;
    }


}
