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
		// initialisation des différents paramètres possibles
		// ********************************************************************************************

		inscrire = request.getParameter("inscrire");
		erreur = request.getParameter("erreur");
		nomRecu = request.getParameter("nom");
		motPasseRecu = request.getParameter("motDePasse");

		// ********************************************************************************************
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// ********************************************************************************************
		// cas 1 pas de paramètre inscrire
		// Si le paramètre "erreur" est présent, informez que les informations
		// transmises ne sont pas acceptables.
		// demandez des informations (nom, mot de passe, email, téléphone) par
		// un formulaire
		// et rappel de cette même servlet avec ces informations en paramètre
		// le paramètre inscrire doit aussi être envoyé avec comme valeur
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
			out.print("Téléphone " + "<input type=text length=20 name=tel><br>");
			out.println("<input type=submit name=inscrire></form>");
			out.println("</body></html>");
		}

		// ********************************************************************************************
		// cas 2 paramètre inscrire présent
		// si les paramètres "nom" et "motdepasse" sont présent et font plus de
		// 4 caractères alors :
		// création du cookie du nom "nomRecu" et de valeur "motPasseRecu".
		// et appel de la servlet ConnexionClient avec le paramètre "inscrit"
		// Sinon rappel de cette même servlet avec le paramètre erreur
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
