package njuse.via.bl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by lenovo on 2015/7/15.
 * 图片压缩
 */
public class PicCompress {

    public Bitmap compressImage(Bitmap image,Bitmap.CompressFormat format) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(format, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 80;
        String time = String.valueOf(System.currentTimeMillis());
        Log.e("start",time);


        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //System.out.println("mylength:" + baos.toByteArray().length);
            image.compress(format, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
            if(options<=0){
                break;
            }
        }
        String time2 = String.valueOf(System.currentTimeMillis());
        Log.e("end",time2);

        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;


        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, option);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public Bitmap comp(Bitmap image,Bitmap.CompressFormat format) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(format, 100, baos);

        Log.e("mylength",":"+baos.toByteArray().length/1024);

        if( baos.toByteArray().length / 1024>1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(format, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        Log.e("nowlength",":"+baos.toByteArray().length/1024);
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 1980f;//这里设置高度为800f
        float ww = 1080f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;//降低图片从ARGB888到RGB565
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap,format);//压缩好比例大小后再进行质量压缩
    }

    public Bitmap compressPre(Bitmap image,Bitmap.CompressFormat format) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(format, 80, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中

        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;

        Log.e("baos:",baos.toByteArray().length+";");
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, option);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
