package com.jcohy.utils.model;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by jiac on 2018/11/8.
 * ClassName  : com.jcohy.utils.model
 * Description  :
 */
public class StyleSheel {

    private Map<String, Map<String, String>> stylesheet = new LinkedHashMap<String, Map<String, String>>();

    public void parseStylesheet(Document document){
        List<String> classNames = document.select("p").stream().map(Element::className).distinct().collect(Collectors.toList());

        String style = document.getElementsByTag("style").toString();
//        System.out.println(style);
        for (String className : classNames) {
            String prefix = "\\." + className+"[ \\s]*\\{[^\\}]+?\\}";
            Pattern r = Pattern.compile(prefix);
            Matcher m = r.matcher(style);
            if (m.find( )) {
                System.out.println("Found value: " + m.group(0) );
            } else {
                System.out.println("NO MATCH");
            }
        }
    }


}
