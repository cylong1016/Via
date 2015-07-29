package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 模板的activity
 * Created by cylong on 2015-07-29 0029
 */
public class TemplateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        addTemplate();  // 添加模板
    }

    private void addTemplate() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.template_layout);
        Field[] templates = R.mipmap.class.getFields();
        for(int i = 0; i < templates.length; i++) {
            String effectName = templates[i].getName();
            if(effectName.startsWith("template")) {
                i++; // 跳过后面的实际模板图片
                // 效果图片ID
                int effectImgID = getResources().getIdentifier(effectName, "mipmap", getPackageName());
                int templateID = getResources().getIdentifier(effectName + "_real", "mipmap", getPackageName());
                RelativeLayout relativeLayout = createTemplateView(effectImgID, templateID, effectName);
                linearLayout.addView(relativeLayout);
            }
        }
    }

    private ImageView createImageView(int id, int templateID) {
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(id);
        imageView.setId(templateID); // 用来知道当前模板图片的ID
        LinearLayout.LayoutParams imgViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        imageView.setLayoutParams(imgViewParams);
        imageView.setOnClickListener(clickListener);
        return imageView;
    }

    private TextView createViewTitle(String name) {
        TextView titleView = new TextView(this);
        titleView.setText(name);
        titleView.setBackgroundResource(R.color.text_background);
        titleView.setTextColor(Color.WHITE);
        titleView.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        titleView.setLayoutParams(textParams);
        return titleView;
    }

    /**
     * 创建模板view
     * @param effectID 模板效果图片id
     * @param templateID 实际模板ID
     * @param name 模板图片名称
     * @return RelativeLayout
     */
    private RelativeLayout createTemplateView(int effectID, int templateID, String name) {
        ImageView imageView = createImageView(effectID, templateID);
        TextView titleView = createViewTitle(name);
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.addView(imageView);
        relativeLayout.addView(titleView);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.weight = 1;
        int margin = (int) getResources().getDimension(R.dimen.read_list_margin);
        params.setMargins(0, 0, margin, 0);
        relativeLayout.setLayoutParams(params);
        return relativeLayout;
    }

    /**
     * 点击进入的监听
     */
    private ImageView.OnClickListener clickListener = new ImageView.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            Intent intent = new Intent();
            intent.setClass(TemplateActivity.this, MakeActivity.class);
            intent.putExtra("id", id);
            TemplateActivity.this.startActivity(intent);
        }
    };

    /**
     * 返回主菜单监听
     */
    public void backListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }
}
