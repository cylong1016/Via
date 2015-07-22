package njuse.via;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 显示制作好生成的网页
 * Created by cylong on 2015-07-09
 */
public class ShowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        this.setContentView(R.layout.activity_show);
        String webName = intent.getStringExtra("html");
        WebView myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl("file:///sdcard/Via/web_product/" + webName + ".html");
        WebSettings webSetting = myWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

}
