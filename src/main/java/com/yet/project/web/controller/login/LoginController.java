package com.yet.project.web.controller.login;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.yet.project.domain.user.User;
import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.web.dto.login.*;
import com.yet.project.domain.service.login.LoginAuth;
import com.yet.project.domain.service.user.UserService;
import com.yet.project.web.enums.user.SocialName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    private final static String CLIENT_ID = "4a78e4143def6e8bbbc876055b65676d";
    private final static String REDIRECT_URI = "http://localhost:8080/login/auth/kakao";
    private final static String APP_ADMIN = "99827a82a26373a3a8b1008bdb062d5a";
    private final static String SOCIAL_KAKAO = "kakao";


    @ModelAttribute("required")
    public Map<Integer, Agreement> requiredAgreements() {
        return userDao.findAgreementRequiredMap();
    }

    @ModelAttribute("option")
    public Map<Integer, Agreement> optionAgreements() {
        return userDao.findAgreementOptionMap();
    }

    @GetMapping
    public String loginRequest(@ModelAttribute("loginForm") LoginForm loginForm, HttpSession session) {
        if (session.getAttribute("uid") != null) {
            return "redirect:/";
        }
        return "/login/login";
    }

    @PostMapping
    public String authLoginRequest(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult, HttpSession session) {
        log.info("loginForm = {}", loginForm);
        //바인딩 실패
        if (bindingResult.hasErrors()) {
            return "/login/login";
        }

        //이미 로그인된 상태
        if (session.getAttribute("uid") != null) {
            return "redirect:/";
        }

        User user = userService.getUserInDbByForm(loginForm);
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
    public String logoutRequest(@SessionAttribute("uid") String uid, HttpSession session) {
        if (session != null && uid != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    @GetMapping("/join")
    public String joinFormRequest(@ModelAttribute("join") BasicJoinForm basicJoinForm, Model model) {
        return "/login/join";
    }

    @PostMapping("/join")
    public String joinUserRequest(@Validated @ModelAttribute("join") BasicJoinForm join, BindingResult bindingResult, Model model) {
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

        List<User> userByPhone = userDao.findUsersByPhone(join.getPhone());
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
    public String unregisterFormRequest(@ModelAttribute("unregisterForm") UnregisterForm unregisterForm, HttpSession session, @SessionAttribute("uid") Long uid, Model model) {
        if (session == null || uid == null) {
            return "redirect:/";
        }

        User basicUserInfo = new User();
        User userById = userDao.findUserById(uid);
        basicUserInfo.setName(userById.getName());
        basicUserInfo.setEmail(userById.getEmail());
        log.info("userById.getEmail() = {}", userById.getEmail());
        model.addAttribute("basicUserInfo", basicUserInfo);

        return "/login/unregister";
    }


    @PostMapping("/unregister")
    public String sendUnregisterRequest(@Validated @ModelAttribute("unregisterForm") UnregisterForm unregisterForm, BindingResult bindingResult, HttpSession session, @SessionAttribute("uid") Long uid, Model model) {
        if (session == null || uid == null) {
            return "redirect:/";
        }

        //삭제하고자 하는 email과 지금 세션에 등록된 uid로 조회된 id가 다르면 거절
        User userById = userDao.findUserById(uid);
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

    @GetMapping("/unregister/kakao")
    public String unregisterKakaoRequest(@ModelAttribute("unregisterForm") UnregisterFormKakao unregisterForm, HttpSession session, @SessionAttribute("uid") Long uid, Model model) {
        if (session == null || uid == null) {
            return "redirect:/";
        }

        User basicUserInfo = new User();
        User userById = userDao.findUserById(uid);
        basicUserInfo.setName(userById.getName());
        basicUserInfo.setEmail(userById.getEmail());
        log.info("userById.getEmail() = {}", userById.getEmail());
        model.addAttribute("basicUserInfo", basicUserInfo);

        return "/login/unregister_kakao";
    }

    @PostMapping("/unregister/kakao")
    public String sendUnregisterKakaoRequest(@Validated @ModelAttribute("unregisterForm") UnregisterFormKakao unregisterForm, BindingResult bindingResult, HttpSession session, @SessionAttribute("uid") Long uid, Model model) {
        if (session == null || uid == null) {
            return "redirect:/";
        }

        //삭제하고자 하는 email과 지금 세션에 등록된 uid로 조회된 id가 다르면 거절
        User userById = userDao.findUserById(uid);
        User basicUserInfo = new User();
        basicUserInfo.setName(userById.getName());
        basicUserInfo.setEmail(userById.getEmail());

        model.addAttribute("basicUserInfo", basicUserInfo);

        if (bindingResult.hasErrors()) {
            return "/login/unregister";
        }

        Boolean consent = unregisterForm.getConsent();
        if (consent == null || !consent) {
            bindingResult.rejectValue("consent", "unregister", "필수 항목에 동의해주셔야 합니다");
            log.info("동의 없음 오류");
            return "/login/unregister";
        }

        User unregisterUser = new User();
        unregisterUser.setUid(uid);

        userService.unregisterUser(unregisterUser);
        session.invalidate();
        return "redirect:/";
    }


    //"https://kauth.kakao.com/oauth/authorize?client_id=4a78e4143def6e8bbbc876055b65676d&redirect_uri=http://localhost:8080/login/kakao&response_type=code"
    @GetMapping("/kakao")
    public String loginKakakoViewRequest() {
        return "redirect:https://kauth.kakao.com/oauth/authorize?client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI + "&response_type=code&scope=account_email,openid";
    }

    //카카오에서 코드 받기 코드 받기
    @GetMapping("/auth/kakao")
    public String kakaoAuthRequest(
        @RequestParam(required = false) String code,
        @RequestParam(required = false) String state,
        @RequestParam(required = false) String error,
        @RequestParam(name = "error_description", required = false) String errorDescription,
        RedirectAttributes redirectAttributes,
        HttpSession session
    ) {

        if (error != null) {
            return "/login/login";
        }

        AccessToken accessToken = null;
        //액세스 토큰 정보 받아오기
        try {
            accessToken = userService.getAccessToken(code, CLIENT_ID, REDIRECT_URI);
            if (accessToken == null) {
                log.info("액세스 토큰 얻어오기 실패");
                return "/login/login";
            }

        } catch (JsonProcessingException e) {
            //예외처리하기
            log.info("errors in JsonProcessing Exception accessToken", e);
            return "/login/login";
        } catch (Exception e) {
            log.info("errors in accessToken", e);
            return "/login/login";
        }

        KaKaoUserInfo kakaoUserInfo = null;

        try {
            String accessTokenStr = accessToken.getAccessToken();
            kakaoUserInfo = userService.getUserInfo(accessTokenStr);
            if (kakaoUserInfo == null) {
                log.info("유저 정보 얻어오기 실패");
                return "/login/login";
            }
        } catch (JsonProcessingException e) {
            return "/login/login";
        } catch (Exception e) {
            return "/login/login";
        }

        Long kakaoId = kakaoUserInfo.getId();
        String email = kakaoUserInfo.getKakaoAccount().getEmail();
        User user = userService.findUserKakao(kakaoId);

        if (user == null) {
            log.info("null 이어야 함");
            session.setAttribute("type", SOCIAL_KAKAO);
            session.setAttribute("kakao_id", kakaoId);
            session.setAttribute("email", email);
            return "redirect:/login/join/social/kakao";
        }

        session.setAttribute("uid", user.getUid());
        log.info("uid {}", session.getAttribute("uid"));
        return "redirect:/";
    }


    @GetMapping("/join/social/{socialName}")
    public String joinBySocialViewRequest(
        @PathVariable("socialName") String socialName,
        HttpSession session,
        @ModelAttribute("join") SocialJoinForm socialJoinForm
    ) {

        String uid = (String) session.getAttribute("uid");
        if (uid != null) {
            return "redirect:/login";
        }

        if (socialName.equals(SOCIAL_KAKAO)) {
            return "/login/kakaojoin";
        }
        return "redirect:/";
    }


    @PostMapping("/join/social/{socialName}")
    public String joinBySocialSendRequest(
        @PathVariable("socialName") SocialName socialName,
        @Validated @ModelAttribute("join") SocialJoinForm socialJoinForm,
        BindingResult bindingResult,
        @SessionAttribute("type") String typeStr,
        @SessionAttribute("kakao_id") String kakaoId,
        @SessionAttribute("email") String email,
        HttpSession session,
        Model model
    ) {

        if (socialName.equals(SocialName.KAKAO)) {
            if (bindingResult.hasErrors()) {
                return "/login/kakaojoin";
            }

            socialJoinForm.getPhone();
            socialJoinForm.getEmail();
            User userByEmail = userDao.findUserByEmail(socialJoinForm.getEmail());

            if (userByEmail != null) {
                bindingResult.rejectValue("email", "Login");
                log.info("bindingResult = {}", bindingResult);
                return "/login/kakaojoin";
            }

            List<User> userByPhone = userDao.findUsersByPhone(socialJoinForm.getPhone());
            if (userByPhone.size() != 0) {
                bindingResult.rejectValue("phone", "Login");
                log.info("bindingResult = {}", bindingResult);
                return "/login/kakaojoin";
            }

            Map<Integer, Agreement> required = (Map<Integer, Agreement>) model.getAttribute("required");
            if (!socialJoinForm.getRequired().containsAll(required.keySet())) {
                bindingResult.rejectValue("required", "NotFull");
                return "/login/kakaojoin";
            }

            //모두 성공했을때
            userService.userJoinByKakao(socialJoinForm, kakaoId, typeStr);
            Long kakaoIdLong = Long.parseLong(kakaoId);
            User userKakao = userService.findUserKakao(kakaoIdLong);
            session.setAttribute("uid", userKakao.getUid());
            return "redirect:/";
        }

        return "redirect:/";
    }


}
