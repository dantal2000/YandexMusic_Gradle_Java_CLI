/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dantal.locale.utils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.*;

/**
 * @author dantal
 */
public class StringTaker {

    private static final Map<String, String> texts = new HashMap<>();
    private static final String LS = System.getProperty("line.separator");

    static {
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(getStringsStream());

            Element root = document.getRootElement();
            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                Element element = it.next();
                if (element.getName().equals("text")) {
                    Text text = new Text();
                    for (Iterator<Attribute> attributes = element.attributeIterator(); attributes.hasNext(); ) {
                        Attribute attribute = attributes.next();
                        if (attribute.getName().equals("name")) {
                            text.setName(attribute.getValue());
                            break;
                        }
                    }
                    for (Iterator<Element> strings = element.elementIterator(); strings.hasNext(); ) {
                        Element string = strings.next();
                        if (string.getName().equals("string")) text.addString(string.getText());
                    }

                    if (!text.getName().equals("__NULL__")) {
                        StringBuilder builder = new StringBuilder();
                        text.strings.forEach(s -> builder.append(s).append(LS));
                        texts.put(text.getName(), builder.toString());
                    }
                }
            }

            document.clearContent();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static String getString(String name) {
        return texts.get(name);
    }

    private static InputStream getStringsStream() {
        String language = Locale.getDefault().getLanguage();
        String path = "strings.xml";
        switch (language) {
            case "en":
                path = "en/" + path;
                break;
            case "ru":
            default:
                path = "ru/" + path;
        }
        path = "res/launcher/locale/" + path;
        return ClassLoader.getSystemClassLoader().getResourceAsStream(path);
    }

    private static class Text {

        private String name;
        private List<String> strings;

        Text() {
            name = "__NULL__";
            strings = null;
        }

        void setName(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void addString(String s) {
            if (strings == null) strings = new ArrayList<>();
            strings.add(s);
        }
    }
}
