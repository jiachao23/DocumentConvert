package com.jcohy.utils.wth;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by jiac on 2018/11/8.
 * ClassName  : com.jcohy.utils.wth
 * Description  :
 */
public class TestDocument {

//    @Test
//    public void testReadByDoc() throws Exception {
//
////        WordToHtml wordToHtml = new WordToHtml();
////        wordToHtml.documentConverter("D:\\StarTimes\\物联网云平台\\终端用户系统需求功能说明v1.1.3.doc",
////                "D:\\StarTimes\\物联网云平台\\终端用户系统需求功能说明v1.1.html");
//        File file = WordToHtml.converterSimpleHtml(new File("D:\\files\\终端用户系统需求功能说明v1.1.3.doc"), "D:\\files\\");
//        System.out.println(file.getPath());
//    }

    @Test
    public void testHtml() throws Exception {

       ParseHtml parseHtml = new ParseHtml("D:\\files\\1.html");
       parseHtml.getHtmlStyle();

    }
}
