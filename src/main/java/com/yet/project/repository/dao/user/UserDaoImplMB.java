package com.yet.project.repository.dao.user;

import com.yet.project.domain.user.UserKakao;
import com.yet.project.domain.user.User;
import com.yet.project.domain.user.UserSocialLogin;
import com.yet.project.repository.mybatismapper.user.UserMapper;
import com.yet.project.web.dto.login.Agreement;
import com.yet.project.web.dto.login.UserConsent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Component
@RequiredArgsConstructor
public class UserDaoImplMB implements UserDao {
    private final UserMapper userMapper;

    @Override
    public User findUserById(Long id) {
        return userMapper.selectUserByUid(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.selectUserByEmail(email);
    }

    @Override
    public Map<Integer, Agreement> findAgreementRequiredMap() {
        return userMapper.selectConsentOptions();
    }

    @Override
    public Map<Integer, Agreement> findAgreementOptionMap() {
        return userMapper.selectConsentRequireds();
    }

    @Override
    public Map<Integer, Agreement> findAllAgreements() {
        return userMapper.selectAgreements();
    }

    @Override
    public List<User> findUsersByPhone(String phone) {
        return userMapper.selectUsersPhones(phone);
    }

    @Override
    public Long saveUser(User user) {
        user.setAuth(2);
        Long uid = userMapper.insertUser(user);
        return uid;
    }

    @Override
    public void saveUserAgreementsRequired(Long uid, List<Integer> requiredIDList) {
        Set<Integer> required = userMapper.selectConsentOptions().keySet();
        for (Integer agreementId : required) {
            UserConsent userConsent = new UserConsent();
            userConsent.setCid(agreementId);
            userConsent.setUid(uid);

            if (requiredIDList.contains(agreementId)) {
                userConsent.setConsent(true);
            } else {
                userConsent.setConsent(false);
            }
            userMapper.insertUserConsent(userConsent);
        }
    }

    @Override
    public void saveUsersAgreementsOption(Long uid, List<Integer> optionalIDList) {
        Set<Integer> optionalSet = userMapper.selectConsentRequireds().keySet();
        for (Integer agreementId : optionalSet) {
            UserConsent userConsent = new UserConsent();
            userConsent.setCid(agreementId);
            userConsent.setUid(uid);

            if (optionalIDList.contains(agreementId)) {
                userConsent.setConsent(true);
            } else {
                userConsent.setConsent(false);
            }
            userMapper.insertUserConsent(userConsent);
        }
    }

    @Override
    public void removeUserById(Long uid) {
        userMapper.deleteUserById(uid);
    }

    @Override
    public UserKakao findUserByKakaoId(Long kakaoId) {
        return userMapper.selectKakaoUserByKakaoId(kakaoId);
    }

    @Override
    public void saveUserBySocialLogin(UserSocialLogin userSocialLogin) {
        userMapper.insertUserSocialLogin(userSocialLogin);
    }

    @Override
    public void saveUserByKakao(UserKakao userKakao) {
        userMapper.insertUserKakao(userKakao);
    }

}
