package com.yet.project.web.service.user;

import com.yet.project.domain.user.User;
import com.yet.project.repository.dao.user.UserDao;
import com.yet.project.web.dto.login.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserDao userDao;

    public void userJoin(JoinForm join) {
        User user = new User();
        user.setName(join.getName());
        user.setPassword(join.getPassword());
        user.setEmail(join.getEmail());
        user.setPhone(join.getPhone());

        //user consent 한번에 저장하도록 함.
        userDao.joinCustomerUser(user);
        User insertedUser = userDao.findUserByEmail(user.getEmail());
        Long uid = insertedUser.getUid();
        log.info("uid when saved = {}", uid);

        List<Integer> requiredIDList = join.getRequired();
        List<Integer> optionIDList = join.getOption();
        userDao.saveUsersAgreementsRequired(uid, requiredIDList);
        userDao.saveUsersAgreementsOption(uid, optionIDList);
    }

    public void unregisterUser(User unregisterUser) {
        Long uid = unregisterUser.getUid();
        userDao.deleteUserById(uid);
    }
}
