package njuse.via;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import njuse.via.bl.MakeBL;
import njuse.via.bl.PicCompress;
import njuse.via.blservice.MakeBLService;
import njuse.via.config.CommonConfig;
import njuse.via.paster.TreasureView;
import njuse.via.po.Option;
import njuse.via.po.Screen;
import njuse.via.po.ScreenEnum;
import njuse.via.po.Treasure;
import njuse.via.po.TreasureSet;
import njuse.via.util.Util;

/**
 * 制作界面
 * Created by cylong on 2015-07-09
 */
public class MakeActivity extends Activity {
    //----------------故事版的变量
    private HorizontalScrollView preview;
    private LinearLayout mGallery;
    private LayoutInflater mInflater;
    private int isselect = 0;
    private ArrayList<Integer> preInt;
    private ArrayList<ImageButton> preButton;
    private PicCompress pc;
    private PreListener plisten;
    //-----------------
    private int screenWidth;
    private int screenHeight;
    private int statusBarHeight;
    private MakeBLService makeBL = new MakeBL();

    public Screen screen;

    //----------------这里是宝藏的变量
    public ArrayList<TreasureView> treaviewset = new ArrayList<>();
    //--------------------------------

    private ArrayList<Bitmap> templateCache = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make);
        pc = new PicCompress();
        getScreenInfo(); // 获得屏幕信息
        initPhotoViewLoc();
        screen = makeBL.getNewScreen();
        initPreview();
        setTemplate();
    }

    private void setTemplate() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        ImageView template = (ImageView) findViewById(R.id.template);
        if (id != 0) {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getResources(), id, opts);
            opts.inSampleSize = Util.computeSampleSize(opts, -1, 720 * 850);
            opts.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id, opts);
            templateCache.add(bitmap);
            template.setImageBitmap(bitmap);
            String name = getResources().getResourceEntryName(id);
            String index = name.split("_")[1];
            makeBL.setTemplateName(index);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /*
    初始化故事板界面
     */
    private void initPreview() {
        preview = (HorizontalScrollView) findViewById(R.id.preview);
        mInflater = LayoutInflater.from(this);
        plisten = new PreListener();
        preButton = new ArrayList<>();
        mGallery = (LinearLayout) findViewById(R.id.id_gallery);
        /*
        初始化第一幕的缩略图
        **/
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(80, 80);
        param.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        param.addRule(RelativeLayout.CENTER_VERTICAL, 1);//布局居中
        View v = mInflater.inflate(R.layout.activity_preview_item, mGallery, false);

            /*
        初始化新建按钮
         */
        View v2 = mInflater.inflate(R.layout.activity_preview_item, mGallery, false);
        ImageButton newsc = (ImageButton) v2.findViewById(R.id.id_index_gallery_item_image);
        newsc.setId(-1);
        newsc.setBackgroundResource(R.mipmap.icon_new);
        newsc.setOnClickListener(plisten);

        ImageButton img = (ImageButton) v.findViewById(R.id.id_index_gallery_item_image);
        img.setId(screen.getID());
        img.setOnClickListener(plisten);
        preButton.add(img);
        preButton.add(newsc);
        mGallery.addView(v);
        mGallery.addView(v2);

    }

    private void setPreviewImg(Bitmap bitmap, Bitmap.CompressFormat format) {
        bitmap = pc.compressPre(bitmap, format);
        for (int i = 0; i < preButton.size(); i++) {
            if (preButton.get(i).getId() == isselect) {
                preButton.get(i).setImageBitmap(bitmap);
            }
        }
    }

    private void getScreenInfo() {
        /* 获取屏幕宽高 */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
    }

    /**
     * 初始化中间放图片和文字的的组件位置
     */
    public void initPhotoViewLoc() {
        RelativeLayout photoRelative = (RelativeLayout) findViewById(R.id.photoRelative); // 显示图片的布局
        EditText explainEdit = (EditText) findViewById(R.id.explain); // 获得输入文字的组件
        double imgH = 850.0;
        double imgW = 720.0;
        int softHeight = screenHeight - statusBarHeight; // 去掉状态栏的高度
        int photoHeight = (int) (softHeight * 17.0 / 23); // 显示图片组件的高

        double magniscaleW = screenWidth / imgW; // 宽缩放的比例
        double magniscaleH = softHeight / imgH; // 高缩放比例
        int viewX;
        int viewY;
        int viewW;
        int viewH;

        int explainX;
        int explainY;
        int explainW;
        int explainH;

        if (magniscaleW < magniscaleH) { // 图片相对于显示图片的view较宽
            double temp = (photoHeight - imgH * magniscaleW) / 2;
            viewX = (int) (magniscaleW * 66.0);
            viewY = (int) (61.0 * magniscaleW + temp + dpTopx(28));
            viewW = (int) (magniscaleW * 587.0);
            viewH = viewW;

            explainX = (int) (magniscaleW * 68.0);
            explainY = (int) (672.0 * magniscaleW + temp + dpTopx(28));
            explainW = (int) (magniscaleW * 583.0);
            explainH = (int) (explainW * (122.0 / 583.0));
            Log.i("height", explainX + " " + explainY + " " + explainW + " " + explainH);
        } else {
            // TODO 横屏
            viewX = 0;
            viewY = 0;
            viewW = 0;
            viewH = 0;

            explainX = 0;
            explainY = 0;
            explainW = 0;
            explainH = 0;
        }
        // 设置放置图片view的位置
        RelativeLayout.LayoutParams paramsView = new RelativeLayout.LayoutParams(viewW, viewH);
        paramsView.setMargins(viewX, viewY, 0, 0);
        photoRelative.setLayoutParams(paramsView);
        // 设置文字输入框的位置
        RelativeLayout.LayoutParams paramsEdit = new RelativeLayout.LayoutParams(explainW, explainH);
        paramsEdit.setMargins(explainX, explainY, 0, 0);
        explainEdit.setLayoutParams(paramsEdit);
    }

    public void initScreen() {
        cleanTreasure();
        EditText edit = (EditText) findViewById(R.id.explain);
        String text = screen.getText();
        edit.setText(text);
        ImageView img = (ImageView) findViewById(R.id.photoView);
        if (screen.getBackGroundURL() != null) {
            initTreasure();
            img.setImageURI(Uri.parse(screen.getBackGroundURL()));
        } else {
            img.setImageResource(R.mipmap.make_background);
        }

        if(screen.getScreenEnum()==ScreenEnum.NORMAL||screen.getScreenEnum()==ScreenEnum.TREASURE){
            ImageView img1=(ImageView) findViewById(R.id.select);
            img1.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
            ImageView img2=(ImageView) findViewById(R.id.puzzle);
            img2.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
        }else if(screen.getScreenEnum()==ScreenEnum.PUZZLE){
            ImageView img1=(ImageView) findViewById(R.id.select);
            img1.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
            ImageView img2=(ImageView) findViewById(R.id.puzzle);
            img2.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_icon_selector));
        }else if(screen.getScreenEnum()==ScreenEnum.OPTION){
            ImageView img1=(ImageView) findViewById(R.id.select);
            img1.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_icon_selector));
            ImageView img2=(ImageView) findViewById(R.id.puzzle);
            img2.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
        }else{
            ImageView img1=(ImageView) findViewById(R.id.select);
            img1.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
            ImageView img2=(ImageView) findViewById(R.id.puzzle);
            img2.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
        }

    }

    /**
     * dp转化成px
     *
     * @param dipValue
     * @return
     */
    public int dpTopx(float dipValue) {
        float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 预览图是否打开
     */
    private boolean previewOn = false;

    public void previewListener(View view) {
        previewOn = !previewOn;
        //---------------------------------------------------------new code
//        mGallery = (LinearLayout) findViewById(R.id.id_gallery);
//
//        for(int i = 0;i<6;i++) {
//            View v = mInflater.inflate(R.layout.activity_preview_item, mGallery, false);
//            ImageButton img = (ImageButton) findViewById(R.id.id_index_gallery_item_image);
//            mGallery.addView(v);
//        }

        //---------------------------------------------------------
        preview = (HorizontalScrollView) findViewById(R.id.preview); // 获取预览图组件
        //preview.getBackground().setAlpha(50);
        ImageView expend = (ImageView) findViewById(R.id.expand); // 获取扩大按钮图片

        RelativeLayout.LayoutParams preParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, 0);

        int expendH = expend.getLayoutParams().height;
        RelativeLayout.LayoutParams expParams =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, expendH);

        if (previewOn) {
            int previewMaxH = preview.getLayoutParams().height * 3;
            preParams.height = previewMaxH;
            preview.setLayoutParams(preParams);

            expParams.topMargin = previewMaxH;
            expend.setLayoutParams(expParams);
            expend.setImageResource(R.mipmap.icon_collapse);
        } else {
            int previewMinH = preview.getLayoutParams().height / 3;
            preParams.height = previewMinH;
            preview.setLayoutParams(preParams);

            expParams.topMargin = 0;
            expend.setLayoutParams(expParams);
            expend.setImageResource(R.mipmap.icon_expand);
        }
    }

    /**
     * 选择图片监听
     *
     * @param view
     */
    public void selectPhotoListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, SelectPhotoActivity.class);
        intent.putExtra("type", "camera");
        this.startActivityForResult(intent, 1);
    }


    /**
     * 返回主菜单监听
     *
     * @param view
     */
    public void backToMainListener(View view) {
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        //设置切换动画，从左边进入，右边退出
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        this.finish();
        Util.clearImgCache(templateCache);
    }

    /**
     * 滤镜按钮监听
     *
     * @param view
     */
    public void filterListener(View view) {
        if (screen.getBackGroundURL() != null) {
            Intent intent = new Intent();
            intent.setClass(this, FilterActivity.class);
            intent.putExtra("path", screen.getBackGroundURL());
            this.startActivityForResult(intent, 16);
        } else {
            Toast.makeText(this, getString(R.string.no_photo), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveWork(EditText editText) {
        if (editText.getText().toString() == null | editText.getText().toString().length() == 0) {
            Toast.makeText(this, R.string.no_inputName, Toast.LENGTH_SHORT).show();
            return;
        }

        arrangeTreasure();
        String workName = editText.getText().toString();
        makeBL.saveWork(workName);
        Toast.makeText(this, R.string.save_success, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
        //设置切换动画，从左边进入，右边退出
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        this.finish();
        Util.clearImgCache(templateCache);
    }

    /**
     * 保存按钮监听
     *
     * @param view
     */
    public void saveListener(View view) {
        refreshTreasure();
        screen.setText(((EditText) findViewById(R.id.explain)).getText().toString());

        final EditText editText = new EditText(this);
        Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(R.string.input_dialog).
                setIcon(android.R.drawable.ic_dialog_info).setView(
                editText).setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveWork(editText);
            }
        }).setNegativeButton(R.string.cancel, null).show();

    }

    /**
     * 裁剪按钮监听
     *
     * @param view
     */
    public void cropListener(View view) {
        if (screen.getBackGroundURL() != null) {
            String path = screen.getBackGroundURL().replace("crop", "copy");
            if (path != null) {
                Intent intent = new Intent();
                intent.setClass(this, CropPicActivity.class);
                intent.putExtra("path", path);
                this.startActivityForResult(intent, 0);
            } else {
                Toast.makeText(this, getString(R.string.img_path_error), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getString(R.string.no_photo), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            ImageView mImageView = (ImageView) findViewById(R.id.photoView);
            String path = data.getStringExtra("bitmap");
            Bitmap bitmap = decodeUriAsBitmap(Uri.parse(path));
            if (bitmap != null) {
                String[] temptype = path.split("\\.");
                String type = temptype[temptype.length - 1];
                Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
                switch (type) {
                    case "jpg":
                        break;
                    case "png":
                        format = Bitmap.CompressFormat.PNG;
                        break;
                }
                mImageView.setImageBitmap(bitmap);
                setPreviewImg(bitmap, format);
            }

            screen.setBackGroundURL(path);
        }
        if (resultCode == 2) {
            String path = data.getStringExtra("path");
            screen.setBackGroundURL(path);

            Uri uri = Uri.parse(path);

            if (uri != null) {
                Intent intent = new Intent();
                intent.setClass(this, CropPicActivity.class);
                intent.putExtra("path", path);
                this.startActivityForResult(intent, 0);
            }
        }

        if (resultCode == 3) {
            setImgAfterFilter();
        }

        if (resultCode == 98) {
            Bundle b = data.getExtras();
            Option op = (Option) b.get("roption");
            if (op != null) {
                if (op.isEff) {
                    ImageView img = (ImageView) findViewById(R.id.select);
                    img.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_icon_selector));
                    screen.setOption(op);
                }
            } else {
                ImageView img = (ImageView) findViewById(R.id.select);
                img.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
                screen.setOption(op);
            }
        }
        if (resultCode == 16) {
            setImgAfterFilter();
        }

    }

    /*
    滤镜结束之后调用这个方法
     */
    public void setImgAfterFilter() {
        Bitmap bitmap = decodeUriAsBitmap(Uri.parse(screen.getBackGroundURL()));
        Log.i("scurl", screen.getBackGroundURL());
        ImageView mImageView = (ImageView) findViewById(R.id.photoView);
        if (bitmap != null) {
            mImageView.setImageBitmap(bitmap);
        }
    }


    private Bitmap decodeUriAsBitmap(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }

    /**
     * 贴图按钮监听
     *
     * @param view
     */
    public void pasterListener(View view) {
        if (screen.getBackGroundURL() != null) {
            Intent intent = new Intent();
            intent.setClass(this, PasterActivity.class);
            intent.putExtra("path", screen.getBackGroundURL());
            this.startActivityForResult(intent, 3);
        } else {
            Toast.makeText(this, R.string.no_photo, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 选项按钮监听
     *
     * @param view
     */
    public void selectListener(View view) {
        refreshTreasure();
        if (screen.getScreenEnum() == ScreenEnum.NORMAL || screen.getScreenEnum() == ScreenEnum.OPTION) {
            Intent intent = new Intent();
            intent.setClass(this, OptionActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("option", screen.getOption());
            intent.putExtras(b);
            this.startActivityForResult(intent, 98);
        } else {
            Toast.makeText(this, R.string.not_option_screen, Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteScreenListener(View view) {
        deletePreview();
    }

    /**
     * 添加宝藏监听
     *
     * @param view
     */
    public void treasureListener(View view) {
        if (screen.getScreenEnum() == ScreenEnum.NORMAL || screen.getScreenEnum() == ScreenEnum.TREASURE) {
            if (screen.getBackGroundURL() != null) {
                screen.setScreenEnum(ScreenEnum.TREASURE);
                if (countTreasure() < CommonConfig.maxTreasureNumber) {
                    ImageView mImageView = (ImageView) findViewById(R.id.photoView);
                    RelativeLayout mLayout = (RelativeLayout) mImageView.getParent();
                    TreasureView treasureView = new TreasureView(MakeActivity.this);
                    treasureView.setImageDrawable(getResources().getDrawable(R.drawable.icon_diamond));
                    mLayout.addView(treasureView);

                    treaviewset.add(treasureView);

                    PointF p = treasureView.getCenPointPer();
                    TreasureSet set = screen.getTreasureSet();
                    Treasure trea = set.getNewTreasure();
                    trea.setX(p.x);
                    trea.setY(p.y);

                    treasureView.id = trea.getID();
                } else {
                    Toast.makeText(this, R.string.max_treasure, Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, R.string.no_photo, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.not_treasure_screen, Toast.LENGTH_SHORT).show();
        }
    }

    public void puzzleListener(View view) {
        arrangeTreasure();
        if (screen.getBackGroundURL() != null) {
            if (screen.getScreenEnum() == ScreenEnum.NORMAL) {
                if(makeBL.getScreenSet().puzzle_num==0){
                    dialog(false);
                }else{
                    Toast.makeText(this, R.string.puzzle_max_num_msg, Toast.LENGTH_SHORT).show();
                }
            } else if (screen.getScreenEnum() == ScreenEnum.PUZZLE) {
                dialog(true);
            } else {
                Toast.makeText(this, R.string.not_puzzle_screen, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.no_photo, Toast.LENGTH_SHORT).show();
        }

    }

    public int countTreasure() {
        int num = 0;
        for (int i = 0; i < treaviewset.size(); i++) {
            if (treaviewset.get(i).getVisibility() == View.VISIBLE) {
                num++;
            }
        }
        return num;
    }

    public void initTreasure() {
        treaviewset = new ArrayList<TreasureView>();

        if (screen.getBackGroundURL() != null) {
            TreasureSet set = screen.getTreasureSet();
            ArrayList<Treasure> array = set.getTreasuresList();
            ImageView mImageView = (ImageView) findViewById(R.id.photoView);
            RelativeLayout mLayout = (RelativeLayout) mImageView.getParent();

            for (int i = 0; i < array.size(); i++) {
                PointF p = new PointF((float) array.get(i).getX(), (float) array.get(i).getY());
                TreasureView treasureView = new TreasureView(MakeActivity.this, p, 0);
                treasureView.setImageDrawable(getResources().getDrawable(R.drawable.smallbackground));
                mLayout.addView(treasureView);

                treasureView.id = array.get(i).getID();

                treaviewset.add(treasureView);
            }
        }

    }

    public void arrangeTreasure() {
        if (screen.getBackGroundURL() != null) {
//            ImageView img=(ImageView) findViewById(R.id.photoView);
//            RelativeLayout mlayout=(RelativeLayout) img.getParent();
            TreasureSet set = screen.getTreasureSet();
            for (int i = 0; i < treaviewset.size(); i++) {
                if (treaviewset.get(i).getVisibility() == View.GONE || treaviewset.get(i).getVisibility() == View.INVISIBLE) {
                    set.deletTreasure(treaviewset.get(i).id);
                } else {
                    Treasure lin = set.getTreasure(treaviewset.get(i).id);
                    PointF mCen = treaviewset.get(i).getCenPointPer();
                    lin.setX(mCen.x);
                    lin.setY(mCen.y);
                }
                RelativeLayout mlayout = (RelativeLayout) treaviewset.get(i).getParent();
                mlayout.removeView(treaviewset.get(i));
            }
        }
        treaviewset = new ArrayList<>();
    }

    public void refreshTreasure() {
        int num = countTreasure();
        if (num <= 0) {
            if (screen.getScreenEnum() == ScreenEnum.TREASURE) {
                screen.setScreenEnum(ScreenEnum.NORMAL);
            }
        } else {
            screen.setScreenEnum(ScreenEnum.TREASURE);
        }
    }

    public void cleanTreasure() {
        if (screen.getBackGroundURL() != null) {
            for (int i = 0; i < treaviewset.size(); i++) {
                RelativeLayout mlayout = (RelativeLayout) treaviewset.get(i).getParent();
                mlayout.removeView(treaviewset.get(i));
            }
        }
        treaviewset = new ArrayList<TreasureView>();
    }


    /*
    缩略图的添加
     */
    private void addPreview() {

        for (int i = 0; i < preButton.size(); i++) {
            if (preButton.get(i).getId() == -1) {
                preButton.remove(i);
                mGallery.removeViewAt(i);
            }
        }

        View v = mInflater.inflate(R.layout.activity_preview_item, mGallery, false);
        ImageButton img = (ImageButton) v.findViewById(R.id.id_index_gallery_item_image);
        img.setId(screen.getID());
        img.setOnClickListener(plisten);
        /*
        把之前选中的图片变小
         */
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(80, 80);
        param.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
        param.addRule(RelativeLayout.CENTER_VERTICAL, 1);
        for (int i = 0; i < preButton.size(); i++) {
            if (preButton.get(i).getId() == isselect) {
                preButton.get(i).setLayoutParams(param);
            }
        }
        isselect = screen.getID();

        preButton.add(img);
        mGallery.addView(v);


        View v2 = mInflater.inflate(R.layout.activity_preview_item, mGallery, false);
        ImageButton newsc = (ImageButton) v2.findViewById(R.id.id_index_gallery_item_image);
        newsc.setId(-1);
        newsc.setBackgroundResource(R.mipmap.icon_new);
        newsc.setOnClickListener(plisten);
        preButton.add(newsc);
        mGallery.addView(v2);
        Log.e("height", mGallery.getChildCount() + ";");
        int postion = (mGallery.getChildCount() - 5) * 144;
        //mGallery.scrollTo(postion,0);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                preview.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        });
    }

    /*
    删除当前选中的幕
    */
    private void deletePreview() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.confirm_delete);
        builder.setTitle(R.string.prompt);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                deletePre();
            }

        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.create().show();
    }

    private void deletePre() {
        ImageView imageView = (ImageView) findViewById(R.id.photoView);
        TextView textView = (TextView) findViewById(R.id.explain);
        cleanTreasure();
        for (int i = 0; i < preButton.size(); i++) {
            if (preButton.get(i).getId() == isselect) {
                int wid = preButton.get(i).getWidth();
                int hei = preButton.get(i).getHeight();

                preButton.remove(i);

                makeBL.remove(screen);
                mGallery.removeViewAt(i);
                int tempi = i;
                /*
                如果只剩一张图片就再新建一次
                 */
                if (preButton.size() == 1) {
                    EditText edit = (EditText) findViewById(R.id.explain);
                    String text = edit.getText().toString(); // 获得用户输入的文本
                    screen.setText(text);
                    Log.e("mytext", text);
                    // 新建一幕
                    screen = makeBL.getNewScreen();
                    initScreen();
                    addPreview();
                    break;
                }
                /*
                如果删除最后一张则跳到之前一张，否则跳到之后一张
                 */
                if (i != preButton.size() - 1) {
                    isselect = preButton.get(i).getId();
                } else {
                    tempi = tempi - 1;
                    isselect = preButton.get(tempi).getId();
                }

                /*
                设置当前的isselect
                 */
                RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(wid, hei);
                param2.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
                param2.addRule(RelativeLayout.CENTER_VERTICAL, 1);
                preButton.get(tempi).setLayoutParams(param2);


                screen = makeBL.getScreenByID(isselect);

                if (screen.getBackGroundURL() != null) {
                    initTreasure();
                    Uri uri = Uri.parse(screen.getBackGroundURL());
                    imageView.setImageBitmap(decodeUriAsBitmap(uri));
                } else {
                    imageView.setImageResource(R.mipmap.make_background);
                }
                textView.setText(screen.getText());
                break;
            }
        }

    }

    protected void dialog(final boolean isPuzzle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MakeActivity.this);
        if (isPuzzle) {
            builder.setMessage(R.string.puzzle_del_msg);
        } else {
            builder.setMessage(R.string.puzzle_add_msg);
        }
        builder.setTitle(R.string.prompt);
        builder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (isPuzzle) {
                    screen.setScreenEnum(ScreenEnum.NORMAL);
                    ImageView img = (ImageView) findViewById(R.id.puzzle);
                    img.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
                    makeBL.getScreenSet().puzzle_num--;
                } else {
                    screen.setScreenEnum(ScreenEnum.PUZZLE);
                    ImageView img = (ImageView) findViewById(R.id.puzzle);
                    img.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_icon_selector));
                    makeBL.getScreenSet().puzzle_num++;
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /*缩略图的监听

     */
    class PreListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            ImageButton button = (ImageButton) v;
            if (isselect == button.getId()) {

            } else if (button.getId() == -1) {
                arrangeTreasure();               //保存宝藏
                EditText edit = (EditText) findViewById(R.id.explain);
                String text = edit.getText().toString(); // 获得用户输入的文本
                screen.setText(text);
                Log.e("mytext", text);
                // 新建一幕
                screen = makeBL.getNewScreen();
                initScreen();
                addPreview();
            } else {
                arrangeTreasure();               //保存宝藏
                ImageView imageView = (ImageView) findViewById(R.id.photoView);
                TextView textView = (TextView) findViewById(R.id.explain);
                /*
                *设置imagebutton大小变化，显示选中的那个
                **/
                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(80, 80);
                param.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
                param.addRule(RelativeLayout.CENTER_VERTICAL, 1);

                int wid = 0;
                int hei = 0;
                for (int i = 0; i < preButton.size(); i++) {
                    if (preButton.get(i).getId() == isselect) {
                        wid = preButton.get(i).getWidth();
                        hei = preButton.get(i).getHeight();
                        preButton.get(i).setLayoutParams(param);
                    }
                }
                /*把文字存入screen中*/
                screen = makeBL.getScreenByID(isselect);
                screen.setText(textView.getText().toString());
                //-----
                isselect = button.getId();

                RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(wid, hei);
                param2.addRule(RelativeLayout.CENTER_HORIZONTAL, 1);
                param2.addRule(RelativeLayout.CENTER_VERTICAL, 1);
                button.setLayoutParams(param2);
                /*
                设置当前界面的更新
                */



                screen = makeBL.getScreenByID(isselect);

                if(screen.getScreenEnum()==ScreenEnum.NORMAL||screen.getScreenEnum()==ScreenEnum.TREASURE){
                    ImageView img1=(ImageView) findViewById(R.id.select);
                    img1.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
                    ImageView img2=(ImageView) findViewById(R.id.puzzle);
                    img2.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
                }else if(screen.getScreenEnum()==ScreenEnum.PUZZLE){
                    ImageView img1=(ImageView) findViewById(R.id.select);
                    img1.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
                    ImageView img2=(ImageView) findViewById(R.id.puzzle);
                    img2.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_icon_selector));
                }else if(screen.getScreenEnum()==ScreenEnum.OPTION){
                    ImageView img1=(ImageView) findViewById(R.id.select);
                    img1.setBackgroundDrawable(getResources().getDrawable(R.drawable.option_icon_selector));
                    ImageView img2=(ImageView) findViewById(R.id.puzzle);
                    img2.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
                }else{
                    ImageView img1=(ImageView) findViewById(R.id.select);
                    img1.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
                    ImageView img2=(ImageView) findViewById(R.id.puzzle);
                    img2.setBackgroundDrawable(getResources().getDrawable(R.drawable.icon_selector));
                }

                if (screen.getBackGroundURL() != null) {
                    initTreasure();
                    Uri uri = Uri.parse(screen.getBackGroundURL());
                    imageView.setImageBitmap(decodeUriAsBitmap(uri));
                } else {
                    imageView.setImageResource(R.mipmap.make_background);
                }
                textView.setText(screen.getText());
            }
        }
    }
}

