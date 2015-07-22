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
import njuse.via.filter.MoltenFilter;
import njuse.via.filter.SoftGlowFilter;
import njuse.via.filter.newFilter.BigBrotherFilter;
import njuse.via.filter.newFilter.BlackWhiteFilter;
import njuse.via.filter.newFilter.ColorQuantizeFilter;
import njuse.via.filter.newFilter.IImageFilter;
import njuse.via.filter.newFilter.Image;
import njuse.via.filter.newFilter.InvertFilter;
import njuse.via.filter.newFilter.MirrorFilter;
import njuse.via.filter.newFilter.NewBrightContrastFilter;
import njuse.via.filter.newFilter.NewFeatherFilter;
import njuse.via.filter.newFilter.ReflectionFilter;
import njuse.via.filter.newFilter.SaturationModifyFilter;
import njuse.via.filter.newFilter.ThresholdFilter;
import njuse.via.filter.newFilter.TintFilter;

public class FilterActivity extends Activity {

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

    public void NoListener(View view) {
        bitmap = bmpDefaultPic;
        iv.setImageBitmap(bitmap);
        cropBmp = cropDefault;
    }

    public void IceListener(View view) {
        IceFilter filter = new IceFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        IceFilter filter_Crop = new IceFilter(cropDefault);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }
    public void MoltenListener(View view) {
        MoltenFilter filter = new MoltenFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        MoltenFilter filter_Crop = new MoltenFilter(cropDefault);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }
    public void ComicListener(View view) {
        ComicFilter filter = new ComicFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        ComicFilter filter_Crop = new ComicFilter(cropDefault);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }
    public void SoftGlowListener(View view) {
        SoftGlowFilter filter = new SoftGlowFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        SoftGlowFilter filter_Crop = new SoftGlowFilter(cropDefault);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }
    public void GlowingEdgeListener(View view) {
        GlowingEdgeFilter filter = new GlowingEdgeFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        GlowingEdgeFilter filter_Crop = new GlowingEdgeFilter(cropDefault);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }
    public void FeatherListener(View view) {
        FeatherFilter filter= new FeatherFilter(bmpDefaultPic);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        FeatherFilter filter_Crop = new FeatherFilter(cropDefault);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }
//    public void ZoomBlurListener(View view) {
//        ZoomBlurFilter filter = new ZoomBlurFilter(bmpDefaultPic,20);
//        bitmap = filter.imageProcess().getDstBitmap();
//        iv.setImageBitmap(bitmap);
//        if(isCrop) {
//            ZoomBlurFilter filter_Crop = new ZoomBlurFilter(cropDefault,20);
//            cropBmp = filter_Crop.imageProcess().getDstBitmap();
//        }
//    }
    public void LomoListener(View view) {
//        LomoFilter filter = new LomoFilter(bmpDefaultPic);
//        bitmap = filter.imageProcess().getDstBitmap();
//        iv.setImageBitmap(bitmap);

        MirrorFilter filter = new MirrorFilter(false);
        Image img = new Image(bmpDefaultPic);
        img = filter.process(img);
        img.copyPixelsFromBuffer();
        iv.setImageBitmap(img.getImage());
        if(isCrop) {
            MirrorFilter filter_Crop = new MirrorFilter(false);
            Image img_crop = new Image(cropDefault);
            img_crop = filter_Crop.process(img_crop);
            img_crop.copyPixelsFromBuffer();
            cropBmp = img_crop.getImage();
        }
    }
    public void FilmListener(View view) {
        FilmFilter filter = new FilmFilter(bmpDefaultPic,20);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        FilmFilter filter_Crop = new FilmFilter(cropDefault,20);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }
    public void RedToneListener(View view) {
        ColorToneFilter filter = new ColorToneFilter(bmpDefaultPic,0xFF0000,192);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        ColorToneFilter filter_Crop = new ColorToneFilter(cropDefault,0xFF0000,192);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }
    public void BlueToneListener(View view) {
        ColorToneFilter filter = new ColorToneFilter(bmpDefaultPic,0x0000FF,192);
        bitmap = filter.imageProcess().getDstBitmap();
        iv.setImageBitmap(bitmap);
        ColorToneFilter filter_Crop = new ColorToneFilter(cropDefault,0x0000FF,192);
        cropBmp = filter_Crop.imageProcess().getDstBitmap();
    }

    public void BrightListener(View view){
        NewBrightContrastFilter filter = new NewBrightContrastFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }

    public void setImgFilter(IImageFilter imageFilter){
        Image img = new Image(bmpDefaultPic);
        img = imageFilter.process(img);
        img.copyPixelsFromBuffer();
        iv.setImageBitmap(img.getImage());
    }

    public void setCropFilter(IImageFilter imageFilter){
        Image img_crop = new Image(cropDefault);
        img_crop = imageFilter.process(img_crop);
        img_crop.copyPixelsFromBuffer();
        cropBmp = img_crop.getImage();
    }

    public void SatListener(View view){
        SaturationModifyFilter filter = new SaturationModifyFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void ReflectListener1(View view){
        ReflectionFilter filter = new ReflectionFilter(true);
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void ReflectListener2(View view){
        ReflectionFilter filter = new ReflectionFilter(false);
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void TintListener(View view){
        TintFilter filter = new TintFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void BigBrotherListener(View view){
        BigBrotherFilter filter = new BigBrotherFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void ColorListener(View view){
        ColorQuantizeFilter filter = new ColorQuantizeFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void NewFeatherListener(View view){
        NewFeatherFilter filter = new NewFeatherFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void InvertListener(View view){
        InvertFilter filter = new InvertFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void BlackWhiteListener(View view){
        BlackWhiteFilter filter = new BlackWhiteFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void ThreListener(View view){
        ThresholdFilter filter = new ThresholdFilter();
        setImgFilter(filter);
        setCropFilter(filter);
    }
    public void ensureFilter(View view) throws IOException{
//        this.startActivity(intent);
//        new Thread() { // ��ֹ�л�����
//            public void run() {
//
//            }
//        }.start();
//        setR0esult(RESULT_CANCELED, null);
        try {
            saveMyBitmap("copy",bitmap);
            if(isCrop) {
                saveMyBitmap("crop",cropBmp);
            }
    } catch (IOException e) {
        e.printStackTrace();
    }
        Intent intent = new Intent();
        setResult(16,intent);
        intent.setClass(this, MakeActivity.class);
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
