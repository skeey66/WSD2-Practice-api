package kr.ac.jbnu.ksh.wsdteaching.user.controller;

import kr.ac.jbnu.ksh.wsdteaching.common.ApiResponse;
import kr.ac.jbnu.ksh.wsdteaching.user.dto.UserDto;
import kr.ac.jbnu.ksh.wsdteaching.user.request.CreateUserRequest;
import kr.ac.jbnu.ksh.wsdteaching.user.request.LoginRequest;
import kr.ac.jbnu.ksh.wsdteaching.user.request.UpdateUserRequest;
import kr.ac.jbnu.ksh.wsdteaching.user.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //GET 1) 전체 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<UserDto>>> getUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    //GET 2) 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> getUser(@PathVariable Long id) {
        UserDto user = userService.getUser(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("User not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    //POST 1) 회원가입
    @PostMapping
    public ResponseEntity<ApiResponse<UserDto>> createUser(
            @RequestBody CreateUserRequest req
    ) {
        if (req.getUsername() == null || req.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("username and password are required"));
        }

        UserDto created = userService.createUser(req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "User created"));
    }

    //POST 2) 로그인
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(
            @RequestBody LoginRequest req
    ) {
        if (req.getUsername() == null || req.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("username and password are required"));
        }

        boolean success = userService.login(req);

        if (!success) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Invalid username or password"));
        }

        Map<String, Object> data = Map.of("login", true);
        return ResponseEntity.ok(ApiResponse.success(data, "Login success"));
    }

    //PUT 1) 전체 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDto>> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest req
    ) {
        UserDto updated = userService.updateUser(id, req);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("User not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(updated, "User updated"));
    }

    //PUT 2) 이메일만 수정
    @PutMapping("/{id}/email")
    public ResponseEntity<ApiResponse<UserDto>> updateUserEmail(
            @PathVariable Long id,
            @RequestParam String email
    ) {
        if (email == null || email.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("email is required"));
        }

        UserDto updated = userService.updateUserEmail(id, email);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("User not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(updated, "Email updated"));
    }

    // DELETE 1) 단건 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUser(id);
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("User not found"));
        }
        return ResponseEntity.ok(ApiResponse.success(null, "User deleted"));
    }

    // DELETE 2) 전체 삭제
    @DeleteMapping
    public ResponseEntity<ApiResponse<Map<String, Integer>>> deleteAllUsers() {
        int count = userService.deleteAllUsers();
        Map<String, Integer> data = Map.of("deletedCount", count);
        return ResponseEntity.ok(ApiResponse.success(data, "All users deleted"));
    }

    //5xx 1) 500 Internal Server Error
    @GetMapping("/error/internal")
    public ResponseEntity<ApiResponse<Void>> errorInternal() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Internal server error (demo)"));
    }

    //5xx 2) 501 Not Implemented
    @GetMapping("/error/not-implemented")
    public ResponseEntity<ApiResponse<Void>> errorNotImplemented() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                .body(ApiResponse.error("Not implemented yet"));
    }
}
