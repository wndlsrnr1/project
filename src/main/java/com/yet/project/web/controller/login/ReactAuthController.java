package com.yet.project.web.controller.login;

import com.yet.project.domain.service.item.ItemService;
import com.yet.project.domain.service.login.LoginService;
import com.yet.project.domain.service.user.UserService;
import com.yet.project.web.dto.login.AuthError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReactAuthController {

    private final UserService userService;

    @GetMapping(value = "/auth", headers = {"referer"})
    public ResponseEntity authenticateReactPath(@RequestParam String uri, HttpSession session) {
        log.info("uri === {}", uri);
        if (userService.authenticateReactURI(session, uri)) {
            return ResponseEntity.ok().build();
        }
        AuthError authError = new AuthError(HttpStatus.UNAUTHORIZED.value(), "", uri);
        return new ResponseEntity<>(authError, HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/isLogin")
    public ResponseEntity checkLogIn(HttpSession httpSession) {
        if (httpSession == null || httpSession.getAttribute("uid") == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok().build();
    }

//    @GetMapping(headers = {"referer"})
//    public
}
