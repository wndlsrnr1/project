package com.yet.project.web.controller.login;

import com.yet.project.domain.user.User;
import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.web.dto.login.Agreement;
import com.yet.project.web.dto.login.JoinForm;
import com.yet.project.web.dto.login.LoginForm;
import com.yet.project.web.dto.login.UnregisterForm;
import com.yet.project.web.service.login.LoginAuth;
import com.yet.project.web.service.login.LoginService;
import com.yet.project.web.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.manager.util.SessionUtils;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/login")
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final UserDao userDao;
    private final LoginAuth loginAuth;
    private final UserService userService;

    @ModelAttribute("required")
    public Map<Integer, Agreement> requiredAgreements() {
        return userDao.getRequiredAgreements();
    }

    @ModelAttribute("option")
    public Map<Integer, Agreement> optionAgreements() {
        return userDao.getOptionalAgreements();
    }

    @GetMapping
    public String viewLoginPage(@ModelAttribute("loginForm") LoginForm loginForm, HttpSession session) {
        if (session.getAttribute("uid") != null) {
            return "redirect:/";
        }
        return "/login/login";
    }

    @PostMapping
    public String authLogin(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
        log.info("loginForm = {}", loginForm);
        //바인딩 실패
        if (bindingResult.hasErrors()) {
            return "/login/login";
        }
        //이미 로그인된 상태
        if (session.getAttribute("uid") != null) {
            return "redirect:/";
        }

        LoginForm form = new LoginForm();
        form.setEmail(loginForm.getEmail());
        form.setPassword(loginForm.getPassword());
        User user = loginAuth.authUserByLoginForm(form);

        //비밀번호가 다름
        if (user == null) {
            bindingResult.rejectValue("password", "Login");
            log.info("bindingResult = {}", bindingResult);
            return "/login/login";
        }

        if (user != null) {
            session.setAttribute("uid", user.getUid());
        }


        //성공
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(@SessionAttribute("uid") String uid, HttpSession session) {
        if (session != null && uid != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/join")
    public String viewJoin(@ModelAttribute("join") JoinForm joinForm, Model model) {
        return "/login/join";
    }

    @PostMapping("/join")
    public String joinUser(@Validated @ModelAttribute("join") JoinForm join, BindingResult bindingResult, Model model) {
        log.info("join {}", join);
        if (bindingResult.hasErrors()) {
            log.info("bindingResult = {}", bindingResult);
            return "/login/join";
        }

        User userByEmail = userDao.findUserByEmail(join.getEmail());
        if (userByEmail != null) {
            bindingResult.rejectValue("email", "Login");
            log.info("bindingResult = {}", bindingResult);
            return "/login/join";
        }

        List<User> userByPhone = userDao.findUserByPhone(join.getPhone());
        if (userByPhone.size() != 0) {
            bindingResult.rejectValue("phone", "Login");
            log.info("bindingResult = {}", bindingResult);
            return "/login/join";
        }

        String password = join.getPassword();
        String password2 = join.getPassword2();
        if (password != null && password2 != null) {
            if (!password.equals(password2)) {
                bindingResult.rejectValue("password2", "Login");
                return "/login/join";
            }
        }


        Map<Integer, Agreement> required = (Map<Integer, Agreement>) model.getAttribute("required");
        log.info("required = {}", required.keySet());
        log.info("join.getRequired() = {}", join.getRequired());
        log.info("!join.getRequired().containsAll(required.keySet()) = {}", !join.getRequired().containsAll(required.keySet()));

        if (!join.getRequired().containsAll(required.keySet())) {
            bindingResult.rejectValue("required", "NotFull");
            return "/login/join";
        }

        //DB에 저장
        userService.userJoin(join);

        return "redirect:/";
    }

    //uid 변수화 하기
    @GetMapping("/unregister")
    public String unregister(@ModelAttribute("unregisterForm") UnregisterForm unregisterForm, HttpSession session, @SessionAttribute("uid") Long uid, Model model) {
        if (session == null || uid == null ) {
            return "redirect:/";
        }

        User basicUserInfo = new User();
        User userById = userDao.getUserById(uid);
        basicUserInfo.setName(userById.getName());
        basicUserInfo.setEmail(userById.getEmail());
        log.info("userById.getEmail() = {}", userById.getEmail());
        model.addAttribute("basicUserInfo", basicUserInfo);

        return "/login/unregister";
    }

    @PostMapping("/unregister")
    public String sendUnregister(@Validated @ModelAttribute("unregisterForm") UnregisterForm unregisterForm, BindingResult bindingResult, HttpSession session, @SessionAttribute("uid") Long uid, Model model) {
        if (session == null || uid == null ) {
            return "redirect:/";
        }

        //삭제하고자 하는 email과 지금 세션에 등록된 uid로 조회된 id가 다르면 거절
        User userById = userDao.getUserById(uid);
        String userPassword = userById.getPassword();

        User basicUserInfo = new User();
        basicUserInfo.setName(userById.getName());
        basicUserInfo.setEmail(userById.getEmail());

        model.addAttribute("basicUserInfo", basicUserInfo);

        if (bindingResult.hasErrors()) {
            return "/login/unregister";
        }

        String password = unregisterForm.getPassword();
        String password2 = unregisterForm.getPassword2();

        if (!password.equals(password2)) {
            bindingResult.rejectValue("password2", "unregister", "같은 비밀번호를 입력해주세요");
            log.info("비밀번호 다름 오류");
            return "/login/unregister";
        }

        Boolean consent = unregisterForm.getConsent();
        if (consent == null || !consent) {
            bindingResult.rejectValue("consent", "unregister", "필수 항목에 동의해주셔야 합니다");
            log.info("동의 없음 오류");
            return "/login/unregister";
        }

        if (!userPassword.equals(password)) {
            log.info("접속한 사용자의 비밀번호와 다름 오류");
            bindingResult.rejectValue("password2", "unregister", "같은 비밀번호를 입력해주세요");
            return "/login/unregister";
        }



        User unregisterUser = new User();
        unregisterUser.setUid(uid);

        userService.unregisterUser(unregisterUser);
        session.invalidate();
        return "redirect:/";
    }



}
