package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by Lf on 2015/7/19.
 */
public class ShowActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String webName=intent.getStringExtra("html");
        WebView myWebView = (WebView) findViewById(R.id.webView);
//        myWebView.loadUrl("file:///sdcard/Android/Via/web_product/"+webName+".html");
        myWebView.loadUrl("http://www.baidu.com");
        WebSettings webSetting = myWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
    }

}
