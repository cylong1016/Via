package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import java.io.FileNotFoundException;

/**
 * Created by zr on 2015/7/14.
 */
public class PicChooseActivity extends Activity{

    private static Uri uri = null;
    private static final int ALBUM = 0;
    private static final int CAMERA = 1;
    private static final int CROP_PIC = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.screen);
        String loc = getResources().getString(R.string.pic_location);
        uri = Uri.parse(loc);
        Intent temp = new Intent();
        String type = temp.getStringExtra("type");
        switch (type){
            case "camera":
                getCamera();break;
            case "album":
                getAlbum();break;
            default:
                break;
        }
    }

    public void getCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //���ݲ��������µ�ͼƬ���浽uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(intent, CAMERA);
    }
    public void getAlbum(){
        //װ��õ������¼�
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, ALBUM);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    Bitmap bitmap = decodeUriAsBitmap(uri);
                     //����ͼƬ
                    //((ImageView)findViewById(R.id.imageView)).setImageBitmap(bitmap);
                    finish();
                }
            default:
                break;
        }
    }

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
    //�ü�ͼƬ
    private void cutpicture(Uri uri,int outputX,int outputY,int requestCode){
        //���ð�׿�Դ��Ĳü�
        Intent intent = new Intent("com.android.camera.action.CROP");
        //��������
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
        intent.putExtra("aspectY", 1);
        //���òü����С
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        //�����ָ��Ŀ¼��
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        //�ص�activity�ļ���
        startActivityForResult(intent, requestCode);
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

}
