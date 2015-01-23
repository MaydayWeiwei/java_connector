package mesCommandes;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.http.*;

public class FiltreSortie implements Filter {
	private FilterConfig filterConfig = null;

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String nom = null;
		HttpServletRequest hrequest = (HttpServletRequest) request;
		chain.doFilter(request, response);
		// ************************************************************
		// ce filtre doit s'executer après la servlet
		// il efface le panier du client en cours
		// ************************************************************
		HttpSession session = hrequest.getSession();
		nom = (String) session.getAttribute("nom");
		ArrayList<String> listeDisque = Paniers.lescommandes.get(nom);
		listeDisque.clear();
	}

	public void destroy() {
		this.filterConfig = null;
	}

}
