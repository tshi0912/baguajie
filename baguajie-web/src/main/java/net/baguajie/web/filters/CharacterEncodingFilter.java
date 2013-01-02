package net.baguajie.web.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class CharacterEncodingFilter extends OncePerRequestFilter {

	private static final String METHOD_POST = "POST";

	private String encoding;

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	private String toUTF(String inStr) throws UnsupportedEncodingException {
		String outStr = "";
		if (inStr != null) {
			// The default encoding for tomcat is iso-8859-1
			outStr = new String(inStr.getBytes("iso-8859-1"), encoding);
		}
		return outStr;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String method = request.getMethod();
		if (METHOD_POST.equalsIgnoreCase(method)) {
			request.setCharacterEncoding(encoding);
		} else {
			// Get all the parameters
			Enumeration<String> paramNames = request.getParameterNames();
			// Iterate the parameters
			while (paramNames.hasMoreElements()) {
				// Get the parameter name
				String name = paramNames.nextElement();
				// Get the parameter values
				String values[] = request.getParameterValues(name);
				// Values are not empty
				if (values != null) {
					if (values.length == 1) {
						try {
							// try to covert the encoding
							String vlustr = toUTF(values[0]);
							// set this value in attributes;
							request.setAttribute(name, vlustr);
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					} else {
						for (int i = 0; i < values.length; i++) {
							try {
								String vlustr = toUTF(values[i]);
								values[i] = vlustr;
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
						request.setAttribute(name, values);
					}
				}
			}
		}
		response.setCharacterEncoding(this.encoding);
		filterChain.doFilter(request, response);
	}

}
