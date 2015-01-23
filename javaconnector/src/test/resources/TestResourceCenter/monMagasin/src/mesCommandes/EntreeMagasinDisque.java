package mesCommandes;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
public class EntreeMagasinDisque extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	   {
			  
	     String nomRecu;
	     nomRecu = request.getParameter("nom");
		  
	//  ********************************************************************************************        
	//  Créez deux variables de session : « nom » qui a pour valeur le nom de l’utilisateur  
	//  et « LeStock »  qui a pour valeur une instance de la classe Stock, 
	// et appeler la servlet  AfficherLesDisques.java
	//  ********************************************************************************************
	     Stock leStock = new Stock();
	     HttpSession session = request.getSession();
	     session.setAttribute("nom", nomRecu);
	     session.setAttribute("leStock", leStock);
	     
	     response.sendRedirect("/monMagasin/servlet/filter/achat");
	     
		}
   
	public void doPost(HttpServletRequest request,  HttpServletResponse response) throws IOException, ServletException
	      { 
	         doGet(request, response);
	      }
}
