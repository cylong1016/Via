package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.ActionBarActivity;
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

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL); // ����
        drawable.setStroke(1, Color.LTGRAY); // �߿��ϸ����ɫ

        Button make = (Button) findViewById(R.id.MakeButton);
        make.setBackgroundDrawable(drawable); // ���ñ�����Ч�������б߿򼰵�ɫ��
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

    public void button1(View view) {
        Intent intent = new Intent();
        intent.setClass(this,Via.class);
        this.startActivity(intent);
    }

    public void button2(View view) {
        Intent intent = new Intent();
        intent.setClass(this,ReadActivity.class);
        this.startActivity(intent);
    }
}
