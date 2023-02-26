package com.yet.project.repository.dao.user;

import com.yet.project.domain.user.User;
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
    public User getUserById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    @Override
    public Map<Integer, Agreement> getRequiredAgreements() {
        return userMapper.findRequiredAgreement();
    }

    @Override
    public Map<Integer, Agreement> getOptionalAgreements() {
        return userMapper.findOptionalAgreement();
    }

    @Override
    public Map<Integer, Agreement> getAgreements() {
        return userMapper.findAllAgreements();
    }

    @Override
    public List<User> findUserByPhone(String phone) {
        return userMapper.findByPhone(phone);
    }

    @Override
    public Map<Integer, String> getAgreementsName() {
        return userMapper.findAllAgreementNames();
    }

    @Override
    public Long joinCustomerUser(User user) {
        user.setAuth(2);
        Long uid = userMapper.insertUser(user);
        return uid;
    }


    @Override
    public void saveUsersAgreements(Long uid, List<Integer> requiredIDList) {

    }

    @Override
    public void saveUsersAgreementsRequired(Long uid, List<Integer> requiredIDList) {
        Set<Integer> required = userMapper.findRequiredAgreement().keySet();
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
        Set<Integer> optionalSet = userMapper.findOptionalAgreement().keySet();
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
    public void deleteUserById(Long uid) {
        userMapper.deleteUserById(uid);
    }


}
