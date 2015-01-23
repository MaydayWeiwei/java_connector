package mesCommandes;
public class Disque {
	String nom;
	String titre;
	int prix;
	String reference;
	int annee;
	String image;
	
public	Disque( String titre, String nom, int prix, String reference, int annee, String image ) {
		this.nom= nom;
		this.titre = titre;
		this.prix = prix;
		this.reference = reference;
		this.annee  = annee;
		this.image  = image;
}

public	String toString(){
		return " disque : " + titre  + ",  Chanteur  "  +  titre  + ", année   "  + annee + ", prix  "   + prix ;
	}

public	String image(){
	return image;
}
	
public	String getNom(){
	return nom;
}

public	String getTitre(){
	return titre;
}

public	String getReference(){
	return reference;
}

public	String getImage(){
	return image;
}
public	int getPrix(){
	return prix;
}
public	int getAnnee(){
	return annee;
}

}
