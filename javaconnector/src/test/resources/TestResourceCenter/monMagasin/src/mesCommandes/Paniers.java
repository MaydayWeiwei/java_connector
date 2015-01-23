package mesCommandes;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.sql.*;

public class Paniers {
	public static TreeMap<String, ArrayList<String>> lescommandes = new TreeMap<String, ArrayList<String>>();

	// String

	static public void afficherIterationDisques(Iterator<String> iter,
			PrintWriter out, String repertoire) {
		Disque leDisque;
		out.println("<table border=1>");
		while (iter.hasNext()) {
			leDisque = Stock.trouveDisque((String) iter.next());
			out.println("<tr> <td>" + leDisque.getTitre() + "  ,  "
					+ leDisque.getNom() + " ,  " + leDisque.getPrix()
					+ " Euros  ,  Année :" + leDisque.getAnnee() + "</td>");
			out.println("<td> <IMG SRC= '" + repertoire + "/images/"
					+ leDisque.getImage() + "'  BORDER=0> </A><br> </td> ");
		}
		out.println("</tr> </table>");
	}

	static public void afficherVenteDisques(Iterator<String> iter,
			PrintWriter out, String repertoire) {
		Disque leDisque;
		out.println("<table border=1>");
		while (iter.hasNext()) {
			leDisque = Stock.trouveDisque((String) iter.next());
			out.println("<tr> <td>" + leDisque.getTitre() + "  ,  "
					+ leDisque.getNom() + " ,  " + leDisque.getPrix()
					+ " Euros  ,  Année :" + leDisque.getAnnee() + "</td>");

			out.println("<td> <IMG SRC= '" + repertoire + "/images/"
					+ leDisque.getImage() + "'  BORDER=0> </A><br> </td> ");
			out.println("<td><A HREF='commande?code=" + leDisque.getReference()
					+ "&ordre=ajouter'>");
			out.println("<IMG SRC='" + repertoire
					+ "/images/panier.gif\' BORDER=0></A><br> </td> </tr>");
		}
		out.println("</tr> </table>");
	}

	static public ArrayList<String> afficherTuplesDisques(ResultSet rs,
			PrintWriter out, String repertoire) {
		ArrayList<String> disques = new ArrayList<String>();
		Disque leDisque;

		try {
			String article;
			out.println("<table border=1>");
			while (rs.next()) {
				// out.println( rs.getString("article") + "<br>" );
				article = rs.getString("nomarticle");
				leDisque = Stock.trouveDisque(article);
				disques.add(article);
				out.println("<tr> <td>" + leDisque.getTitre() + "  ,  "
						+ leDisque.getNom() + " ,  " + leDisque.getPrix()
						+ " Euros  ,  Année :" + leDisque.getAnnee() + "</td>");
				out.println("<td> <IMG SRC= '" + repertoire + "/images/"
						+ leDisque.getImage() + "'  BORDER=0> </A><br> </td> ");
			}
			out.println("</tr></table>");
		} catch (Exception E) {
			out.println("erreur base");
			System.out.println(" - probeme ajoute " + E.getClass().getName());
			E.printStackTrace();
		}
		return disques;
	}

	static public int afficherFactureDisques(Iterator<String> iter,
			PrintWriter out, String repertoire) {
		Disque leDisque;
		int total = 0;
		int prix;
		out.println("<table border=1>");
		out.println("<tr><th></th><th>Commande</th><th>Prix</th></tr>");
		while (iter.hasNext()) {
			leDisque = Stock.trouveDisque((String) iter.next());
			out.println("<tr><td> <IMG SRC= '" + repertoire + "/images/"
					+ leDisque.getImage() + "'  BORDER=0> </A><br> </td> ");
			out.println(" <td>" + leDisque.getTitre() + "  ,  "
					+ leDisque.getNom() + " ,  Année :" + leDisque.getAnnee() + "</td>");
			prix = leDisque.getPrix();
			total += prix;
			out.println("<td>" + prix +"</td>");
		}
		out.println("</tr> </table>");
		return total;
	}

}
