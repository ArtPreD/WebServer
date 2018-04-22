package accounts;

public class UserProfile {
    private String login;
    private String email;
    private String password;


    public UserProfile(String login, String email, String password) {
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public UserProfile(String login) {
        this.login = login;
        this.email = login;
        this.password = login;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
