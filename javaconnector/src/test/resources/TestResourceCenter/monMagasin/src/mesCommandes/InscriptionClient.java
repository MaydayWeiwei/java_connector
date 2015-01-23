package mesCommandes;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class InscriptionClient extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String nomRecu, motPasseRecu;
		String inscrire, erreur;

		// ********************************************************************************************
		// initialisation des diff�rents param�tres possibles
		// ********************************************************************************************

		inscrire = request.getParameter("inscrire");
		erreur = request.getParameter("erreur");
		nomRecu = request.getParameter("nom");
		motPasseRecu = request.getParameter("motDePasse");

		// ********************************************************************************************
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// ********************************************************************************************
		// cas 1 pas de param�tre inscrire
		// Si le param�tre "erreur" est pr�sent, informez que les informations
		// transmises ne sont pas acceptables.
		// demandez des informations (nom, mot de passe, email, t�l�phone) par
		// un formulaire
		// et rappel de cette m�me servlet avec ces informations en param�tre
		// le param�tre inscrire doit aussi �tre envoy� avec comme valeur
		// inscrire (bouton submit)
		// ********************************************************************************************
		if (inscrire == null) {
			out.println("<html><body>");
			out.println("<h3> Inscription </h3>");
			if (erreur != null) {
				out.print("<h4>" + erreur + "</h4><br>");
			}
			out.print("<form action=\" /monMagasin/servlet/sinscrire\" method=GET>");
			out.print("Nom " + " <input type=text length=20 name=nom><br>");
			out.print("Mot de passe "
					+ "<input type=text length=20 name=motDePasse><br>");
			out.print("Email " + "<input type=text length=50 name=email><br>");
			out.print("T�l�phone " + "<input type=text length=20 name=tel><br>");
			out.println("<input type=submit name=inscrire></form>");
			out.println("</body></html>");
		}

		// ********************************************************************************************
		// cas 2 param�tre inscrire pr�sent
		// si les param�tres "nom" et "motdepasse" sont pr�sent et font plus de
		// 4 caract�res alors :
		// cr�ation du cookie du nom "nomRecu" et de valeur "motPasseRecu".
		// et appel de la servlet ConnexionClient avec le param�tre "inscrit"
		// Sinon rappel de cette m�me servlet avec le param�tre erreur
		// ********************************************************************************************

		else {
			if ((nomRecu != null) && (nomRecu.length() > 4)
					&& (motPasseRecu != null) && (motPasseRecu.length() > 4)) {
				Cookie cookie = new Cookie(nomRecu, motPasseRecu);
				response.addCookie(cookie);
				response.sendRedirect("/monMagasin/servlet/seconnecter?inscrit="+inscrire+"&nomCookie="+nomRecu);
			}
			else{
				response.sendRedirect("/monMagasin/servlet/sinscrire?erreur=Information invalide");
			}
		}

	} // doGet(HttpServletRequest

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
