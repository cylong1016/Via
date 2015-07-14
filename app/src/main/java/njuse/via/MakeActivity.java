package njuse.via;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MakeActivity extends Activity {

    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);

        getScreenInfo();
        initComponent(); // 初始化部分组件的位置
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void getScreenInfo() {
        /* 获取屏幕宽高 */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    /**
     * 初始化部分组件的位置
     */
    private void initComponent() {

        int photoHeight = (int)(screenHeight * 17.0 / 23); // 装载图片组件的高

        EditText explainEdit = (EditText) findViewById(R.id.explain); // 获得输入文字的组件
        int explainX = (int) (screenWidth * (31.0 / 720));
        int explainY = (int) (photoHeight * (609.0 / 850));
        int explainW = (int) (screenWidth * (657.0 / 720));
        int explainH = (int) (photoHeight * (146.0 / 850));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(explainW, explainH);
        params.setMargins(explainX, explainY, 0, 0);
        explainEdit.setLayoutParams(params);
    }

    /** 预览图是否打开 */
    private boolean previewOn = false;

    public void previewListener(View view) {
        previewOn = !previewOn;
        TextView preview = (TextView) findViewById(R.id.preview); // 获取预览图组件
        int height = 0;
        preview.setHeight(preview.getHeight() * 6);
//        while(true) {
//            try {
//                Thread.sleep(20);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
