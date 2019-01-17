package com.example.felixmac.tp_ip_infodb.FindMyCarb.parser;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.List;

public class CarburantsParser implements ContentHandler {

    private int i = 0;
    private int indice = -1;
    private String s;
    private List<List<String>> listOfLists = new ArrayList<List<String>>();

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        switch (this.i) {
            case 1:
                String str = (new String(ch, start, length)).trim();
                if (!str.equals(""))
                    listOfLists.get(indice).add("adresse:" + str); // Si l'adresse n'est pas null on la stock
                break;
            case 2:
                String str2 = (new String(ch, start, length)).trim();
                if (!str2.equals(""))
                    listOfLists.get(indice).add("ville:" + str2.toLowerCase()); // Si l'adresse n'est pas null on la stock
                break;
        }
    }

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (qName.equals("adresse")) {
            this.i = 1;
            indice++;
            listOfLists.add(new ArrayList<String>());
        } else {
            if (qName.equals("ville")) {
                this.i = 2;
            } else {
                if (qName.equals("prix")) {
                    for (int i = 0; i < atts.getLength(); ++i) {
                        if(atts.getLocalName(i).equals("nom")){
                            this.s = atts.getValue(i);
                        }
                        if (atts.getLocalName(i).equals("valeur")) {
                            listOfLists.get(indice).add(this.s + ":" + atts.getValue(i));
                        }
                    }
                    this.i = 0;
                } else this.i = 0;
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    @Override
    public void skippedEntity(String name) throws SAXException {

    }

    public List<List<String>> getListOfLists() {
        return listOfLists;
    }
}
