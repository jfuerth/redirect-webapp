package org.jboss.errai.redirect;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RedirectServlet
 */
public class RedirectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	  String relativePath = request.getPathInfo();
	  URI basePath = URI.create(getInitParameter("target-base-url"));
	  URI redirectTo = basePath.resolve(relativePath);
	  response.setStatus(301);
	  response.setHeader("location", redirectTo.toString());
	}
}
