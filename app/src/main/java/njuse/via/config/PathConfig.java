package njuse.via.config;

import android.os.Environment;

/**
 * Created by cylong on 2015-07-21 0021
 */
public class PathConfig {
    public static final String ROOT = Environment.getExternalStorageDirectory() + "/Via";
    public static final String IMG_COPY = ROOT + "/copy";
    public static final String IMG_CROP = ROOT + "/crop";
    public static final String IMG_ORIGINAL = ROOT + "/original";
    public static final String DATA_SER = ROOT + "/ser";
    public static final String WEB = ROOT + "/web";
    public static final String WEB_CSS = WEB + "/css";
    public static final String WEB_JS = WEB + "/js";
    public static final String WEB_PROJECT = WEB + "/project";
}
