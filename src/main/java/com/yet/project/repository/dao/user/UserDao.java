package com.yet.project.repository.dao.user;

import com.yet.project.domain.user.User;
import com.yet.project.web.dto.login.Agreement;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User getUserById(Long id);

    User findUserByEmail(String email);

    Map<Integer, Agreement> getRequiredAgreements();

    Map<Integer, Agreement> getOptionalAgreements();

    Map<Integer, Agreement> getAgreements();

    List<User> findUserByPhone(String phone);

    Map<Integer, String> getAgreementsName();

    Long joinCustomerUser(User user);

    void saveUsersAgreements(Long uid, List<Integer> requiredIDList);

    void saveUsersAgreementsRequired(Long uid, List<Integer> requiredIDList);

    void saveUsersAgreementsOption(Long uid, List<Integer> requiredIDList);

    void deleteUserById(Long uid);
}
