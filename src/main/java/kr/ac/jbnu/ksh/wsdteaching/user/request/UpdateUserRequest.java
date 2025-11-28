package kr.ac.jbnu.ksh.wsdteaching.user.request;

public class UpdateUserRequest {

    private String username;
    private String email;

    public String getUsername() { return username; }
    public String getEmail() { return email; }

    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
}
