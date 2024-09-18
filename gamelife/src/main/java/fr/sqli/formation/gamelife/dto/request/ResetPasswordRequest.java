package fr.sqli.formation.gamelife.dto.request;

public class ResetPasswordRequest {
    private String pwd;


    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String pwd) {
        this.pwd = pwd;
    }

    public String getPassword() {
        return this.pwd;
    }

    public void setPassword(String pwd) {
        this.pwd = pwd;
    }

}
