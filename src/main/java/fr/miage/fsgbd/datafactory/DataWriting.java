package fr.miage.fsgbd.datafactory;

import java.io.*;

public class DataWriting {

    public static void main(String[] args) throws IOException {
        DataFactory factory = new DataFactory();
        try (FileWriter f = new FileWriter("data.txt", true);
             BufferedWriter b = new BufferedWriter(f);
             PrintWriter p = new PrintWriter(b)
             ) {
            for(int i = 0; i < 10000; i++){
                p.println(factory.generateSocialSecurityNumber()+","+
                        factory.generateName()+","+factory.generateSurname()+","+
                        factory.generatePhoneNumber()+","+factory.generateAddress());
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}

