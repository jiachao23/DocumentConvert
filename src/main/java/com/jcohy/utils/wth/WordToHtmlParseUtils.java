package com.jcohy.utils.wth;

import com.jcohy.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by jiac on 2018/11/13.
 * ClassName  : com.jcohy.utils.wth
 * Description  :
 */
public class WordToHtmlParseUtils {

    public String html;
    public Document document;
    private Map<String, Map<String, String>> stylesheet = new LinkedHashMap<String, Map<String, String>>();


    public WordToHtmlParseUtils(Document document) {
        this.document = document;

    }

    public WordToHtmlParseUtils(String html) throws IOException {
        this.html = html;
        File htmlf=new File(html);
        this.document = Jsoup.parse(htmlf,"UTF-8");
    }


    public void getHtmlStyle() throws IOException {
        addheader(document);
//        Elements select = document.select("body>div");
//        for (Element element : select) {
//            element.remove();
//        }
        addTableStyle(document);
        printDocument(document);
    }

    /**
     * 添加addBlockQuote
     * @param document
     * @return
     */
    @Deprecated
    private Document addBlockQuote(Document document) {
        Elements divs = document.select("div>p.p8");
        List<String> h2 = divs.stream().map(Element::text).collect(Collectors.toList());

        for (Element div : divs) {
            div.parent().append("<blockquote>"+div.html()+"</blockquote>");
        }
        return document;
    }

    private Document addTableStyle(Document document) {
        Elements tables = document.getElementsByTag("table");
        for (Element table : tables) {
            table.addClass("benchmark-table");
        }
        return document;
    }

    /**
     * 添加头部样式
     * @param document
     * @return
     */
    private Document addheader(Document document) {
        Elements header = document.getElementsByTag("head");
        header.append(" <link rel=\"stylesheet\" type=\"text/css\" href=\"./style/prettify.css\"/>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"./style/bootstrap.min.css\"/>\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"./style/love.css\"/>\n" +
                "    <script type=\"text/javascript\" src=\"./style/jquery.min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"./style/bootstrap.min.js\"></script>\n" +
                "<script type=\"text/javascript\" src=\"./style/prettify.js\"></script>"+
                "<script type=\"text/javascript\">\n" +
                "  $(function () {\n" +
                "    $(\"pre\").addClass(\"prettyprint linenums\");\n" +
                "    //代码高亮\n" +
                "    prettyPrint();\n" +
                "    $(\"table\").attr('class', 'table table-striped table-bordered table-condensed');\n" +
                "  });\n" +
                "</script>");
        return document;
    }

    private void printDocument(Document document) {
        FileOutputStream fos = null;
        try {
            fos= new FileOutputStream("D:\\files\\test.html", true);
            fos.write(document.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document removeStyle(Document document){
        Elements style = document.getElementsByTag("style");
        System.out.println(style);
        style.html("");
        return document;
    }

    public Document removeIndex(Document document){
        Elements span = document.getElementsByTag("span");
        for (Element element : span) {
            String name = StringUtils.cleanBlank(element.text());
            String parentTagName = element.parent().tag().getName();
            if(("目录").equals(name) && ("p").equals(parentTagName)){
                element.parent().parent().remove();
            }
        }
        return document;
    }
//    public void parseStylesheet(){
//        String style = document.getElementsByTag("style").toString();
//
//        //获取p标签属性的集合
//        Map<String, Map<String, String>> tagStyle = ParseHTMLTagStyle(document, style, "p");
//        //遍历
//        for(Map.Entry<String,Map<String, String>> entry : tagStyle.entrySet()){
//            //{...}
//            Map<String, String> classStyle = entry.getValue();
//            String className = entry.getKey();
//            //删除目录和首页等
//            String textAlign = classStyle.get("text-align");
//            String fontSize = classStyle.get("font-size");
//            Integer substring = Integer.valueOf(fontSize.substring(0, fontSize.length() - 2));
//            if(textAlign.equals("center")&& substring>10){
//                Elements taglist = document.getElementsByClass(className);
//                //判断标签的父级标签是否一致
//                for (Element element : taglist) {
//                    if(element.parent().tagName().equals("div")){
//                        if(!document.getElementsByClass(className).isEmpty()){
//                            element.parent().remove();
//                        }else{
//                            break;
//                        }
//                    }
//                }
//            }
//        }
//
//        //获取
//        List<Element> elements = document.select("a[name]").stream().filter(x ->StringUtils.isNotEmpty(x.text())).collect(Collectors.toList());
//
//        Map<String, String> fontMap = new HashMap<>();
//        List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(fontMap.entrySet());
//        //然后通过比较器来实现排序
//        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));
//
//
//        for (Element element : elements) {
//            String styleByStyleKey = styleUtils.getStyleByStyleKey(element.parent().className(), "font-size");
//            fontMap.put(element.parent().className(),styleByStyleKey);
//        }
//        if(list!= null){
//            addBlockQuote(list);
//        }
//        System.out.println(list.toString());
//
//    }

    private Map<String, Map<String, String>> ParseHTMLTagStyle(Document document, String style, String p) {
        return null;
    }

    private void addBlockQuote(List<Map.Entry<String, String>> list) {
        String key = list.get(list.size()-1).getKey();
        Elements divs = document.select("div>p."+key);
        List<String> h2 = divs.stream().map(Element::text).collect(Collectors.toList());

        for (Element div : divs) {
            div.parent().append("<blockquote>"+div.html()+"</blockquote>");
            div.remove();
        }
    }
}
