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
     * ���հ�ť����
     * @param view
     */
    public void takePhotoListener(View view) {

    }

    /**
     * �����ѡ��ť����
     * @param view
     */
    public void pickPhotoListener(View view) {

    }

    /**
     * ������ť�ĵ������
     * @param view
     */
    public void cancelBtnListener(View view) {
        this.finish();
    }

}
