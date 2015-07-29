package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

import njuse.via.config.PathConfig;
import njuse.via.util.Util;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        copyJSAndCSSFile(); // 拷贝js和css文件到手机中
        copyTemplate(); // 拷贝模板图片到手机中
    }

    private void copyTemplate() {
        Field[] fields = R.raw.class.getFields();
        for(Field f : fields) {
            String name = f.getName();
            if(name.startsWith("template")) {
                int id = getResources().getIdentifier(name, "raw", getPackageName());
                Util.copyFileFromRaw(id, name + ".png", PathConfig.WEB_TEMPLATE, this);
            }
        }
    }

    /**
     * 把raw中的css和js文件复制到手机sdcard中
     * by cylong
     */
    private void copyJSAndCSSFile() {
        Util.copyFileFromRaw(R.raw.blur_css, "blur_css.css", PathConfig.WEB_CSS, this);
        Util.copyFileFromRaw(R.raw.full_page, "full_page.css", PathConfig.WEB_CSS, this);
        Util.copyFileFromRaw(R.raw.global, "global.css", PathConfig.WEB_CSS, this);
        Util.copyFileFromRaw(R.raw.index, "index.css", PathConfig.WEB_CSS, this);
        Util.copyFileFromRaw(R.raw.blur, "blur.js", PathConfig.WEB_JS, this);
        Util.copyFileFromRaw(R.raw.glb, "glb.js", PathConfig.WEB_JS, this);
        Util.copyFileFromRaw(R.raw.lufylegend, "lufylegend.js", PathConfig.WEB_JS, this);
        Util.copyFileFromRaw(R.raw.pic_main, "pic_main.js", PathConfig.WEB_JS, this);
        Util.copyFileFromRaw(R.raw.show_tile, "show_tile.js", PathConfig.WEB_JS, this);
        Util.copyFileFromRaw(R.raw.jquery_easing, "jquery_easing.js", PathConfig.WEB_JS, this);
        Util.copyFileFromRaw(R.raw.jquery_full_page_min, "jquery_full_page_min.js", PathConfig.WEB_JS, this);
        Util.copyFileFromRaw(R.raw.jquery_min, "jquery_min.js", PathConfig.WEB_JS, this);
        Util.copyFileFromRaw(R.raw.no_photo, "no_photo.jpg", PathConfig.WEB, this);
        Util.copyFileFromRaw(R.raw.icon_diamond, "icon_diamond.png", PathConfig.WEB, this);
    }

    public void makeButtonListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, TemplateActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    public void readButtonListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, ReadActivity.class);
        this.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

}
