package A4.ShareHand.web.config.jwt;

import A4.ShareHand.domain.Member;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class CreateJwt {

    public static String createAccessToken(Member memberEntity) {
        return JWT.create()
                .withSubject(memberEntity.getEmail())
                .withClaim("id", memberEntity.getId())
                .withClaim("email", memberEntity.getEmail())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
    }

//    public static String createRefreshToken(Member memberEntity, String AccessToken) {
//        return JWT.create()
//                .withSubject(memberEntity.getEmail())
//                .withClaim("AccessToken", AccessToken)
//                .withClaim("username", memberEntity.getEmail())
//                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
//    }
}
