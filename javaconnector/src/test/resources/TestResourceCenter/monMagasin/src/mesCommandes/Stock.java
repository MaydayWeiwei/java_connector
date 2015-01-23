package mesCommandes;
import java.io.*;
import java.util.ArrayList;

public class Stock {
	 ArrayList<String> listeDisques;
	
	public Stock () {
		listeDisques = new  ArrayList<String>();
	       for(int i = 0; i< leStock.length; i++) {
	    	   listeDisques.add(leStock[i][3]);
	         }
	  }
	
	
    static String [] [] leStock = {
        {"Bretonne",  "Nolwenn Le Roy", "15", "disque00202010", "2010","nolwenBretonne.jpg"},
        {"Les Retouvailles" , "Yann Tiercen", "19", "disque002342008", "2008", "YannRetouvailles.jpg"},
        {"Chansons Ordinaires" , "Christophe Miossec", "25", "disque006542008", "2008", "ChristopheOrdinaires.jpg"},
        {"D'Iroise et d'ailleurs" , "Les Marins d'Iroise", "35", "disque006212011", "2011","MarinsIroise.jpg" },
        {"La Blanche Hermine" , "Gilles Servat", "25", "disque006211999", "1999", "GillesHermine.jpg" },
        {"Concert Matmatah" , "Matmatah", "15", "disque002311999", "1999","ConcertMatmatah.jpg" },
        {"La Tri Yann ou l'ignorance" , "Tri Yann", "20", "disque006212008", "2008", "TriYannIgnorance.jpg" },
        {"Le plus gros est fait" , "Les Goristes", "25", "disque002322011", "2011", "GrosGoristes.jpg" },
		  {"Chant de Mer" , "Nolwenn Le Roy", "25", "disque002322012", "2014", "nowennChantdeMer.jpg" }
     };

     public static  Disque trouveDisque (String reference){
    	 for(int i = 0; i< leStock.length; i++) {
    	     if (leStock[i][3].equals(reference)) 
    	    		  return new Disque(leStock[i][0], leStock[i][1], Integer.parseInt(leStock[i][2]), leStock[i][3], Integer.parseInt(leStock[i][4]),leStock[i][5]);
    	 }
    	 return null;
    	 }
     
     
     public static Disque donneDisque (int numero){
    	    return new Disque(leStock[numero][0],leStock[numero][1],Integer.parseInt(leStock[numero][2]),leStock[numero][3],Integer.parseInt(leStock[numero][4]),leStock[numero][5]);
    	 }


     public ArrayList<String>  getListeDisques() {
	      return  listeDisques;
}
}

