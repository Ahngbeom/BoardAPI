package com.perfect.community.api.service.user;

import com.google.common.base.Preconditions;
import com.perfect.community.api.dto.user.UserAuthoritiesDTO;
import com.perfect.community.api.dto.user.UserDTO;
import com.perfect.community.api.entity.user.UserEntity;
import com.perfect.community.api.mapper.user.UsersAuthoritiesMapper;
import com.perfect.community.api.mapper.user.UsersMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger log = LogManager.getLogger();

    private final UsersMapper mapper;

    private final UsersAuthoritiesMapper usersAuthoritiesMapper;

    private final PasswordEncoder encoder;

    @Override
    public List<UserDTO> getUserList() {
        return mapper.selectAllUsers().stream().map(entity -> entity.toDTO()).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getUserListWithAuthorities() {
        return mapper.selectAllUsersWithAuthorities().stream().map(entity -> entity.toDTO()).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserInfoByUserId(String userId) {
        UserEntity userEntity = mapper.selectUserByUserId(userId);
        return userEntity != null ? userEntity.toDTO() : null;
    }

    @Override
    public UserDTO getUserInfoWithAuthoritiesByUserId(String userId) {
        UserEntity userEntity = mapper.selectUserWithAuthoritiesByUserId(userId);
        return userEntity != null ? userEntity.toDTO() : null;
    }

    @Override
    public void createUser(UserDTO user) throws RuntimeException {
        Preconditions.checkNotNull(user.getPassword(), "User PW must be not null.");
        Preconditions.checkNotNull(user.getNickname(), "User nickname must be not null.");
        if (!userIdAvailability(user.getUserId())) {
            throw new DuplicateKeyException("User ID is duplicated.");
        }
        if (!nicknameAvailability(user.getNickname())) {
            throw new DuplicateKeyException("User nickname is duplicated.");
        }
        if (user.getAuthorities() == null || user.getAuthorities().size() == 0) {
            throw new RuntimeException("Cannot be request without authorities.");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        if (mapper.insertUser(user) != 1) {
            throw new RuntimeException("Failed to create user.");
        }
        if (usersAuthoritiesMapper.insertUserAuthorities(UserAuthoritiesDTO.builder().userId(user.getUserId()).authorities(user.getAuthorities()).build()) != 1) {
            throw new RuntimeException("Failed to grant user authorities.");
        }
    }

    @Override
    public void updateUserInfo(UserDTO user) {
        Preconditions.checkNotNull(user.getNickname(), "User nickname must be not null.");
        if (mapper.updateUser(user) != 1)
            throw new RuntimeException("failed to update user info");
    }

    @Override
    public void changeUserPassword(UserDTO user) {
        Preconditions.checkNotNull(user.getPassword(), "User PW must be not null.");
        user.setPassword(encoder.encode(user.getPassword()));
        if (mapper.updatePassword(user) != 1)
            throw new RuntimeException("failed to change user password");
    }

    @Override
    public void removeUser(String userId) {
        if (mapper.deleteUser(userId) != 1)
            throw new RuntimeException("That user doesn't exist.");
    }

    @Override
    public void disableUser(String userId) {
        if (mapper.disableUser(userId) != 1)
            throw new RuntimeException("User deactivation failed.");
    }

    @Override
    public void enableUser(String userId) {
        if (mapper.enableMember(userId) != 1)
            throw new RuntimeException("User activation failed.");
    }

    @Override
    public boolean verifyPassword(String userId, String password) {
        Preconditions.checkNotNull(userId, "User PW must be not null.");
        return encoder.matches(password,  mapper.selectUserByUserId(userId).getPassword());
    }

    @Override
    public boolean userIdAvailability(String userId) {
        return mapper.userIdAvailability(userId);
    }

    @Override
    public boolean nicknameAvailability(String nickname) {
        return mapper.nicknameAvailability(nickname);
    }
}
