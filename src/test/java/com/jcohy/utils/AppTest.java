package com.jcohy.utils;

import static org.junit.Assert.assertTrue;

import com.jcohy.utils.wth.WordToHtml;
import com.jcohy.utils.wth.WordToHtmlParseUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Unit test for simple App.
 */
public class AppTest {
    String path = null;
    @Before
    public void testReadByDoc() throws Exception {
        WordToHtml wordToHtml = new WordToHtml("D:\\files\\终端用户系统需求功能说明v1.1.3.doc", "D:\\files\\");
        path = wordToHtml.getTargetFilePath();
        System.out.println(wordToHtml.getTargetFilePath());
    }

    @Test
    public void testHtml() throws Exception {

        WordToHtmlParseUtils parseHtml = new WordToHtmlParseUtils(path);
        parseHtml.getHtmlStyle();

    }
}
