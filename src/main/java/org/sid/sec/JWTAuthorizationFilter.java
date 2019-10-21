package org.sid.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requestd-With, Content-Type, Access-Control-Request-Method, Access-control-Request-Headers, authorization");
		response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
		if(request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
		}
		else {
					String jwt = request.getHeader(SecurityConstants.HEADER_STRING);
		System.out.println(jwt);
		if (jwt == null || !jwt.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			filterChain.doFilter(request, response);
			return;
		}

		/*
		 * je vais parser un toke, je vais le signer avec mon signature, je vais
		 * remplacer bearer par une chaine vide get body reccuépére contenu de ce token
		 * 
		 * 
		 */
		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
				.parseClaimsJws(jwt.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody();

		// si je veux les username
		
		
		
		
		String username = claims.getSubject();

		// si je veux charger les roles déserializer

		ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");
		// je vais creer une liste et parcourir les roles
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.get("authority")));

		});
		//créer un objet de type UsernamePasswordAuthenticationToken
		/*
		 * verifier si le token est valide ou pas le mot de passe pas nécessaire
		 * par contre j'ai besoin de connaitre les roles
		 * charger le context security de spring 
		 * */
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		// 
		filterChain.doFilter(request, response);

	}
		}
		


}
