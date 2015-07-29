package njuse.via.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * 一些常用的方法
 * Created by cylong on 2015-07-28 0028
 */
public class Util {

    public static void copyFileFromRaw(int id, String fileName, String dirPath, Context context) {
        String filePath = dirPath + "/" + fileName;// 文件路径
        File dir = new File(dirPath);// 目录路径
        if (!dir.exists()) {// 如果不存在，则创建路径名
            dir.mkdirs();   // 创建该路径名，返回true则表示创建成功
        }
        // 目录存在，则将apk中raw中的需要的文档复制到该目录下
        try {
            File file = new File(filePath);
            InputStream ins = context.getResources().openRawResource(id);// 通过raw得到数据资源
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[8192];
            int count = 0;// 循环写出
            while ((count = ins.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.close();// 关闭流
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 递归删除文件和文件夹
     *
     * @param file 要删除的根目录
     */
    public static void recursionDeleteFile(File file) {
        if (file.isFile()) {
            file.delete();
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                file.delete();
                return;
            }
            for (File f : childFile) {
                recursionDeleteFile(f);
            }
            file.delete();
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 :
                (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 :
                (int) Math.min(Math.floor(w / minSideLength),
                        Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) &&
                (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    public static void clearImgCache(ArrayList<Bitmap> imgCache) {
        for (int i = 0; i < imgCache.size(); i++) {
            imgCache.get(i).recycle();
        }
        imgCache.clear();
        System.gc();
    }

}
