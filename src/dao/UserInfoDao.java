package dao;

import model.UserInfo;

import java.util.Optional;

public interface UserInfoDao {
    Optional<UserInfo> findByUserId(int userId);
}

