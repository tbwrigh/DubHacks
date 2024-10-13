package dev.tbwright.dubhacks.aspect

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component
import org.aspectj.lang.JoinPoint
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

@Aspect
@Component
class AuthAspect {

    @Before("@annotation(dev.tbwright.dubhacks.annotation.Auth)")
    fun beforeAuthMethod(joinPoint: JoinPoint) {
        // Get the current request to extract the JWT from the security context
        val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        val request: HttpServletRequest = requestAttributes.request

        // Extract the authentication object from the security context
        val authentication = request.userPrincipal as JwtAuthenticationToken
        val jwt: Jwt = authentication.token

        // Extract user ID or email from the JWT claims
        val userEmail = jwt.claims["email"] as String

        // Inject the email into the method arguments (optional)
        val args = joinPoint.args
        for (i in args.indices) {
            if (args[i] is String) {
                args[i] = userEmail
                break
            }
        }
    }
}
