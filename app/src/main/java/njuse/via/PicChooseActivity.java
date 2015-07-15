package njuse.via;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

import java.io.File;

/**
 * Created by zr on 2015/7/14.
 */
public class PicChooseActivity extends Fragment{
    Uri uri = null;
    private static final int ALBUM = 0;
    private static final int CAMERA = 1;
    private static final int CROP_PIC = 2;

    public interface PicListener{
        public void showImage(Uri uri);
    }

    private PicListener myLisener;
    private Button camera;
    private Button album;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myLisener = (PicListener)activity;
    }

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
        uri = Uri.parse(path);

    }

    @Override
    public void onResume() {
        super.onResume();
        camera = (Button)getActivity().findViewById(1);
        album = (Button)getActivity().findViewById(1);

        MyButtonClickListener clickListener = new MyButtonClickListener();
       camera.setOnClickListener(clickListener);
        album.setOnClickListener(clickListener);
    }

    class MyButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button button = (Button)v;
            if(button ==camera){
                getCamera();
            }
            else if(button==album){
                getAlbum();
            }
            else{

            }
        }
    }


    public void getCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //图片输出到uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
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
                cutpicture(uri,300,300,CROP_PIC);
                break;
            case ALBUM:
                cutpicture(uri,300,300,CROP_PIC);
                break;
            case CROP_PIC:
                if(uri!=null){
                    myLisener.showImage(uri);
                }
            default:
                break;
        }
    }

    /*
    private Bitmap decodeUriAsBitmap(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
    */
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
