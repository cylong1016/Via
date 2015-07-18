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
import android.widget.TextView;


public class ReadActivity extends Activity {

    LinearLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        layout = (LinearLayout) findViewById(R.id.read_layout);
        TextView tx = new TextView(this);
        tx.setText("我是动态添加的");
        layout.addView(tx, 0);
        for(int i = 0 ;i < 5; i ++) {
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            p.setMargins(10,10,0,0);
            final ImageButton mImageView = new ImageButton(this);
//            File path = Environment.getExternalStorageDirectory(); //���SDCardĿ¼
//            Bitmap bm = BitmapFactory.decodeFile(path + "/Pictures/108.jpg", null);
            Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.filter);
            mImageView.setImageBitmap(bm);
            mImageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction()==MotionEvent.ACTION_DOWN)
                    {
                        mImageView.setBackgroundColor(Color.rgb(127, 127, 127));
                    }
                    else if(event.getAction()==MotionEvent.ACTION_UP)
                    {
                        mImageView.setBackgroundColor(Color.TRANSPARENT);
                    }
                    return false;
                     }
                }
                );
                layout.addView(mImageView,p);
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
