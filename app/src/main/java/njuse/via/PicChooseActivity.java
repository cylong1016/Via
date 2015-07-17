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

/**
 * Created by zr on 2015/7/14.
 */
public class PicChooseActivity extends Activity{
    String uri = "";
    private static final int ALBUM = 0;
    private static final int CAMERA = 1;
    private static final int CROP_PIC = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String path  = "file:///sdcard/Android/Via/";
        String dirpath = "/sdcard/Android/Via";
        //创建文件夹
        File file = new File(dirpath);
        if(!file.exists()){
            try {
                file.mkdirs();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        //确定这一次图片的时间戳
        String time = String.valueOf(System.currentTimeMillis());
        path = path + "img_"+time+".jpg";

        //String loc = getResources().getString(R.string.pic_location);
        //uri = Uri.parse(path);
        String type = getIntent().getStringExtra("type");
        switch (type){
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



    public void getCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //图片输出到uri

        startActivityForResult(intent, CAMERA);
    }
    public void getAlbum(){
        //获取相册
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, ALBUM);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case CAMERA:
                //cutpicture(uri,300,300,CROP_PIC);



//
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//                byte[] datas = baos.toByteArray();

                Uri camerauri = data.getData();
                Bitmap bitmap = decodeUriAsBitmap(camerauri);

                String temp[] = camerauri.toString().split("/");
                String bitname = temp[temp.length-1];
                bitname = bitname.split("\\.")[0];
                saveMyBitmap(bitmap,bitname);

                Intent intent = new Intent();
                intent.setClass(this,MakeActivity.class);
                intent.putExtra("bitmap", uri);
                setResult(10, intent);
                Log.e("putextra","put data");
                finish();
                break;
            case ALBUM:
                //cutpicture(uri,300,300,CROP_PIC);
                Uri tempuri = data.getData();
                Log.e("tempuri",tempuri.toString());
                Bitmap bitmap1 = decodeUriAsBitmap(tempuri);
                String temp1[] = tempuri.toString().split("/");
                bitname = temp1[temp1.length-1];
                bitname = bitname.split("\\.")[0];
                saveMyBitmap(bitmap1,bitname);


                Intent inte = new Intent();
                inte.setClass(this,MakeActivity.class);
                inte.putExtra("bitmap", uri);
                setResult(10, inte);
                finish();
                break;
            case CROP_PIC:
                if(uri!=null){
                   // myListener.showImage(uri);
                }
            default:
                break;
        }
    }

    private void saveMyBitmap(Bitmap mBitmap,String bitName)  {
        Log.e("myuri",bitName);
        File f = new File( "/sdcard/Android/Via/"+bitName + "_copy.jpg");
        uri = "file:///sdcard/Android/Via/"+bitName + "_copy.jpg";
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
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
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    //裁剪
    private void cutpicture(Uri uri,int outputX,int outputY,int requestCode){
        //调用API
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置裁剪参数
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出参数
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        //输出到uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        //返回结果
        startActivityForResult(intent, requestCode);
    }



}
