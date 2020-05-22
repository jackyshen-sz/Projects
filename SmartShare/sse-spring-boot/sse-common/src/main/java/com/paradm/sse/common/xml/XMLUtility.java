package com.paradm.sse.common.xml;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Jacky.shen
 * @create data 2020/5/22
 */
@Slf4j
public class XMLUtility implements XMLReader {

  ContentHandler handler;
  OutputStream out = System.out;
  Document doc;

  public XMLUtility() {}

  public XMLUtility(String indexFile) { // indexFile = fullpath of xml file
    this(new InputSource(indexFile));
  }

  public XMLUtility(InputStream xmlInputStream) {
    this(new InputSource(xmlInputStream));
  }

  public XMLUtility(InputSource inputSource) {
    try {
      DocumentBuilderFactory dfactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder docBuilder = dfactory.newDocumentBuilder();
      doc = docBuilder.parse(inputSource);
    } catch (ParserConfigurationException | SAXException | IOException pce) {
      log.error(pce.getMessage(), pce);
    }
  }

  /**
   * getNodeListByTagName
   *
   * @param tagName
   * @return
   */
  public NodeList getNodeListByTagName(String tagName) {
    return doc.getElementsByTagName(tagName);
  }

  @Override
  public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
    return false;
  }

  @Override
  public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {

  }

  @Override
  public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
    return null;
  }

  @Override
  public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {

  }

  @Override
  public void setEntityResolver(EntityResolver resolver) {

  }

  @Override
  public EntityResolver getEntityResolver() {
    return null;
  }

  @Override
  public void setDTDHandler(DTDHandler handler) {

  }

  @Override
  public DTDHandler getDTDHandler() {
    return null;
  }

  @Override
  public void setContentHandler(ContentHandler handler) {

  }

  @Override
  public ContentHandler getContentHandler() {
    return null;
  }

  @Override
  public void setErrorHandler(ErrorHandler handler) {

  }

  @Override
  public ErrorHandler getErrorHandler() {
    return null;
  }

  @Override
  public void parse(InputSource input) throws IOException, SAXException {

  }

  @Override
  public void parse(String systemId) throws IOException, SAXException {

  }
}
