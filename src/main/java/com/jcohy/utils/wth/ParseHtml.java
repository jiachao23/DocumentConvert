package com.jcohy.utils.wth;

import com.jcohy.lang.StringUtils;
import com.jcohy.utils.model.StyleSheel;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jiac on 2018/11/8.
 * ClassName  : com.jcohy.utils.wth
 * Description  :
 */
public class ParseHtml {

    public String html;
    public Document document;

    public ParseHtml(String html) throws IOException {
        this.html = html;
        File htmlf=new File(html);
        this.document = Jsoup.parse(htmlf,"UTF-8");
    }

    public void getHtmlStyle() throws IOException {
        StyleSheel styleSheel = new StyleSheel();

//        removeStyle(document);
//        removeIndex(document);
//        addheader(document);
//        addTableStyle(document);
//        addBlockQuote(document);
//        printDocument(document);
        styleSheel.parseStylesheet(document);
    }

    private Document addBlockQuote(Document document) {
        Elements divs = document.select("div>p.p8");
        List<String> h2 = divs.stream().map(Element::text).collect(Collectors.toList());

        for (Element div : divs) {
//            System.out.println(div.parent().toString());
            div.parent().append("<blockquote>"+div.html()+"</blockquote>");
//            div.parent().append(div.html());
//            div.parent().append("</blockquote>");
//            System.out.println(div.parent().toString());
            div.remove();
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

    private Document addheader(Document document) {
        Elements header = document.getElementsByTag("head");
//        System.out.println(header.toString());
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
//        System.out.println(header.toString());
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
//            System.out.println(name);
//            System.out.println(parentTagName);
            if(("目录").equals(name) && ("p").equals(parentTagName)){
                element.parent().parent().remove();
            }
        }
        return document;
    }
}
