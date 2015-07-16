package njuse.via;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * Created by cylong on 2015-07-15 0015
 */
public class SelectPhotoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_photo);
        initBtnListener(); // ��ʼ����ť����
    }

    /**
     * ������գ�ѡ�����ͼƬ��ȡ����ť�ļ���
     */
    public void initBtnListener() {
        Button takePhoto = (Button) findViewById(R.id.btn_take_photo);
        takePhoto.setOnTouchListener(btnTouchListener);
        Button pickPhoto = (Button) findViewById(R.id.btn_pick_photo);
        pickPhoto.setOnTouchListener(btnTouchListener);
        Button cancel = (Button) findViewById(R.id.btn_cancel);
        cancel.setOnTouchListener(btnTouchListener);
    }

    /**
     * ��ť�İ����ɿ�����
     */
    public View.OnTouchListener btnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundResource(R.mipmap.btn_click);
            } else if(event.getAction() == MotionEvent.ACTION_UP){
                v.setBackgroundResource(R.mipmap.btn_nor);
            }
            return false;
        }
    };

    /**
     * ������ť�ĵ������
     * @param view
     */
    public void cancelBtnListener(View view) {
        this.finish();
    }
}
