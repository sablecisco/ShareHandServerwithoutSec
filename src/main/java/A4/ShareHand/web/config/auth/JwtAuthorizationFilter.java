package A4.ShareHand.web.config.auth;

import A4.ShareHand.domain.Member;
import A4.ShareHand.web.config.jwt.JwtProperties;
import A4.ShareHand.web.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String access_token = request.getHeader("ACCESS_TOKEN");
        String refresh_token = request.getHeader("REFRESH_TOKEN");
        String authorization = request.getHeader("Authorization");

        String userEmail;

        userEmail = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(access_token).getClaim("email").asString();
        // refresh code 쓸 때 필요한 코드
//        try {
//            userEmail = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(authorization).getClaim("email").asString();
//        } catch (TokenExpiredException e) {
//            String restoreUsername = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(refresh_token).getClaim("email").asString();
//            if (restoreUsername != null) {
//                Member member = memberRepository.findByEmail(restoreUsername);
//                String accessToken = CreateJwt.createAccessToken(member);
//
//                response.setHeader("ACCESS_TOKEN", accessToken);
//            }
//        }

        if (userEmail != null) {
            Member member = memberRepository.findByEmail(userEmail);

            // 인증은 토큰 검증시 끝. 인증을 하기 위해서가 아닌 스프링 시큐리티가 수행해주는 권한 처리를 위해
            // 아래와 같이 토큰을 만들어서 Authentication 객체를 강제로 만들고 그걸 세션에 저장!
            PrincipalDetails principalDetails = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 값 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response);
    }
}
