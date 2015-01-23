package mesCommandes;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class CommanderUnDisque extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String nom = null;
		HttpSession session = request.getSession();
		nom = (String) session.getAttribute("nom");

		// ***********************************************************
		// Si la personne dont le nom est dans la session, ne poss�de pas de
		// panier ,
		// son panier est cr�� dans l�ensemble des paniers,
		// "Panier.lescommandes"
		// C�est une nouvelle ArrayList qui est rajout� dans la TreeMap
		// "lescommandes" de la classe � Paniers �,
		// avec comme cl� le nom.
		//
		// ************************************************************

		if (Paniers.lescommandes.get(nom) == null) {
			Paniers.lescommandes.put(nom, new ArrayList<String>());
		}

		ArrayList<String> listeDisque = Paniers.lescommandes.get(nom);

		// ************************************************************
		// Si le param�tre � ordre � est pr�sent est a comme valeur � ajouter �,
		// la r�f�rence du disque pass�e en param�tre est rajout�e dans le
		// panier (ArrayList<String>).
		//
		// ***********************************************************
		if("ajouter".equals(request.getParameter("ordre"))){
			listeDisque.add(request.getParameter("code"));
		}
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>  votre commande </title>");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' >");
		out.println("</head>");
		out.println("<body bgcolor=\"white\">");
		out.println("<h3>" + "Bonjour  " + nom + "  voici  votre commande"
				+ "</h3>");

		// *********************************************************
		// affichage du contenu du panier par la m�thode
		// afficherIterationDisques de � Paniers � avec trois param�tres :
		// - un Iterateur sur les disques disponibles dans le magasin
		// - le � PrintWriter � pour pouvoir rajouter ces disques dans la
		// r�ponse HTML,
		// - le r�pertoire courant de votre application
		// "request.getContextPath()"
		// *********************************************************
		
		Paniers.afficherIterationDisques(listeDisque.iterator(), out, request.getContextPath());

		out.println(" </table>");
		out.println("<A HREF=achat> Vous pouvez commandez un autre disque </A><br> ");
		out.println("<A HREF=enregistre> Vous pouvez enregistrer votre commande </A><br> ");
		out.println("<A HREF=facturer> Fin de la commande et demande de la facture </A><br> ");
		out.println("</body>");
		out.println("</html>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
