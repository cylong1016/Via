package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.FileNotFoundException;

/**
 * ��������
 * Created by cylong on 2015-07-09
 */
public class MakeActivity extends Activity {


    public static String picPath = null;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        getScreenInfo(); // �����Ļ���?
        initTextEditSize(); // ��ʼ��������λ�úʹ�С
        initPhotoViewSize(); // ��ʼ����ʾͼƬ��view��λ�úʹ�С
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
        /* ��ȡ��Ļ���? */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    /**
     * ��ʼ��������λ��
     */
    private void initTextEditSize() {
        double imgH = 850.0;
        double imgW = 720.0;
        double magniscale = screenWidth / imgW; // ͼƬ�Ŵ�ı���?
        int photoHeight = (int) (screenHeight * 17.0 / 23); // װ��ͼƬ����ĸ�?

        EditText explainEdit = (EditText) findViewById(R.id.explain); // ����������ֵ����
        int explainX = (int) (magniscale * 68.0);
        int explainY = (int) (photoHeight * (672.0 / imgH));
        int explainW = (int) (magniscale * 583.0);
        int explainH = (int) (explainW * (122.0 / 583.0));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(explainW, explainH);
        params.setMargins(explainX, explainY, 0, 0);
        explainEdit.setLayoutParams(params);
    }

    /**
     * ��ʼ����ʾͼƬ�����λ��?
     */
    public void initPhotoViewSize() {
        ImageView photoView = (ImageView) findViewById(R.id.photoView); // �����ʾͼƬ��view
        double imgH = 850.0;
        double imgW = 720.0;
        int photoHeight = (int) (screenHeight * 17.0 / 23); // װ��ͼƬ����ĸ�?

        double magniscaleW = screenWidth / imgW; // ͼƬ��Ŵ�ı���
        double magniscaleH = photoHeight / imgH; // ͼƬ�߷Ŵ�ı���?
        int viewX;
        int viewY;
        int viewW;
        int viewH;

        if(magniscaleW < magniscaleH) { // ģ��ͼƬ�������ʾͼƬ��view�Ͽ�
            double temp = (photoHeight - imgH * magniscaleW) / 2;
            viewX = (int) (magniscaleW * 68.0);
            viewY = (int) (61.0 * magniscaleW + temp + dpTopx(25)); // TODO
            Log.i("height", temp + " " + screenHeight + " " + photoHeight + " " + magniscaleW);
            viewW = (int) (magniscaleW * 583.0);
            viewH = viewW;
        } else {
            viewX = 0;
            viewY = 0;
            viewW = 0;
            viewH = 0;
        }
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(viewW, viewH);
        params.setMargins(viewX, viewY, 0, 0);
        photoView.setLayoutParams(params);
    }

    /**
     * ��dip��dpֵת��Ϊpxֵ����֤�ߴ��С����?
     * @param dipValue
     * @return
     */
    public int dpTopx(float dipValue) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * Ԥ��ͼ�Ƿ��?
     */
    private boolean previewOn = false;

    public void previewListener(View view) {
        previewOn = !previewOn;
        TextView preview = (TextView) findViewById(R.id.preview); // ��ȡԤ��ͼ���?
        ImageView expend = (ImageView) findViewById(R.id.expand); // ��ȡ����ťͼƬ

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
     * ѡ��ͼƬ����
     * @param view
     */
    public void selectPhotoListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, SelectPhotoActivity.class);
        intent.putExtra("type", "camera");
        this.startActivityForResult(intent, 1);
    }


    /**
     * �������˵�����
     * @param view
     */
    public void backToMainListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        //�����л�����������߽��룬�ұ��˳�?
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
    /**
     * �˾��İ�ť�ļ���
     * @param view
     */
    public void filterListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, FilterActivity.class);
        this.startActivity(intent);
    }

    /**
     * ���水ť�ļ���
     * @param view
     */
    public void saveListener(View view) {

    }

    /**
     * �ü���ť�ļ���
     * @param view
     */
    public void cropListener(View view) {
        if(picPath!=null) {
            Intent intent = new Intent();
            intent.setClass(this, CropPicActivity.class);
            this.startActivityForResult(intent, 0);
        }
        else{
            Toast.makeText(this, "没有导入图片", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1) {
            ImageView mImageView = (ImageView) findViewById(R.id.photoView);
            byte[] b = data.getByteArrayExtra("bitmap");
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
            if (bitmap != null) {

                mImageView.setImageBitmap(bitmap);
            }
        }
        if(resultCode==2){
            Log.e("back", "back to make");
            picPath = data.getStringExtra("bitmap");
            Uri uri = Uri.parse(data.getStringExtra("bitmap"));
            Bitmap bit = decodeUriAsBitmap(uri);
            ImageView mImageView = (ImageView) findViewById(R.id.photoView);
            mImageView.setImageBitmap(bit);
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
     * ��ͼ��ť����
     * @param view
     */
    public void pasterListener(View view) {

    }

    /**
     * ���ѡ�ť����?
     * @param view
     */
    public void selectListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, OptionActivity.class);
        this.startActivity(intent);
    }

    /**
     * �½���ť����
     * @param view
     */
    public void newScreenListener(View view) {

    }

}

