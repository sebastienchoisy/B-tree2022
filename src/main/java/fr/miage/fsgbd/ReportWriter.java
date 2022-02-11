package fr.miage.fsgbd;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportWriter {

    public void writeInReportFile(String string){
        try {
            File myObj = new File("RapportDeRecherche.txt");
            if (myObj.createNewFile()) {
                System.out.println("Fichier cree: " + myObj.getName());
            }
            try {
                FileWriter myWriter = new FileWriter("RapportDeRecherche.txt",true);
                myWriter.write(string+"\n");
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
