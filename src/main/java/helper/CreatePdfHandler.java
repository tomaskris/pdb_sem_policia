package main.java.helper;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.font.PdfType1Font;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.*;
import org.apache.commons.io.IOUtils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.swing.text.StyleConstants;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.layout.Document;

public class CreatePdfHandler extends DefaultHandler {
    private String destination;
    private PdfFont fontNormal;
    private PdfFont fontBold;

    private Document document;
    private Paragraph pripad;
    private Table podozriveTable;
    private Table hladaneTable;
    private Table obvineneTable;
    private Table odsudeneTable;
    private Table svedkoviaTable;
    private int pocetOsob = 0;

    private boolean isTyp = false;
    private boolean isDatum_vykonania = false;
    private boolean isZac_vysetrovania = false;
    private boolean isKoniec_vysetrovania = false;
    private boolean isObjasnenost = false;
    private boolean isMiesto_vykonania = false;
    private boolean isOsoba = false;




    public CreatePdfHandler(String destination) {

        try {
            this.destination = destination;
            PdfDocument pdf = new PdfDocument(new PdfWriter(destination));
            document = new Document(pdf);

            fontNormal = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN, "Cp1250");
            fontBold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD, "Cp1250");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

    }

    @Override
    public void endDocument() throws SAXException {
        document.close();
        System.out.println("END");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        setElementFlag(qName);

        pripadCheck(qName, attributes);
        osobyCheck(qName);
        osobaCheck(qName,attributes);
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        writeToPdf(ch,start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        resetElements(qName);

        // zacinaju tabulky
        if(qName.equals("Miesto_vykonania")){
            document.add(pripad);
        }

        osobyCheckEnd(qName);
    }

    private void osobyCheck(String qName) {
        if(qName.equals("Podozrive_osoby")){
            float[] podozriveVelkost = new float[2];
            podozriveVelkost[0] = 100;
            podozriveVelkost[1] = 100;

            podozriveTable = new Table(podozriveVelkost,false);
            podozriveTable.addCell((new Cell()).add(new Paragraph("Rodné číslo osoby").setFont(fontNormal)));
            podozriveTable.addCell((new Cell()).add(new Paragraph("Meno a priezvisko osoby").setFont(fontNormal)));
            pocetOsob = 0;
        }else if(qName.equals("Hladane_osoby")){
            float[] hladaneeVelkost = new float[5];
            hladaneeVelkost[0] = 100;
            hladaneeVelkost[1] = 100;
            hladaneeVelkost[2] = 100;
            hladaneeVelkost[3] = 100;
            hladaneeVelkost[4] = 100;

            hladaneTable = new Table(hladaneeVelkost,false);
            hladaneTable.addCell((new Cell()).add(new Paragraph("Rodné číslo osoby").setFont(fontNormal)));
            hladaneTable.addCell((new Cell()).add(new Paragraph("Meno a priezvisko osoby").setFont(fontNormal)));
            hladaneTable.addCell((new Cell()).add(new Paragraph("Dôvod").setFont(fontNormal)));
            hladaneTable.addCell((new Cell()).add(new Paragraph("Dátum od").setFont(fontNormal)));
            hladaneTable.addCell((new Cell()).add(new Paragraph("Dátum do").setFont(fontNormal)));

            pocetOsob = 0;
        }else if(qName.equals("Obvinene_osoby")){
            float[] velkost = new float[4];
            velkost[0] = 100;
            velkost[1] = 100;
            velkost[2] = 100;
            velkost[3] = 100;

            obvineneTable = new Table(velkost,false);
            obvineneTable.addCell((new Cell()).add(new Paragraph("Rodné číslo osoby").setFont(fontNormal)));
            obvineneTable.addCell((new Cell()).add(new Paragraph("Meno a priezvisko osoby").setFont(fontNormal)));
            obvineneTable.addCell((new Cell()).add(new Paragraph("Škoda").setFont(fontNormal)));
            obvineneTable.addCell((new Cell()).add(new Paragraph("Pokuta").setFont(fontNormal)));
        }else if(qName.equals("Odsudene_osoby")){
            float[] velkost = new float[5];
            velkost[0] = 100;
            velkost[1] = 100;
            velkost[2] = 100;
            velkost[3] = 100;
            velkost[4] = 100;


            odsudeneTable = new Table(velkost,false);
            odsudeneTable.addCell((new Cell()).add(new Paragraph("Rodné číslo osoby").setFont(fontNormal)));
            odsudeneTable.addCell((new Cell()).add(new Paragraph("Meno a priezvisko osoby").setFont(fontNormal)));
            odsudeneTable.addCell((new Cell()).add(new Paragraph("Väznica").setFont(fontNormal)));
            odsudeneTable.addCell((new Cell()).add(new Paragraph("Datum od").setFont(fontNormal)));
            odsudeneTable.addCell((new Cell()).add(new Paragraph("Pocet mesiacov").setFont(fontNormal)));

        }else if(qName.equals("Svedkovia")){
            float[] velkost = new float[2];
            velkost[0] = 100;
            velkost[1] = 100;

            svedkoviaTable = new Table(velkost,false);
            svedkoviaTable.addCell((new Cell()).add(new Paragraph("Rodné číslo osoby").setFont(fontNormal)));
            svedkoviaTable.addCell((new Cell()).add(new Paragraph("Meno a priezvisko osoby").setFont(fontNormal)));
            pocetOsob = 0;
        }
    }

    private void osobaCheck(String qName, Attributes attributes){
        if (!qName.equals("Osoba")) return;
        if (podozriveTable != null){
            podozriveTable.addCell(attributes.getValue("rod_cislo"));
            pocetOsob++;
        }
        if (hladaneTable != null){
            hladaneTable.addCell(attributes.getValue("rod_cislo"));
            pocetOsob++;
        }
        if (obvineneTable != null){
            obvineneTable.addCell(attributes.getValue("rod_cislo"));
            pocetOsob++;
        }
        if (odsudeneTable != null){
            odsudeneTable.addCell(attributes.getValue("rod_cislo"));
            pocetOsob++;
        }
        if (svedkoviaTable != null){
            svedkoviaTable.addCell(attributes.getValue("rod_cislo"));
            pocetOsob++;
        }
    }

    private void osobyCheckEnd(String qName){
        if(qName.equals("Podozrive_osoby")){
            if(podozriveTable != null && pocetOsob > 0){
                podozriveTable.setMarginLeft(50);

                Paragraph p = new Paragraph();
                p.setFont(fontBold);
                p.add(new Tab());
                p.add("Podozrive Osoby");

                document.add(p);
                document.add(podozriveTable);
            }

            podozriveTable = null;
            pocetOsob = 0;
        }else if(qName.equals("Hladane_osoby")){
            if(hladaneTable != null && pocetOsob > 0){
                hladaneTable.setMarginLeft(50);

                Paragraph p = new Paragraph();
                p.setFont(fontBold);
                p.add(new Tab());
                p.add("Hladane Osoby");

                document.add(p);
                document.add(hladaneTable);
            }

            hladaneTable = null;
            pocetOsob = 0;
        }else if(qName.equals("Obvinene_osoby")){
            if(obvineneTable != null && pocetOsob > 0){
                obvineneTable.setMarginLeft(50);

                Paragraph p = new Paragraph();
                p.setFont(fontBold);
                p.add(new Tab());
                p.add("Obvinene Osoby");

                document.add(p);
                document.add(obvineneTable);
            }

            obvineneTable = null;
            pocetOsob = 0;
        }else if(qName.equals("Odsudene_osoby")){
            if(odsudeneTable != null && pocetOsob > 0){
                odsudeneTable.setMarginLeft(50);

                Paragraph p = new Paragraph();
                p.setFont(fontBold);
                p.add(new Tab());
                p.add("Odsudene Osoby");

                document.add(p);
                document.add(odsudeneTable);
            }

            odsudeneTable = null;
            pocetOsob = 0;
        }else if(qName.equals("Svedkovia")){
            if(svedkoviaTable != null && pocetOsob > 0){
                svedkoviaTable.setMarginLeft(50);

                Paragraph p = new Paragraph();
                p.setFont(fontBold);
                p.add(new Tab());
                p.add("Svedkovia");

                document.add(p);
                document.add(svedkoviaTable);
            }

            svedkoviaTable = null;
            pocetOsob = 0;
        }

    }

    private void addMenoOsoba(char[] ch, int start, int length){
        if (podozriveTable != null){
            podozriveTable.addCell(new String(ch,start,length));
        }else if (hladaneTable != null){
            hladaneTable.addCell(new String(ch,start,length));
        }else if (obvineneTable != null){
            obvineneTable.addCell(new String(ch,start,length));
        }else if (odsudeneTable != null){
            odsudeneTable.addCell(new String(ch,start,length));
        }else if (svedkoviaTable != null){
            svedkoviaTable.addCell(new String(ch,start,length));
        }
    }


    private void pripadCheck(String qName, Attributes attributes) {
        if(qName.equals("Pripad")){
            addPripadLine(attributes.getValue("ID"));
        }
    }

    private void addPripadLine(String id){
        Paragraph p = new Paragraph();
        p.setFont(fontBold).setFontSize(20);
        p.add("Pripad č. "+id);
        p.add("\n");
        document.add(p);

        pripad = new Paragraph();
        pripad.setFont(fontNormal);
    }

    private void addTextLine(String data){
        Text text = new Text(data+"\n");
        text.setFont(fontNormal);
        pripad.add(new Tab());
        pripad.add(text);
    }

    private void writeToPdf(char[] ch, int start, int length){
        if (isTyp){
            addTextLine("Typ: "+new String(ch,start,length));
        }else if (isDatum_vykonania){
            addTextLine("Datum Vykonania: "+new String(ch,start,length));
        }else if (isZac_vysetrovania){
            addTextLine("Zaciatok Vysetrovania: "+new String(ch,start,length));
        }else if (isKoniec_vysetrovania){
            addTextLine("Koniec Vysetrovania: "+new String(ch,start,length));
        }else if (isObjasnenost){
            addTextLine("Objastnenost: "+new String(ch,start,length));
        }else if (isMiesto_vykonania){
            addTextLine("Miesto vykonania: "+new String(ch,start,length));
        }else if (isOsoba){
            addMenoOsoba(ch, start, length);
        }
    }

    private String getTextFromArray(char[] ch, int start, int lenght) {
        String s = "";
        for (int i = start; i < lenght; i++) {
            s += ch[i];
        }
        return s;
    }


    private void setElementFlag(String qName) {
        switch (qName){
            case "Typ":
                isTyp = true;
                break;
            case "Datum_vykonania":
                isDatum_vykonania = true;
                break;
            case "Zac_vysetrovania":
                isZac_vysetrovania = true;
                break;
            case "Koniec_vysetrovania":
                isKoniec_vysetrovania = true;
                break;
            case "Objasnenost":
                isObjasnenost = true;
                break;
            case "Miesto_vykonania":
                isMiesto_vykonania = true;
                break;
            case "Meno":
                isOsoba = true;
                break;
            case "Dovod":
                isOsoba = true;
                break;
            case "Dat_od":
                isOsoba = true;
                break;
            case "Dat_do":
                isOsoba = true;
                break;
            case "Skoda":
                isOsoba = true;
                break;
            case "Pokuta":
                isOsoba = true;
                break;
            case "Vazanie":
                isOsoba = true;
                break;
            case "Od":
                isOsoba = true;
                break;
            case "Dlzka":
                isOsoba = true;
                break;



        }
    }

    private void resetElements(String qName){
        isTyp = false;
        isDatum_vykonania = false;
        isZac_vysetrovania = false;
        isKoniec_vysetrovania = false;
        isObjasnenost = false;
        isMiesto_vykonania = false;
        isOsoba = false;
    }

}
