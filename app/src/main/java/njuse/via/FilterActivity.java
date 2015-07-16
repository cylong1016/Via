package njuse.via;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.io.File;


public class FilterActivity extends Activity {

    private String filename = "/mnt/media_rw/sdcard/Pictures/ai1.png";
    private ImageView myImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        File file = new File(filename);
        myImageView = (ImageView) findViewById(R.id.photo);
        if(file.exists()) {
            Bitmap bm = BitmapFactory.decodeFile(filename);
            myImageView.setImageBitmap(bm);
        } else{
            
        }
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
     * 无效果，返回原图
     */
    public void NoListener(View view) {

    }

    public void IceListener(View view) {

    }
    public void MoltenListener(View view) {

    }
    public void ComicListener(View view) {

    }
    public void SoftGlowListener(View view) {

    }
    public void GlowingEdgeListener(View view) {

    }
    public void FeatherListener(View view) {

    }
    public void ZoomBlurListener(View view) {

    }
    public void LomoListener(View view) {

    }
    public void FilmListener(View view) {

    }
    public void ColorToneListener(View view) {

    }

    /**
     * @param view
     * 确认滤镜
     */
    public void ensureFilter(View view) {

    }

}
