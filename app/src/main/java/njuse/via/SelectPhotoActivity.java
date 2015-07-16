package njuse.via;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by cylong on 2015-07-15 0015
 */
public class SelectPhotoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
    }

    /**
     * 撤销按钮的点击监听
     * @param view
     */
    public void cancelBtnListener(View view) {
        this.finish();
    }
}
