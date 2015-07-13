package njuse.via.po;

/**
 * 幕的类型
 * Created by cylong on 2015-07-09 0009
 */
public enum ScreenEnum {
    START("开始幕"),
    NORMAL("正常幕"),
    TRANSITION("过度幕"),
    OPTION("选项幕"),
    ENDING("结束幕");

    private final String value;

    ScreenEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
