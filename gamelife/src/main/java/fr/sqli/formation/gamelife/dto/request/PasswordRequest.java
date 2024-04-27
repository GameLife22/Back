package fr.sqli.formation.gamelife.dto.request;

public class PasswordRequest {
    private String login;

    public PasswordRequest() {
    }

    public PasswordRequest(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
