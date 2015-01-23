package mesCommandes;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
class Util {
         
static public   String rechercheCookies(Cookie[] lescookies, String nom)  {
         String reponse =null;
         
               //  ********************************************************************************************        
               //  recherche si dans le tableau de cookies il en existe un avec le nom donn�.
               //       si oui  la valeur de ce cookie est donn�e en r�sultat (mot de passe)
               //  Cette m�thode sera appel�e par d�autres classes aussi elle est � public � 
               //  et � static � pour pouvoir  l�appeler directement par la classe  "Util.rechercheCookies(..)"
               //  ********************************************************************************************
         
         if(lescookies!=null&&lescookies.length>0){
        	 int count = lescookies.length;
        	 for(int i = 0; i<count;i++){
        		 if(nom.equals(lescookies[i].getName())){
        			 reponse=lescookies[i].getValue();
        		 }
        	 }
         }
	
         return reponse;
      }


static boolean identique (String recu, String cookie) {
    return ( (cookie != null) && (recu.equals(cookie) ));

}
}