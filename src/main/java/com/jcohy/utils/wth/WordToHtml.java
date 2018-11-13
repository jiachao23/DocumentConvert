package com.jcohy.utils.wth;

import com.jcohy.utils.DocumentConverter;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

    public  Document htmlDocument;

    //要转换的文件
    public String sourcePath;
    public String targetPath;
    public String targetFilePath;
    public WordToHtml(String  sourcePath,  String targetPath) {
        this.sourcePath = sourcePath;
        this.targetPath = targetPath;
        this.targetFilePath = targetPath;
        converterSimpleHtml(sourcePath,targetPath);
    }

    public  File converterSimpleHtml(String sourcePath,final String targetPath){
        OutputStream outStream = null;
        InputStream is = null;
        File sourceFile = new File(sourcePath);
        String filename = sourceFile.getName().substring(0,sourceFile.getName().lastIndexOf("."));
        targetFilePath = targetPath+filename+prefix;
        File htmlFile = new File(targetFilePath);
        try {
            is = new FileInputStream(sourceFile);
            HWPFDocument doc = new HWPFDocument(is);
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                @Override
                public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches, float heightInches) {
                    File imgPath = new File(targetPath+File.separator+"images");
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
            htmlDocument = wordToHtmlConverter.getDocument();
            printAttrs(htmlDocument);
            afterProcess(htmlDocument);
            outStream = new FileOutputStream(htmlFile);
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(outStream);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer serializer = factory.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
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

    private  void afterProcess(Document htmlDocument) {
        Node rootNode = htmlDocument.getFirstChild();
        if(rootNode.hasChildNodes()){
            NodeList childNodes = rootNode.getChildNodes();
            for(int i = 0; i< childNodes.getLength();i++){
                //body
                if(childNodes.item(i).getNodeName().equals("body")){
                    if(childNodes.item(i).hasChildNodes()){
                        createBlockQuote(htmlDocument,childNodes.item(i));
                    }
                }
            }
        }
    }

    @Override
    public File documentConverter( File file,final String path) {
        return null;
    }


    private  void createBlockQuote(Document htmlDocument, Node node) {
        //divs
        NodeList childNodes = node.getChildNodes();
        System.out.println(node.getNodeName());
        for(int i=0;i<childNodes.getLength();i++){
            System.out.println(childNodes.item(i).getNodeName());
            if(childNodes.item(i).getNodeName().equals("div")){
                //拷贝一份div的内容
                System.out.println(childNodes.item(i).getTextContent());
                System.out.println(childNodes.item(i).getOwnerDocument());
                Node node1 = childNodes.item(i).cloneNode(true);
                Element blockquote = htmlDocument.createElement("blockquote");
                node.appendChild(blockquote).appendChild(node1);
            }else{
                removeNode(node,childNodes.item(i));
            }
        }
    }

    /**
     * 删除node
     * @param node
     * @param childNode
     */
    private  void removeNode(Node node, Node childNode) {
        Node parentNode = node;
        if(childNode.hasChildNodes()){

            parentNode = childNode;
            for(int i = 0;i<childNode.getChildNodes().getLength();i++){
                removeNode(parentNode,childNode.getChildNodes().item(i));
            }
        }else{
            parentNode.removeChild(childNode);
        }

    }



    private  void printAttrs(Document doc) {

        String nodeName = doc.getFirstChild().getNodeName();
        org.w3c.dom.Node firstChild = doc.getFirstChild();

        if(firstChild.getNodeName() != null)System.out.println("getNodeName:"+firstChild.getNodeName());
        if(doc.getChildNodes() != null) System.out.println("doc.getChildNodes():"+firstChild.getChildNodes().getLength());
        if(firstChild.getFirstChild() != null) System.out.println("getFirstChild:"+firstChild.getFirstChild().getNodeName());
        if(firstChild.getLastChild() != null)System.out.println("getLastChild:"+firstChild.getLastChild().getNodeName());
        if(firstChild.getNextSibling() != null)System.out.println(":getNextSibling"+firstChild.getNextSibling().getNodeName());

        if(firstChild.getBaseURI() != null)System.out.println("getBaseURI:"+firstChild.getBaseURI());
        if(firstChild.getLocalName() != null)System.out.println("getLocalName:"+firstChild.getLocalName());
        if(firstChild.getNamespaceURI() != null)System.out.println("getNamespaceURI:"+firstChild.getNamespaceURI());
        if(firstChild.getNodeValue() != null)System.out.println("getNodeValue:"+firstChild.getNodeValue());
        if(firstChild.getTextContent() != null)System.out.println("getTextContent:"+firstChild.getTextContent());

        if(firstChild.getPreviousSibling() != null)System.out.println("getPreviousSibling:"+firstChild.getPreviousSibling().getNodeName());
        if(firstChild.hasChildNodes())System.out.println("hasChildNodes:"+firstChild.hasChildNodes());
        if(firstChild.getParentNode() != null)System.out.println("getParentNode:"+firstChild.getParentNode().getNodeName());
//        if(firstChild.getTextContent() != null)System.out.println("getTextContent:"+firstChild.getTextContent());
//        if(firstChild.getTextContent() != null)System.out.println("getTextContent:"+firstChild.getTextContent());


        if(firstChild.getAttributes() != null)System.out.println("getAttributes:"+firstChild.getAttributes().toString());
        if(firstChild.getPrefix() != null)System.out.println("getPrefix:"+firstChild.getPrefix().toString());

    }

    public String getSourcePath() {
        return sourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public String getTargetFilePath() {
        return targetFilePath;
    }
}
