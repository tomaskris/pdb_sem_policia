package main.java.helper;

import main.java.Connector;
import main.java.DbAccess.DB_vypoved;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;

public class GeneratorVypoved {

    public static void main(String[] args) throws FileNotFoundException {
        Random rnd = new Random();

        File fotka1 = new File("blob_files_police/fotka1.jpg");
        File fotka2 = new File("blob_files_police/fotka2.jpg");
        File fotka3 = new File("blob_files_police/fotka3.jpg");
        File text1 = new File("blob_files_police/text1.txt");
        File text2 = new File("blob_files_police/text2.txt");
        File text3 = new File("blob_files_police/text3.txt");
        File video1 = new File("blob_files_police/video1.mp4");
        File video2 = new File("blob_files_police/video2.mp4");
        File video3 = new File("blob_files_police/video3.mp4");

        Vypoved v1 = new Vypoved("F",".jpg",fotka1);
        Vypoved v2 = new Vypoved("F",".jpg",fotka2);
        Vypoved v3 = new Vypoved("F",".jpg",fotka3);
        Vypoved v4 = new Vypoved("T",".txt",text1);
        Vypoved v5 = new Vypoved("T",".txt",text2);
        Vypoved v6 = new Vypoved("T",".txt",text3);
        Vypoved v7 = new Vypoved("V",".mp4",video1);
        Vypoved v8 = new Vypoved("V",".mp4",video2);
        Vypoved v9 = new Vypoved("V",".mp4",video3);

        Vypoved[] vypovede = new Vypoved[]{v1,v2,v3,v4,v5,v6,v7,v8,v9};

        Random kolkoRND = new Random();
        Random vypovedRND = new Random();



        for (int i = 0; i < 500; i++) {
            BigDecimal id = new BigDecimal(rnd.nextInt(9359)+1);
            int kolkoVypovedy = kolkoRND.nextInt(2) + 1;
            for (int j = 0; j < kolkoVypovedy; j++) {
                Vypoved vypoved = vypovede[vypovedRND.nextInt(9)];
                insert(id, vypoved.typ_vypovede, vypoved.typ_suboru, vypoved.getZaznam());
            }
            System.out.println(i);

        }


    }

    public static void insert(BigDecimal id, String typ,String pripona, InputStream in) {
        try (Connection connection = Connector.getConnection()) {
            CallableStatement stmnt = connection.prepareCall("BEGIN proc_vytvor_vypoved(?, ?, ?, ?); END;");
            stmnt.setBigDecimal(1, id);
            stmnt.setString(2, typ);
            stmnt.setString(3, pripona);
            stmnt.setBinaryStream(4, in);
            stmnt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static class Vypoved {
        String typ_vypovede;
        String typ_suboru;
        File zaznam;

        public Vypoved(String typ_vypovede, String typ_suboru, File zaznam) {
            this.typ_vypovede = typ_vypovede;
            this.typ_suboru = typ_suboru;
            this.zaznam = zaznam;
        }

        public String getTyp_vypovede() {
            return typ_vypovede;
        }

        public String getTyp_suboru() {
            return typ_suboru;
        }

        public InputStream getZaznam() throws FileNotFoundException {
            return new FileInputStream(zaznam);
        }
    }
}
