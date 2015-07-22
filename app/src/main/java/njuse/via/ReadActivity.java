package njuse.via;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
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
            imageView.setImageResource(R.drawable.cat);
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



}
