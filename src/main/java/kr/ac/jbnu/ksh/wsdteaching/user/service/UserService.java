package kr.ac.jbnu.ksh.wsdteaching.user.service;

import kr.ac.jbnu.ksh.wsdteaching.user.domain.User;
import kr.ac.jbnu.ksh.wsdteaching.user.dto.UserDto;
import kr.ac.jbnu.ksh.wsdteaching.user.request.CreateUserRequest;
import kr.ac.jbnu.ksh.wsdteaching.user.request.UpdateUserRequest;
import kr.ac.jbnu.ksh.wsdteaching.user.request.LoginRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private final Map<Long, User> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public List<UserDto> getAllUsers() {
        List<UserDto> result = new ArrayList<>();
        for (User user : store.values()) {
            result.add(toDto(user));
        }
        return result;
    }

    public UserDto getUser(Long id) {
        User user = store.get(id);
        if (user == null) return null;
        return toDto(user);
    }

    public UserDto createUser(CreateUserRequest req) {
        User user = new User();
        user.setId(sequence.getAndIncrement());
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(req.getPassword());

        store.put(user.getId(), user);
        return toDto(user);
    }

    public UserDto updateUser(Long id, UpdateUserRequest req) {
        User user = store.get(id);
        if (user == null) return null;

        if (req.getUsername() != null) {
            user.setUsername(req.getUsername());
        }
        if (req.getEmail() != null) {
            user.setEmail(req.getEmail());
        }
        return toDto(user);
    }

    public UserDto updateUserEmail(Long id, String email) {
        User user = store.get(id);
        if (user == null) return null;
        user.setEmail(email);
        return toDto(user);
    }

    public boolean deleteUser(Long id) {
        return store.remove(id) != null;
    }

    public int deleteAllUsers() {
        int count = store.size();
        store.clear();
        return count;
    }

    public boolean login(LoginRequest req) {
        return store.values().stream()
                .anyMatch(user ->
                        user.getUsername().equals(req.getUsername()) &&
                                user.getPassword().equals(req.getPassword())
                );
    }

    private UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
