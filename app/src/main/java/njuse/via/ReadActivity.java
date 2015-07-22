package njuse.via;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * 读取制作测作品
 * Created by cylong on 2015-07-09
 */
public class ReadActivity extends Activity {

    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        layout = (LinearLayout) findViewById(R.id.read_layout);
        for (int i = 0; i < 5; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            int mergin = (int)getResources().getDimension(R.dimen.read_list_margin);
            params.setMargins(mergin, mergin, mergin, mergin);
            final ImageButton imageView = new ImageButton(this);
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
            imageView.setImageBitmap(bm);
            imageView.setOnTouchListener(new View.OnTouchListener() {
                                             @Override
                                             public boolean onTouch(View v, MotionEvent event) {
                                                 if (event.getAction() == MotionEvent.ACTION_DOWN) {
                                                     imageView.setBackgroundColor(Color.rgb(127, 127, 127));
                                                 } else if (event.getAction() == MotionEvent.ACTION_UP) {
                                                     imageView.setBackgroundColor(Color.TRANSPARENT);
                                                 }
                                                 return false;
                                             }
                                         }
            );
            layout.addView(imageView, params);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_read, menu);
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
