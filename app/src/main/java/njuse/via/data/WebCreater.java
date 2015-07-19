package njuse.via.data;

import java.util.LinkedList;

import njuse.via.po.Screen;
import njuse.via.po.ScreenSet;

/**
 * Created by zucewei on 2015/7/19.
 */
public class WebCreater {
    public static void createHTML(ScreenSet screenSet){
        ForFileWriter(createWeb(screenSet),screenSet.getWorkName()+".html");
    }

    public static String createWeb(ScreenSet screenSet){
        String string = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<meta name=\"viewport\"\n" +
                "\t  content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0,\n" +
                "\t  maximum-scale=1.0,user-scalable=no,minimal-ui\"/>\n" +
                "\n" +
                "<title>" + screenSet.getWorkName() + "</title>\n"+
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"global.css\" />\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"blur_css.css\">\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"jquery.fullPage.css\" />\n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"jquery-1.8.3.min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"jquery.easing.1.3.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"jquery.fullPage.min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"blur.js\"></script>\n" +
                "</head>\n"+"<body>\n" +
                "\n" +
                "<style>\n" +
                ".u-arrow{width:23px;height:auto;margin-left:-12px;position:absolute;}\n" +
                "</style>\n" +
                "<!-- <ul id=\"menu\"></ul> -->\n" +
                "<div id=\"fullpage\">\n" +
                divCreater(screenSet) +
                "\t<div class=\"section p8\">\n" +
                "\t\t<!--<a href=\"#\"><img src=\"images/text8a.png\" class=\"a\" /></a>-->\n" +
                "\t\t<a href=\"#\"><img src=\"images/text8b.png\" class=\"b\" /></a>\n" +
                "\t</div>\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "\n" +
                "$(document).ready(function () {\n" +
                "\t$('#fullpage').fullpage({\n" +
                "\t\tscrollingSpeed:500,\n" +
                "\t\teasing:'',\n" +
                "\t\tsectionsColor: ['', '', ''],\n" +
                "\t\tnavigation:0,\n" +
                "\t\tslidesNavigation: true,\n" +
                "\t\tloopHorizontal:0\n" +
                "\t});\n" +
                "});\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>";
        return string;
    }

    private static String divCreater(ScreenSet screenSet){
        String str = "";
        LinkedList<Screen> screenList = screenSet.getScreenList();
        for(int i = 0; i < screenList.size(); i++){
            Screen screen = screenList.get(i);
            str = str +"\n" +
                    "\t<div class=\"section\">\n" +
                    "\t\t<img class=\"bg blur\" src=\""+screen.getBackGroundURL()+"\"/>\n" +
                    "\t\t<div class=\"type t1\">\n" +
                    "\t\t\t<div class=\"images\">\n" +
                    "\t\t\t\t<img src=\"+screen.getBackGroundURL()+\"/>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t\t<div class=\"text\">\n" +
                    "\t\t\t\t<p>+"+ screen.getText()+"</p>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +
                    "\t</div>\n" +
                    "\n";
        }
        return str;
    }

    public static void ForFileWriter(String string,String fileName) {
        Web_IO io=new Web_IO();
        io.write_file(fileName,string);
    }
}
