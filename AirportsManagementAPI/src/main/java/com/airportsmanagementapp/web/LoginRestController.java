package com.airportsmanagementapp.web;

import com.airportsmanagementapp.config.filters.JWTAuthenticationFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/login")
public class LoginRestController {

    private final JWTAuthenticationFilter filter;

    public LoginRestController(JWTAuthenticationFilter filter) {
        this.filter = filter;
    }

    @PostMapping
    public ResponseEntity doLogin(HttpServletRequest request,
                                  HttpServletResponse response) throws IOException {
        Authentication auth = this.filter.attemptAuthentication(request, response);
        return ResponseEntity.ok().body(this.filter.generateJwt(response, auth));
    }
}
