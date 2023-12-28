package s2.grupo8.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String id = request.getParameter("id");
        String password = request.getParameter("password");

        if (isValidUser(id, password)) {
            chain.doFilter(request, response);
        } else {
            response.getWriter().write("Acceso no autorizado");
        }
    }

    private boolean isValidUser(String id, String password) {
        return "usuario1".equals(id) && "contrasena1".equals(password);
    }

    @Override
    public void destroy() {
        
    }
}
