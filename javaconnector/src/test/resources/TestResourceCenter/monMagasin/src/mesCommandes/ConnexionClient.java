package mesCommandes;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ConnexionClient extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nomRecu, motPasseRecu;
		String connexion, inscrit, erreur;
		String nomCookie, motPasseCookie;
		Cookie[] cookies;

		// ********************************************************************************************
		// initialisation des différents paramètres possibles
		// ********************************************************************************************

		connexion = request.getParameter("connexion");
		erreur = request.getParameter("erreur");
		inscrit = request.getParameter("inscrit");
		nomCookie = request.getParameter("nomCookie");
		nomRecu = request.getParameter("nom");
		motPasseRecu = request.getParameter("motPasse");

		cookies = request.getCookies();

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// ********************************************************************************************
		// cas 1 pas de paramètre connexion
		// demande nom et mot de passe par un formulaire et un bouton "submit"
		// de nom connexion
		// si parametre "inscrit" present : message diasant que l'inscription a
		// bien été prise en compte
		// et mettre la valeur du nom dans le formulaire
		// si parametre "erreur" present : message indiquant le type d'errreur.
		// ********************************************************************************************

		if (connexion == null) {
			out.println("<html><body>");
			out.println("<h3> Connexion </h3>");
			if (erreur != null) {
				out.print("<h4>" + erreur + "</h4><br>");
			}
			out.print("<form action=\"/monMagasin/servlet/seconnecter\" method=GET>");
			if (inscrit == null) {
				out.print("Nom " + " <input type=text length=20 name=nom><br>");
			} else {
				out.print("Nom "
						+ " <input type=text length=20 name=nom value="
						+ nomCookie + "><br>");
			}
			out.print("Mot de passe "
					+ "<input type=text length=20 name=motPasse><br>");
			out.println("<input type=submit name=connexion></form>");
			out.println("</body></html>");
		}

		// ********************************************************************************************
		// cas 2 paramètre connexion présent (suite du cas 1)
		// la connexion est demandée on vérifie que le nom et le mot de passe
		// sont présents dans un cookie
		// utilisez la méthode « rechercheCookies » à compléter dans la classe
		// Util.java
		// si oui appel de la servlet « EntreeMagasinDisque » avec le paramètre
		// nom
		// si non rappel de la servlet courante « ConnectionClient » avec le
		// paramètre "erreur" indiquant le type d'erreur
		// ********************************************************************************************

		else {
			motPasseCookie = Util.rechercheCookies(cookies, nomRecu);
			if(motPasseRecu.equals(motPasseCookie)){
				response.sendRedirect("/monMagasin/servlet/entrer?nom="+nomRecu);
			}
			else{
				response.sendRedirect("/monMagasin/servlet/seconnecter?erreur=nom ou mot de passe incorrect");
			}
		}

	} // doGet(HttpServletRequest

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
