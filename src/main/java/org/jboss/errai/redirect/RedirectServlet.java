/* Copyright 2013 Red Hat Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.errai.redirect;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet or Filter that redirects based on configuration in its init-params in web.xml.
 */
public class RedirectServlet extends HttpServlet implements Filter {
  private static final long serialVersionUID = 1L;

  private final Set<String> excludedPaths = new HashSet<String>();
  private URI basePath;
  private Boolean includeSubpaths;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String relativePath = request.getPathInfo();
    if (relativePath == null) {
      // this works when we're a filter
      relativePath = request.getServletPath();
      relativePath = relativePath.substring(1);
    }
    URI redirectTo;
    if (includeSubpaths) {
      redirectTo = basePath.resolve(relativePath);
    }
    else {
      redirectTo = basePath;
    }
    response.setStatus(301);
    response.setHeader("location", redirectTo.toString());
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
          ServletException {

    // this returns the context-relative path for requests to a filter
    String relativePath = ((HttpServletRequest) request).getServletPath();

    // trim leading /
    relativePath = relativePath.substring(1);

    if (excludedPaths.contains(relativePath)) {
      chain.doFilter(request, response);
    }
    else {
      doGet((HttpServletRequest) request, (HttpServletResponse) response);
    }
  }

  @Override
  public void init(FilterConfig config) throws ServletException {
    String excludes = config.getInitParameter("excludes");
    for (String path : excludes.split(",")) {
      excludedPaths.add(path.trim());
    }

    basePath = URI.create(config.getInitParameter("target-base-url"));
    includeSubpaths = Boolean.valueOf(config.getInitParameter("include-subpath"));
  }

  @Override
  public void init(ServletConfig config) throws ServletException {
    if (config.getInitParameter("exclude") != null) {
      log("WARNING: found init-parameter 'exclude' when running in Servlet mode. Exclusion only works when configured as a filter!");
    }

    basePath = URI.create(config.getInitParameter("target-base-url"));
    includeSubpaths = Boolean.valueOf(config.getInitParameter("include-subpath"));
  }

}
