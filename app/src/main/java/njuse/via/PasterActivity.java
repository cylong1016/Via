package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import njuse.via.paster.SingleTouchView;


/**
 * Created by Administrator on 2015/7/19 0019
 */
public class PasterActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_paster);
    }

    public void paster1Listener(View view){
        Intent intent=new Intent();
        setResult(3,intent);
        this.finish();
    }

    public void cancelOptionBtnListener(View view) {
        this.finish();
    }
}
