package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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
    private Bitmap bmpDefaultPic = null,bitmap,cropDefault,cropBmp;
    private ImageView iv;
    private String url;//原图的图片，original
    private boolean isCrop = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        iv = (ImageView)findViewById(R.id.photo);
        url = getIntent().getStringExtra("path").substring(7);


        if(url.contains("crop")) {
            cropDefault = BitmapFactory.decodeFile(url);
            cropBmp = cropDefault;
            isCrop = true;
            url = url.replace("crop","original");
        }else if(url.contains("copy")) {
            url = url.replace("copy","original");
        }
        bmpDefaultPic = BitmapFactory.decodeFile(url);
        bitmap = bmpDefaultPic;
        iv.setImageBitmap(bmpDefaultPic);
//        File path = Environment.getExternalStorageDirectory();

//        LinearLayout layout = (LinearLayout) findViewById(R.id.entirety);
//        TextView tx = new TextView(this);
//        tx.setText(url+" "+path);
//        layout.addView(tx, 0);
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
     * ��Ч��������ԭͼ
     */
    public void NoListener(View view) {
        bitmap = bmpDefaultPic;
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            cropBmp = cropDefault;
        }
    }

    public void IceListener(View view) {
        IceFilter filter = new IceFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            IceFilter filter_Crop = new IceFilter(cropDefault);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void MoltenListener(View view) {
        MoltenFilter filter = new MoltenFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            MoltenFilter filter_Crop = new MoltenFilter(cropDefault);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void ComicListener(View view) {
        ComicFilter filter = new ComicFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            ComicFilter filter_Crop = new ComicFilter(cropDefault);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void SoftGlowListener(View view) {
        SoftGlowFilter filter = new SoftGlowFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            SoftGlowFilter filter_Crop = new SoftGlowFilter(cropDefault);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void GlowingEdgeListener(View view) {
        GlowingEdgeFilter filter = new GlowingEdgeFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            GlowingEdgeFilter filter_Crop = new GlowingEdgeFilter(cropDefault);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void FeatherListener(View view) {
        FeatherFilter filter= new FeatherFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            FeatherFilter filter_Crop = new FeatherFilter(cropDefault);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void ZoomBlurListener(View view) {
        ZoomBlurFilter filter = new ZoomBlurFilter(bmpDefaultPic,20);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            ZoomBlurFilter filter_Crop = new ZoomBlurFilter(cropDefault,20);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void LomoListener(View view) {
        LomoFilter filter = new LomoFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            LomoFilter filter_Crop = new LomoFilter(cropDefault);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void FilmListener(View view) {
        FilmFilter filter = new FilmFilter(bmpDefaultPic,20);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            FilmFilter filter_Crop = new FilmFilter(bmpDefaultPic,20);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void RedToneListener(View view) {
        ColorToneFilter filter = new ColorToneFilter(bmpDefaultPic,0xFF0000,192);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            ColorToneFilter filter_Crop = new ColorToneFilter(bmpDefaultPic,0xFF0000,192);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }
    public void BlueToneListener(View view) {
        ColorToneFilter filter = new ColorToneFilter(bmpDefaultPic,0x0000FF,192);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        if(isCrop) {
            ColorToneFilter filter_Crop = new ColorToneFilter(bmpDefaultPic,0x0000FF,192);
            cropBmp = filter_Crop.imageProcess().getDstBitmap();
        }
    }

    /**
     * @param view
     * ȷ���˾�
     */
    public void ensureFilter(View view) throws IOException{
        Intent intent = new Intent();
        intent.setClass(this, MakeActivity.class);
        setResult(16,intent);
//        this.startActivity(intent);
        new Thread() { // ��ֹ�л�����
            public void run() {
                try {
                    saveMyBitmap("copy",bitmap);
                    if(isCrop) {
                        saveMyBitmap("crop",cropBmp);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
//        setR0esult(RESULT_CANCELED, null);
        finish();
    }

    public void saveMyBitmap(String tempUrl,Bitmap bmpTemp) throws IOException {
        String copyURL = url;
        copyURL = copyURL.replace("original", tempUrl);
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
