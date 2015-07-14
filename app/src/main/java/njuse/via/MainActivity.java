package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButton();

//        make.setBackgroundDrawable(drawable); // ���ñ�����Ч�������б߿򼰵�ɫ��
    }

    public void setButton() {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE); // ����
        drawable.setStroke(1, Color.LTGRAY); // �߿��ϸ����ɫ

        Button make = (Button) findViewById(R.id.MakeButton);
        Button read = (Button) findViewById(R.id.ReadButton);
        make.setBackground(drawable);
        read.setBackground(drawable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_make, menu);
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

    /**
     * ��ʼ������ť�ļ���
     * @param view
     */
    public void makeButtonListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this,MakeActivity.class);
        this.startActivity(intent);
    }

    /**
     * ��ȡ�İ�ť�ļ���
     * @param view
     */
    public void readButtonListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this,ReadActivity.class);
        this.startActivity(intent);
    }
}
