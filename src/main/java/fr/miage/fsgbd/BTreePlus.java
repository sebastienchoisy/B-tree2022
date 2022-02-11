package fr.miage.fsgbd;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.*;


/**
 * @author Galli Gregory, Mopolo Moke Gabriel
 * @param <Type>
 */
public class BTreePlus<Type> implements java.io.Serializable {
    private Noeud<Type> racine;
    private ArrayList<Type> listeValeurs = new ArrayList<>();
    private ReportWriter writer = new ReportWriter();

    public BTreePlus(int u, Executable e) {
        racine = new Noeud<Type>(u, e, null);
    }

    public void afficheArbre() {
        racine.afficheNoeud(true, 0);
    }

    public Noeud<Type> getRacine(){
        return this.racine;
    }

    /**
     * Méthode récursive permettant de récupérer tous les noeuds
     *
     * @return DefaultMutableTreeNode
     */
    public DefaultMutableTreeNode bArbreToJTree() {
        return bArbreToJTree(racine);
    }

    private DefaultMutableTreeNode bArbreToJTree(Noeud<Type> root) {
        StringBuilder txt = new StringBuilder();
        for (Type key : root.keys)
            txt.append(key.toString()).append(" ");

        DefaultMutableTreeNode racine2 = new DefaultMutableTreeNode(txt.toString(), true);
        for (Noeud<Type> fil : root.fils)
            racine2.add(bArbreToJTree(fil));

        return racine2;
    }


    public boolean addValeur(Type valeur) {
        System.out.println("Ajout de la valeur : " + valeur.toString());
        if (racine.contient(valeur) == null) {
            Noeud<Type> newRacine = racine.addValeur(valeur);
            if (racine != newRacine)
                racine = newRacine;
            return true;
        }
        return false;
    }

    public boolean addValeur(Type valeur, Integer ligne) {
        System.out.println("Ajout de la valeur : " + valeur.toString()+ " presente a la ligne "+ligne);
        if (racine.contient(valeur) == null) {
            Noeud<Type> newRacine = racine.addValeur(valeur,ligne);
            this.listeValeurs.add(valeur);
            if (racine != newRacine)
                racine = newRacine;
            return true;
        }
        return false;
    }


    public void removeValeur(Type valeur) {
        System.out.println("Retrait de la valeur : " + valeur.toString());
        if (racine.contient(valeur) != null) {
            Noeud<Type> newRacine = racine.removeValeur(valeur, false);
            if (racine != newRacine)
                racine = newRacine;
        }
    }

    // On cree une liste de valeurs à chercher de façon aleatoire en se basant sur les valeurs inserees dans l'arbre

    public ArrayList<Type> creationListeValeursRecherchees(){
        ArrayList<Type> CopieListeValeur = new ArrayList<>(this.listeValeurs);
        ArrayList<Type> listePioche = new ArrayList<>();
        Collections.shuffle(CopieListeValeur);
        for(int i = 0; i < 100; i ++){
            listePioche.add(CopieListeValeur.get(i));
        }
        return listePioche;
    }

    // Methode de recherche indexee

    public Integer rechercheIndexee(Integer value){
        Noeud<Type> racineParentDeLaValeur = racine.choixNoeudAjout((Type) value);
        Integer ligne = racineParentDeLaValeur.keysPointers.get(value);
        return ligne;
    }

    // Methode de recherche sequentielle

    public Integer rechercheSequentielleDansArbre(Integer value) {
        Noeud<Type> noeud = racine;
        while (noeud.fils.size() > 0) {
            noeud = noeud.fils.get(0);
        }
        Integer ligne = 0;
        while (ligne == 0 && noeud.getNext() != null) {
            if(noeud.keysPointers.get(value)!= null){
                ligne = noeud.keysPointers.get(value);
            }
            noeud = noeud.getNext();
        }
        return ligne;
    }

