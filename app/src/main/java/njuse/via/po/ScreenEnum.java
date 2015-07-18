package njuse.via.po;

import java.io.Serializable;

/**
 * 幕的类型
 * Created by cylong on 2015-07-09 0009
 */
public enum ScreenEnum implements Serializable {
    NORMAL("正常幕"),
    OPTION("选项幕");

    private final String value;
    ScreenEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
