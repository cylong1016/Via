package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.ByteArrayOutputStream;

import njuse.via.R;
import njuse.via.crop.ClipImageLayout;
import njuse.via.crop.ShowImageActivity;

/**
 * Created by zr on 2015/7/17.
 */
public class CropPicActivity extends Activity{
    private ClipImageLayout mClipImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Bitmap bitmap = mClipImageLayout.clip();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] datas = baos.toByteArray();

        Intent intent = new Intent();
        intent.putExtra("bitmap", datas);
        setResult(1,intent);
        finish();
    }

}
