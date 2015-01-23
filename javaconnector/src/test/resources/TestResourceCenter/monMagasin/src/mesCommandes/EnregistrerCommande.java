package mesCommandes;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class EnregistrerCommande extends HttpServlet {
	Connection connexion = null;
	Statement stmt = null;
	PreparedStatement pstmt = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String nom = null;
		ArrayList<String> lesdisques = null;
		ArrayList<String> disques = null;

		// ***********************************************************
		// initialisez nom et le panier du client : variable « nom » et
		// lesdisques »
		//
		// **********************************************************

		HttpSession session = request.getSession();
		nom = (String) session.getAttribute("nom");
		lesdisques = Paniers.lescommandes.get(nom);

		OuvreBase();
		AjouteNomBase(nom);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>  votre commande </title>");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' >");
		out.println("</head>");

		out.println("<body bgcolor=\"white\">");
		out.println("<a HREF=achat> Vous pouvez commandez un autre disque </a><br> ");
		out.println("<A HREF=facturer> Fin de la commande et demande de la facture  de   "
				+ nom.toUpperCase() + " </A><br> ");
		out.println("<h3>" + " Disques rajoutés à la commande de  "
				+ nom.toUpperCase() + "</h3>");

		// ************************************************************
		// afficher le contenu du panier
		// ************************************************************

		AjouteCommandeBase(nom, lesdisques);
		out.println("<h3>" + "et voici " + nom.toUpperCase()
				+ "  Voici  l'ensemble de tes commandes  enregistrées "
				+ "</h3>");
		disques = MontreCommandeBase(nom, out, request.getContextPath());
		out.println("</body>");
		out.println("</html>");
		fermeBase();
		session.setAttribute("commande", disques);
	}

	protected void OuvreBase() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			connexion = DriverManager.getConnection(
					"jdbc:mysql://localhost/magasin", "root", "");
			connexion.setAutoCommit(true);
			stmt = connexion.createStatement();
		} catch (Exception E) {
			log(" -------- problème  " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void fermeBase() {
		try {
			stmt.close();
			connexion.close();
		} catch (Exception E) {
			log(" -------- problème  " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void AjouteNomBase(String nom) {
		try {
			ResultSet rset = null;
			pstmt = connexion
					.prepareStatement("select id from client where nom=?");
			pstmt.setString(1, nom);
			rset = pstmt.executeQuery();
			if (!rset.next())
				stmt.executeUpdate("INSERT INTO client (nom)VALUES  ('" + nom
						+ "' )");
		}

		catch (Exception E) {
			log(" - probeme  " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected void AjouteCommandeBase(String nom, ArrayList<String> lesdisques) {
		ResultSet rset = null;
		int cle = 0;
		try {

			// *******************************************************
			// ajoutez le contenu du panier dans la base de données. « table
			// commande »
			// *******************************************************
			pstmt = connexion
					.prepareStatement("select id from client where nom=?");
			pstmt.setString(1, nom);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				cle=rset.getInt(1);
				for (String disque : lesdisques) {
					stmt.executeUpdate("INSERT INTO commande (nomarticle, client)VALUES  ('"
							+ disque + "','" + cle + "')");
				}
			}

		}

		catch (Exception E) {
			log(" - problème  ajoute " + E.getClass().getName());
			E.printStackTrace();
		}
	}

	protected ArrayList<String> MontreCommandeBase(String nom, PrintWriter out,
			String repertoire) {
		ResultSet rset = null;
		ArrayList<String> disques = null;
		try {

			// *********************************************************
			// affichez les disques que client a commandé dans la base de
			// données. « table commande »
			// vous pouvez utiliser "afficherTuplesDisques" avec une instance de
			// "Resulset" de la table commande
			// *********************************************************
			pstmt = connexion
					.prepareStatement("select comd.* from commande comd, client c where c.nom=? and comd.client=c.id");
			pstmt.setString(1, nom);
			rset = pstmt.executeQuery();
			disques = Paniers.afficherTuplesDisques(rset, out, repertoire);

		} catch (Exception E) {
			out.println("erreur base");
			log(" - probeme ajoute " + E.getClass().getName());
			E.printStackTrace();
		}
		
		return disques;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
