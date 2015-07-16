package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��������
 * Created by cylong on 2015-07-09
 */
public class MakeActivity extends Activity {

    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        getScreenInfo(); // �����Ļ��Ϣ
        initComponent(); // ��ʼ�����������λ��
        initListener(); // ��ʼ����ť�ļ���
    }

    public void initListener() {
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnTouchListener(touchListener);
        ImageView save = (ImageView) findViewById(R.id.save);
        save.setOnTouchListener(touchListener);
        ImageView selectPhoto = (ImageView) findViewById(R.id.selectPhoto);
        selectPhoto.setOnTouchListener(touchListener);
        ImageView crop = (ImageView) findViewById(R.id.crop);
        crop.setOnTouchListener(touchListener);
        ImageView filter = (ImageView) findViewById(R.id.filter);
        filter.setOnTouchListener(touchListener);
        ImageView paster = (ImageView) findViewById(R.id.paster);
        paster.setOnTouchListener(touchListener);
        ImageView select = (ImageView) findViewById(R.id.select);
        select.setOnTouchListener(touchListener);
        ImageView newScreen = (ImageView) findViewById(R.id.newScreen);
        newScreen.setOnTouchListener(touchListener);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(Color.BLACK);
            } else if(event.getAction() == MotionEvent.ACTION_UP){
                v.setBackgroundColor(getResources().getColor(R.color.tool_bar_background));
            }
            return false;
        }
    };

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
        /* ��ȡ��Ļ��� */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
    }

    /**
     * ��ʼ�����������λ��
     */
    private void initComponent() {

        int photoHeight = (int) (screenHeight * 17.0 / 23); // װ��ͼƬ����ĸ�

        EditText explainEdit = (EditText) findViewById(R.id.explain); // ����������ֵ����
        int imgH = 850;
        int imgW = 720;
        int explainX = (int) (screenWidth * (31.0 / imgW));
        int explainY = (int) (photoHeight * (609.0 / imgH));
        int explainW = (int) (screenWidth * (657.0 / imgW));
        int explainH = (int) (photoHeight * (146.0 / imgH));
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(explainW, explainH);
        params.setMargins(explainX, explainY, 0, 0);
        explainEdit.setLayoutParams(params);
    }

    /**
     * Ԥ��ͼ�Ƿ��
     */
    private boolean previewOn = false;

    public void previewListener(View view) {
        previewOn = !previewOn;
        TextView preview = (TextView) findViewById(R.id.preview); // ��ȡԤ��ͼ���
        ImageView expend = (ImageView) findViewById(R.id.expand); // ��ȡ����ťͼƬ

        RelativeLayout.LayoutParams preParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 0);

        int expendH = expend.getLayoutParams().height;
        RelativeLayout.LayoutParams expParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, expendH);

        if (previewOn) {
            int previewMaxH = preview.getLayoutParams().height * 3;
            preParams.height = previewMaxH;
            preview.setLayoutParams(preParams);

            expParams.topMargin = previewMaxH;
            expend.setLayoutParams(expParams);
            expend.setImageResource(R.mipmap.icon_collapse);
        } else {
            int previewMinH = preview.getLayoutParams().height / 3;
            preParams.height = previewMinH;
            preview.setLayoutParams(preParams);

            expParams.topMargin = 0;
            expend.setLayoutParams(expParams);
            expend.setImageResource(R.mipmap.icon_expand);
        }
    }

    /**
     * ѡ��ͼƬ����
     * @param view
     */
    public void selectPhotoListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, SelectPhotoActivity.class);
        this.startActivity(intent);
    }


    /**
     * �������˵�����
     * @param view
     */
    public void backToMainListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        //�����л�����������߽��룬�ұ��˳�
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
    /**
     * �˾��İ�ť�ļ���
     * @param view
     */
    public void filterListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, FilterActivity.class);
        this.startActivity(intent);
    }

    /**
     * ���水ť�ļ���
     * @param view
     */
    public void saveListener(View view) {

    }

    /**
     * �ü���ť�ļ���
     * @param view
     */
    public void cropListener(View view) {

    }

    /**
     * ��ͼ��ť����
     * @param view
     */
    public void pasterListener(View view) {

    }

    /**
     * ���ѡ�ť����
     * @param view
     */
    public void selectListener(View view) {

    }

    /**
     * �½���ť����
     * @param view
     */
    public void newScreenListener(View view) {

    }
}