    public Integer rechercheSequentielleDansFichier(Integer value){
        BufferedReader reader;
        Integer socialNumber;
        Integer valueLine = 0;
        try {
            reader = new BufferedReader(new FileReader("data.txt"));
            String line = reader.readLine();
            Integer lineNumber = 0;
            while (valueLine == 0 && line != null) {
                lineNumber++;
                socialNumber = Integer.parseInt(line.substring(0, line.indexOf(",")));
                line = reader.readLine();
                if(socialNumber == value){
                    valueLine = lineNumber;
                }
            }
            reader.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return valueLine;
    }

    // On fait les deux types de recherches dans la meme methode et on publie les resultats/statistiques sur la console et dans
    // le fichier RapportDeRecherche.txt à la racine

    public void recherche() {
        if (this.listeValeurs.size() == 0) {
            System.out.println("Veuillez charger l'arbre depuis le fichier de donnees");
        } else {
            ArrayList<Type> liste = this.creationListeValeursRecherchees();
            Date date = new Date();
            DateFormat format = DateFormat.getInstance();
            long begin, end, tempsIndexe, tempsSequentiel, tempsSequentielFichier;
            long tempsMoyenIndexe = 0;
            long tempsMaxIndexe = 0;
            long tempsMinIndexe = 99999999;
            long tempsMoyenSequentiel = 0;
            long tempsMaxSequentiel = 0;
            long tempsMinSequentiel = 99999999;
            long tempsMoyenSequentielFichier = 0;
            long tempsMaxSequentielFichier = 0;
            long tempsMinSequentielFichier = 99999999;

            for (Type element : liste) {

                //Recherche Indexee
                begin = System.nanoTime();
                this.rechercheIndexee((Integer) element);
                end = System.nanoTime();
                tempsIndexe = end - begin;
                tempsMoyenIndexe += tempsIndexe;
                if (tempsIndexe < tempsMinIndexe)
                    tempsMinIndexe = tempsIndexe;
                if (tempsIndexe > tempsMaxIndexe)
                    tempsMaxIndexe = tempsIndexe;

                //Recherche Sequentielle dans l'arbre
                begin = System.nanoTime();
                this.rechercheSequentielleDansArbre((Integer) element);
                end = System.nanoTime();
                tempsSequentiel = end - begin;
                tempsMoyenSequentiel += tempsSequentiel;
                if (tempsSequentiel < tempsMinSequentiel)
                    tempsMinSequentiel = tempsSequentiel;
                if (tempsSequentiel > tempsMaxSequentiel)
                    tempsMaxSequentiel = tempsSequentiel;

                //Recherche Sequentielle dans le fichier
                begin = System.nanoTime();
                this.rechercheSequentielleDansFichier((Integer) element);
                end = System.nanoTime();
                tempsSequentielFichier = end - begin;
                tempsMoyenSequentielFichier += tempsSequentielFichier;
                if (tempsSequentielFichier < tempsMinSequentielFichier)
                    tempsMinSequentielFichier = tempsSequentielFichier;
                if (tempsSequentielFichier > tempsMaxSequentielFichier)
                    tempsMaxSequentielFichier = tempsSequentielFichier;

            }
            tempsMoyenIndexe = tempsMoyenIndexe / 100;
            tempsMoyenSequentiel = tempsMoyenSequentiel / 100;
            tempsMoyenSequentielFichier = tempsMoyenSequentielFichier / 100;

            // Display dans la console

            System.out.println("\n"+format.format(date.getTime()) + " Statistiques\n");
            System.out.println("Recherches indexees et sequentielles realisees sur 100 valeurs choisies aleatoirement\n");
            System.out.println("Temps moyen pour une recherche indexee : " + tempsMoyenIndexe + " nanosecondes.");
            System.out.println("Temps maximum pour une recherche indexee : " + tempsMaxIndexe + " nanosecondes.");
            System.out.println("Temps minimum pour une recherche indexee : " + tempsMinIndexe + " nanosecondes.\n");
            System.out.println("Temps moyen pour une recherche sequentielle dans l'arbre : " + tempsMoyenSequentiel + " nanosecondes.");
            System.out.println("Temps maximum pour une recherche sequentielle dans l'arbre : " + tempsMaxSequentiel + " nanosecondes.");
            System.out.println("Temps minimum pour une recherche sequentielle dans l'arbre : " + tempsMinSequentiel + " nanosecondes.\n");
            System.out.println("Temps moyen pour une recherche sequentielle dans le fichier : " + tempsMoyenSequentielFichier + " nanosecondes.");
            System.out.println("Temps maximum pour une recherche sequentielle dans le fichier : " + tempsMaxSequentielFichier + " nanosecondes.");
            System.out.println("Temps minimum pour une recherche sequentielle dans le fichier : " + tempsMinSequentielFichier + " nanosecondes.");

            // On ecrit le resultat des recherches dans le fichier de rapport

            writer.writeInReportFile(format.format(date.getTime()) + " Statistiques");
            writer.writeInReportFile("\n");
            writer.writeInReportFile("Recherches indexees et sequentielles (dans l'arbre et dans le fichier) realisees sur 100 valeurs choisies aleatoirement");
            writer.writeInReportFile("\n");
            writer.writeInReportFile("Temps moyen pour une recherche indexee : " + tempsMoyenIndexe + " nanosecondes.");
            writer.writeInReportFile("Temps maximum pour une recherche indexee : " + tempsMaxIndexe + " nanosecondes.");
            writer.writeInReportFile("Temps minimum pour une recherche indexee : " + tempsMinIndexe + " nanosecondes.\n");
            writer.writeInReportFile("Temps moyen pour une recherche sequentielle dans l'arbre : " + tempsMoyenSequentiel + " nanosecondes.");
            writer.writeInReportFile("Temps maximum pour une recherche sequentielle dans l'arbre : " + tempsMaxSequentiel + " nanosecondes.");
            writer.writeInReportFile("Temps minimum pour une recherche sequentielle dans l'arbre : " + tempsMinSequentiel + " nanosecondes.\n");
            writer.writeInReportFile("Temps moyen pour une recherche sequentielle dans le fichier : " + tempsMoyenSequentielFichier + " nanosecondes.");
            writer.writeInReportFile("Temps maximum pour une recherche sequentielle dans le fichier : " + tempsMaxSequentielFichier + " nanosecondes.");
            writer.writeInReportFile("Temps minimum pour une recherche sequentielle dans le fichier : " + tempsMinSequentielFichier + " nanosecondes.\n");
            writer.writeInReportFile("\n");
        }
    }
}
