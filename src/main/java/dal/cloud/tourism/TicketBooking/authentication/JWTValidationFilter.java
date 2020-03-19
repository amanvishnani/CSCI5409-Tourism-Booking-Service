package dal.cloud.tourism.TicketBooking.authentication;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;

public class JWTValidationFilter extends BasicAuthenticationFilter {

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String HEADER_STRING = "Authorization";

    public JWTValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    public JWTValidationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null) {
            SecurityContextHolder.getContext().setAuthentication(null);
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING);
        if (token != null) {
            try {
                DecodedJWT jwt = JWT.decode(token);
                JwkProvider provider = new UrlJwkProvider(jwt.getIssuer());
                Jwk jwk = provider.get(jwt.getKeyId());
                Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
                algorithm.verify(jwt);

                // Check expiration
                if (jwt.getExpiresAt().before(Calendar.getInstance().getTime())) {
                    throw new RuntimeException("Expired token!");
                    // parse the token.
                }
                String user = jwt.getSubject();
                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}
