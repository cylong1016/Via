package njuse.via.data;

import java.util.LinkedList;

import njuse.via.po.Option;
import njuse.via.po.Screen;
import njuse.via.po.ScreenEnum;
import njuse.via.po.ScreenSet;

/**
 * Created by zucewei on 2015/7/19
 */
public class WebCreater {
    public static void createHTML(String path,ScreenSet screenSet){
        ForFileWriter(path,createWeb(screenSet),screenSet.getWorkName()+".html");
    }

    public static String createWeb(ScreenSet screenSet){
        String string = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<meta name=\"viewport\"\n" +
                "\t  content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0,\n" +
                "\t  maximum-scale=1.0,user-scalable=no,minimal-ui\"/>\n" +
                "\n" +
                "<title>" + screenSet.getWorkName() + "</title>\n"+
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/global.css\" />\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/blur_css.css\">\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/index.css\">\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/full_page.css\" />\n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"../../js/jquery_min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"../../js/jquery_easing.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"../../js/jquery_full_page_min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"../../js/blur.js\"></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "<style>\n" +
                ".u-arrow{width:23px;height:auto;margin-left:-12px;position:absolute;}\n" +
                "</style>\n" +
                "<div id=\"fullpage\">" +
                divCreater(screenSet) +
                "\n" +
                "</div>\n" +
                "\n" +
                "<script type=\"text/javascript\">\n" +
                "\n" +
                "$(document).ready(function(){\n" +
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
            ScreenEnum e = screen.getScreenEnum();

            boolean isOption = (e==ScreenEnum.OPTION)?true:false;
//            boolean isTreasure = (e==ScreenEnum.NORMAL)?true:false;

            str = str +"\n" +
                    "\t<div class=\"section ";
            if (isOption){
                str = str + " hasSel\">\n";
            }
//            else if(e == ScreenEnum.NORMAL){
//                str = str + " hasTreasure\">\n";
//            }
            else{
                str = str + "\">\n";
            }
            str = str +
                    "\t\t<img class=\"bg \" src=\""+screen.getBackGroundURL()+"\"/>\n" +
                    "\t\t<div class=\"type t3\"></div>\n" +
                    "\t\t<div class=\"type t2\"></div>\n" +
                    "\t\t<div class=\"type t1\">\n" +
                    "\t\t\t<div class=\"images\">\n" +
                    "\t\t\t\t<img src=\""+screen.getBackGroundURL()+"\"/>\n";

            if(isOption){
                str = str +
                        "<div class=\"sel\">";
                Option op = screen.getOption();
                for (int j = 0; j<op.getItemLength(); j++){
                    str = str + "<div class=\"answer";
                    if (op.getOptionItem(j).getValue()){
                        str = str + " correct";
                    }
                    str = str + "\">" + op.getOptionItem(j).getText() +
                    "</div>";
                }
                str = str + "</div>";
            }

            str = str +
                    "\t\t\t</div>\n" +
                    "\t\t\t<div class=\"text\">\n" +
                    "\t\t\t\t<p>"+ screen.getText()+"</p>\n" +
                    "\t\t\t</div>\n" +
                    "\t\t</div>\n" +
                    "\t</div>\n" +
                    "\n";
        }
        return str;
    }

    public static void ForFileWriter(String path,String content,String fileName) {
        Web_IO io=new Web_IO(path);
        io.write_file(path,fileName,content);
    }
}
