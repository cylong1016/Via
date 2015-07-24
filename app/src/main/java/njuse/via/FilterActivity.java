package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import njuse.via.filter.newFilter.BlackWhiteFilter;
import njuse.via.filter.newFilter.ColorQuantizeFilter;
import njuse.via.filter.newFilter.IImageFilter;
import njuse.via.filter.newFilter.Image;
import njuse.via.filter.newFilter.InvertFilter;
import njuse.via.filter.newFilter.MirrorFilter;
import njuse.via.filter.newFilter.ReflectionFilter;
import njuse.via.filter.newFilter.SaturationModifyFilter;
import njuse.via.filter.newFilter.ThresholdFilter;
import njuse.via.filter.newFilter.TintFilter;

public class FilterActivity extends Activity {

    private Bitmap bmpDefaultPic = null, bitmap, cropDefault, cropBmp;
    private ImageView iv;
    private String url;//原图的图片，original
    private boolean isCrop = false;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        iv = (ImageView) findViewById(R.id.photo);
        url = getIntent().getStringExtra("path").substring(7);
        textView = (TextView) findViewById(R.id.runtime);
        textView.setVisibility(View.GONE);

        if (url.contains("crop")) {
            cropDefault = BitmapFactory.decodeFile(url);
            cropBmp = cropDefault;
            isCrop = true;
            url = url.replace("crop", "original");
        } else if (url.contains("copy")) {
            url = url.replace("copy", "original");
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

    public void NoListener(View view) {
        bitmap = bmpDefaultPic;
        iv.setImageBitmap(bitmap);
        cropBmp = cropDefault;
    }


    public void LomoListener(View view) {
        MirrorFilter filter = new MirrorFilter(false);
        Image img = new Image(bmpDefaultPic);
        img = filter.process(img);
        img.copyPixelsFromBuffer();
        bitmap = img.getImage();
        iv.setImageBitmap(bitmap);
        if (isCrop) {
            MirrorFilter filter_Crop = new MirrorFilter(false);
            Image img_crop = new Image(cropDefault);
            img_crop = filter_Crop.process(img_crop);
            img_crop.copyPixelsFromBuffer();
            cropBmp = img_crop.getImage();
        }
    }

    public void setImgFilter(IImageFilter imageFilter) {
        Image img = new Image(bmpDefaultPic);
        img = imageFilter.process(img);
        img.copyPixelsFromBuffer();
        bitmap = img.getImage();
        iv.setImageBitmap(img.getImage());
    }

    public void setCropFilter(IImageFilter imageFilter) {
        Image img_crop = new Image(cropDefault);
        img_crop = imageFilter.process(img_crop);
        img_crop.copyPixelsFromBuffer();
        cropBmp = img_crop.getImage();
    }

    public void SatListener(View view) {
        SaturationModifyFilter filter = new SaturationModifyFilter();
        new processImageTask(FilterActivity.this, filter).execute();
    }

    public void ReflectListener2(View view) {
        ReflectionFilter filter = new ReflectionFilter(false);
        new processImageTask(FilterActivity.this, filter).execute();
    }

    public void TintListener(View view) {
        TintFilter filter = new TintFilter();
        new processImageTask(FilterActivity.this, filter).execute();
    }

    public void ColorListener(View view) {
        ColorQuantizeFilter filter = new ColorQuantizeFilter();
        new processImageTask(FilterActivity.this, filter).execute();
    }

    public void InvertListener(View view) {
        InvertFilter filter = new InvertFilter();
        new processImageTask(FilterActivity.this, filter).execute();
    }

    public void BlackWhiteListener(View view) {
        BlackWhiteFilter filter = new BlackWhiteFilter();
        new processImageTask(FilterActivity.this, filter).execute();
    }

    public void ThreListener(View view) {
        ThresholdFilter filter = new ThresholdFilter();
        setImgFilter(filter);
        setCropFilter(filter);
        new processImageTask(FilterActivity.this, filter).execute();
    }

    public void ensureFilter(View view) throws IOException {
//        setResult(RESULT_CANCELED, null);
        try {
            saveMyBitmap("copy", bitmap);
            if (isCrop) {
                saveMyBitmap("crop", cropBmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent();
        setResult(16, intent);
        intent.setClass(this, MakeActivity.class);
        finish();
    }

    public void saveMyBitmap(String tempUrl, Bitmap bmpTemp) throws IOException {
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

    public class processImageTask extends AsyncTask<Void, Void, Bitmap> {
        private IImageFilter filter;
        private Activity activity = null;

        public processImageTask(Activity activity, IImageFilter imageFilter) {
            this.filter = imageFilter;
            this.activity = activity;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            textView.setVisibility(View.VISIBLE);
        }

        public Bitmap doInBackground(Void... params) {
            Image img = null;
            try {
                setImgFilter(filter);
                setCropFilter(filter);
            } catch (Exception e) {
                if (img != null && img.destImage.isRecycled()) {
                    img.destImage.recycle();
                    img.destImage = null;
                    System.gc(); // 提醒系统及时回收
                }
            } finally {
                if (img != null && img.image.isRecycled()) {
                    img.image.recycle();
                    img.image = null;
                    System.gc(); // 提醒系统及时回收
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                super.onPostExecute(result);
            }
            textView.setVisibility(View.GONE);
        }
    }

}
