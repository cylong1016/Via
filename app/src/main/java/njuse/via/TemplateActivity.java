package njuse.via;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import njuse.via.util.ColorAnimationView;
import njuse.via.util.Util;

/**
 * 模板的activity
 * Created by cylong on 2015-07-29 0029
 */
public class TemplateActivity extends FragmentActivity {

    /** 模板图片，先写死吧 */
    private final int[] resource = new int[]{
            R.mipmap.template_1_real,
            R.mipmap.template_2_real,
//            R.mipmap.template_3_real,
//            R.mipmap.template_4_real,
//            R.mipmap.template_5_real,
//            R.mipmap.template_6_real,
//            R.mipmap.template_7_real,
//            R.mipmap.template_8_real,
//            R.mipmap.template_9_real
    };


    private final int[] template = new int[]{
            R.mipmap.template_1,
            R.mipmap.template_2,
//            R.mipmap.template_3,
//            R.mipmap.template_4,
//            R.mipmap.template_5,
//            R.mipmap.template_6,
//            R.mipmap.template_7,
//            R.mipmap.template_8,
//            R.mipmap.template_9
    };

    private ArrayList<Bitmap> imgCache = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);
        for(int id : resource) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
            imgCache.add(bitmap);
        }

        MyFragmentStatePager adpter = new MyFragmentStatePager(getSupportFragmentManager());
        ColorAnimationView colorAnimationView = (ColorAnimationView) findViewById(R.id.ColorAnimationView);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(adpter);
        /**
         *  首先，你必须在 设置 Viewpager的 adapter 之后在调用这个方法
         *  第二点，setmViewPager(ViewPager mViewPager,Object obj, int count, int... colors)
         *         第一个参数 是 你需要传人的 viewpager
         *         第二个参数 是 一个实现了ColorAnimationView.OnPageChangeListener接口的Object,用来实现回调
         *         第三个参数 是 viewpager 的 孩子数量
         *         第四个参数 int... colors ，你需要设置的颜色变化值~~ 如何你传人 空，那么触发默认设置的颜色动画
         * */
        /**
         *  Frist: You need call this method after you set the Viewpager adpter;
         * Second: setmViewPager(ViewPager mViewPager,Object obj， int count, int... colors)
         *          so,you can set any length colors to make the animation more cool!
         * Third: If you call this method like below, make the colors no data, it will create
         *          a change color by default.
         * */
        colorAnimationView.setmViewPager(viewPager, resource.length);
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
            Util.clearImgCache(imgCache);
            TemplateActivity.this.finish();
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
        Util.clearImgCache(imgCache);
        this.finish();
    }

    public class MyFragmentStatePager extends FragmentStatePagerAdapter {

        public MyFragmentStatePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new MyFragment(position);
        }

        @Override
        public int getCount() {
            return resource.length;
        }
    }

    @SuppressLint("ValidFragment")
    public class MyFragment extends Fragment {
        private int position;

        public MyFragment(int position) {
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setImageBitmap(imgCache.get(position));
            imageView.setId(template[position]);
            imageView.setOnClickListener(clickListener);
            return imageView;
        }
    }
}