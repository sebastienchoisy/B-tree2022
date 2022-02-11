import fr.miage.fsgbd.BTreePlus;
import fr.miage.fsgbd.Noeud;
import fr.miage.fsgbd.TestInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReferencementTestNoeuds {

    @Test
    public void testReferenceNoeuds(){
        TestInteger testInt = new TestInteger();
        BTreePlus<Integer> btree = new BTreePlus<>(4,testInt);
        for(int i = 0;i < 10000;i++){
            btree.addValeur(i,i+1);
        }
        int keys;
        Noeud<Integer> noeud = btree.getRacine();
        while(noeud.fils.size()>0){
            noeud = noeud.fils.get(0);
        }
        keys = noeud.keys.size();
        while(noeud.getNext() != null){
            noeud = noeud.getNext();
            keys = keys + noeud.keys.size();
        }
        // On vérifie qu'on peut bien parcourir les 10000 clefs en utilisant getNext()
        assertEquals(10000,keys);
    }

}

