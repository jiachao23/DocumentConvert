package com.jcohy.utils.wth;

import com.jcohy.utils.DocumentConverter;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.jsoup.Jsoup;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * Created by jiac on 2018/11/8.
 * ClassName  : com.jcohy.utils.wth
 * Description  :
 */
public class WordToHtml implements DocumentConverter{
    public static String prefix = ".html";
    public static File converterSimpleHtml( File file,final String path){
        OutputStream outStream = null;
        InputStream is = null;
        String filename = file.getName().substring(0,file.getName().lastIndexOf("."));
        String newFilePath = path+filename+prefix;
        File htmlFile = new File(newFilePath);
        try {
            is = new FileInputStream(file);

            HWPFDocument doc = new HWPFDocument(is);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());

            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                @Override
                public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                    File imgPath = new File(path+File.separator+"images");
                    //图片目录不存在则创建
                    if(!imgPath.exists()){
                        imgPath.mkdirs();
                    }
                    File file = new File(imgPath + File.separator+suggestedName);
                    try {
                        OutputStream os = new FileOutputStream(file);
                        os.write(content);
                        os.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return imgPath + File.separator+suggestedName;
                }
            });

            wordToHtmlConverter.processDocument(doc);
            Document htmlDocument = wordToHtmlConverter.getDocument();

            outStream = new FileOutputStream(htmlFile);
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer serializer = factory.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
//            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(outStream == null){
                    outStream.close();
                }
                if(is == null ){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return htmlFile;
    }
    @Override
    public File documentConverter( File file,final String path) {
        return null;
    }

    public File converterSpeciHtml(File file,final String path){
        File html = converterSimpleHtml(file, path);
        Jsoup.parse(file.getName());
        return null;
    }
}
