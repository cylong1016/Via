package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 模板的activity
 * Created by cylong on 2015-07-29 0029
 */
public class TemplateActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
    }

    public void backListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
