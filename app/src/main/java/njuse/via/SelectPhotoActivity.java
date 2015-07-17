package njuse.via;

import android.app.Activity;
import android.content.Intent;
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
        Intent intent = new Intent();
        intent.setClass(this,PicChooseActivity.class);
        intent.putExtra("type", "camera");
        startActivityForResult(intent, 10);
    }

    /**
     * �����ѡ��ť����
     * @param view
     */
    public void pickPhotoListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this,PicChooseActivity.class);
        intent.putExtra("type", "album");
        startActivityForResult(intent, 11);
    }

    /**
     * ����ť�ĵ������
     * @param view
     */
    public void cancelBtnListener(View view) {
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==10){
            Intent intent = new Intent();
            intent.putExtra("bitmap",data.getStringExtra("bitmap"));
            setResult(2,intent);
            finish();
        }
        if(resultCode==11){
            Intent intent = new Intent();
            intent.putExtra("bitmap",data.getStringExtra("bitmap"));
            setResult(2,intent);
            finish();
        }
    }
}
