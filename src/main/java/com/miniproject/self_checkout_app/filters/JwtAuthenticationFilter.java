package com.miniproject.self_checkout_app.filters;

import com.miniproject.self_checkout_app.service.UserService;
import com.miniproject.self_checkout_app.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationFilter implements Filter {
	
	private final JwtUtil jwtUtil;
	private final UserService userService;
	private final String[] openPaths= {"/login","/signup","/verify-token","/product/qr/","/store-cart/qr/","/store-cart/get-attached-user/"
			,"/cart"};
	
	
	public JwtAuthenticationFilter(JwtUtil jwtUtil,UserService userService) {
		this.jwtUtil=jwtUtil;
		this.userService=userService;
	}

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialize filter if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        String requestedPath=httpRequest.getRequestURI();
        
        for(String path:openPaths) {
        	if(requestedPath.startsWith(path)) {
        		System.out.println("Skipping Jwt Checking for "+requestedPath);
        		chain.doFilter(request, response);
        		return;
        	}
        }
        
        String authorizationHeader = httpRequest.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7); // Extract the token from the header

            try {
                if (jwtUtil.validateToken(token)) {
                    // Token is valid, continue with the request
                	   String username = jwtUtil.extractUsername(token);
                       
                       // Retrieve user details and set authentication
                       UserDetails userDetails = userService.loadUserByUsername(username);
                       UsernamePasswordAuthenticationToken authenticationToken =
                               new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                       
                       // Set the authentication in SecurityContext
                       SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                       
                       // Continue the 
                    chain.doFilter(request, response);
                } else {
                    // Invalid token or expired
                    httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    httpResponse.getWriter().write("Invalid or expired token");
                }
            } catch (ExpiredJwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Token expired");
            } catch (UnsupportedJwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Unsupported JWT token");
            } catch (MalformedJwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Malformed JWT token");
            } catch (Exception e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("Authentication failed");
            }
        } else {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Authorization header missing or invalid");
        }
    }

    @Override
    public void destroy() {
        // Clean up if needed
    }
}
