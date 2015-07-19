package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

/**
 * Created by Lf on 2015/7/19.
 */
public class ShowActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        this.setContentView(R.layout.activity_show);
        String webName=intent.getStringExtra("html");
        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("file:///sdcard/Via/web_product/"+webName+".html");
//        myWebView.loadUrl("file:///sdcard/Via/web_product/"+"web2"+".html");
//        myWebView.loadUrl("http://www.baidu.com");
        WebSettings webSetting = myWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
    }

}
