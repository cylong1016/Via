package njuse.via.data;

import java.util.LinkedList;

import njuse.via.config.PathConfig;
import njuse.via.po.Option;
import njuse.via.po.Screen;
import njuse.via.po.ScreenEnum;
import njuse.via.po.ScreenSet;
import njuse.via.po.TreasureSet;

/**
 * Created by zucewei on 2015/7/19
 */
public class WebCreater {
    public static void createHTML(String path,ScreenSet screenSet){
        ForFileWriter(path,createWeb(screenSet),screenSet.getProjectName()+".html");
    }

    public static String createWeb(ScreenSet screenSet){
        String string = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\">\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "<meta name=\"viewport\"\n" +
                "\t  content=\"width=device-width,initial-scale=1.0,minimum-scale=1.0,\n" +
                "\t  maximum-scale=1.0,user-scalable=no,minimal-ui\"/>\n" +
                "\n" +
                "<title>" + screenSet.getProjectName() + "</title>\n"+
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/global.css\" />\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/blur_css.css\">\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/index.css\">\n" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/full_page.css\" />\n" +
                "\n" +
                "<script type=\"text/javascript\" src=\"../../js/jquery_min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"../../js/jquery_easing.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"../../js/jquery_full_page_min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"../../js/blur.js\"></script>\n" +

                "<script type=\"text/javascript\" src=\"../../js/lufylegend.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"../../js/pic_main.js\"></script>\n" +
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

        String temp = screenSet.getTemplateName();
        String tempClass = "temp_" + temp;
        String tempPath = "../../template/";
        String tempImg = tempPath + "template_" + temp + ".png";
        String tempBgImg = tempImg;

        String bg_blur = "temp_bg opacity_5";

        if (temp.equals("3")){
            tempImg = "";
            bg_blur = "temp_bg";
        }
        else if(temp.equals("0")){
            tempImg = "";
            tempBgImg = "";
            bg_blur = "blur_bg";
        }

        for(int i = 0; i < screenList.size(); i++){
            Screen screen = screenList.get(i);
            ScreenEnum e = screen.getScreenEnum();

            boolean isOption = (e==ScreenEnum.OPTION)?true:false;
            boolean isTreasure = (e==ScreenEnum.TREASURE)?true:false;
            boolean isPuzzle = (e==ScreenEnum.PUZZLE)?true:false;

            str = str +"\n" +
                    "\t<div class=\"section ";
            if (isOption){
                str = str + " hasSel\">\n";
            }
            else if(isTreasure){
                str = str + " hasTreasure\">\n";
            }
            else if(isPuzzle){
                str = str + " hasPuzzle\">\n";
            }
            else{
                str = str + "\">\n";
            }
            str = str +
                    "\t\t<img class=\"bg \" src=\""+screen.getBackGroundURL()+"\"/>\n" +
                    "<img class=\"" + bg_blur + "\" src=\""+ tempBgImg + "\" />" +
                    "\t\t<div class=\"type t3 " + tempClass + "\"" +
                    "style=\"background: url('" + tempImg + "')" + "\"></div>\n" +

                    "\t\t<div class=\"type t2 " + tempClass + "\"></div>\n" +
                    "style=\"background: url('" + tempImg + "')" + "\"></div>\n" +

                    "\t\t<div class=\"type t1 " + tempClass + "\">\n" +
                    "style=\"background: url('" + tempImg + "')" + "\"></div>\n" +
                    "<img class=\"temp_bg\" src=\"" + tempImg + "\" />" +
                    "\t\t\t<div class=\"images\">\n";

            if(!isPuzzle){
                str = str + "\t\t\t\t<img src=\"" + screen.getBackGroundURL() + "\"/>\n";
            }

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
            else if(isTreasure){
                str = str + "<div class=\"treasure\">\n";
                TreasureSet ts = screen.getTreasureSet();
                for (int k = 0; k <ts.getTreasuresList().size(); k++){
                    str = str + "\t\t\t\t\t<img id=\"diamond_" + k +
                            "\" class=\"diamond\" src=\"../../icon_diamond.png\" style=\"" +
                            "left:" + ts.getTreasuresList().get(k).getX()*100 +
                            "%; top:" + ts.getTreasuresList().get(k).getY()*100 +
                            "%\"/>\n";
                }

                str = str + "\t\t\t\t\t<span>一共有<i></i>个宝藏，你找到了<i>0</i>个， 还有 <i>3</i> 次机会</span>\n" +
                        "</div>";

            }

            else if(isPuzzle){
                str = str + "<div id=\"mytest\">loading……</div>\n" +
                        "\t\t\t\t<img class=\"right-img\" src=\"" + screen.getBackGroundURL() + "\"/>\n" +
                        "<span class=\"file-name\" style=\"display: none;\">" + screenSet.getProjectName() + "</span>" +
                        "<span class=\"current-num\" style=\"display: none;\">" + i + "</span>" +
                        "\t\t\t\t<span class=\"correct-res\">拼出来啦</span>";
            }

            str = str +
                    "\t\t\t</div>\n" +
                    "\t\t\t<div class=\"text\">\n" +
                    "\t\t\t\t<p>"+ screen.getText()+"</p>\n" +
                    "\t\t\t</div>\n";
            if (temp.equals("1")){
                str = str + "<img class=\"tage\" src=\"" + tempPath + "template_tape.png" + "\" />";
            }
            str = str + "\t\t</div>\n" +
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
