package fr.sqli.formation.gamelife.dto.request;

public class ResetPasswordRequest {
    private String pwd;


    public ResetPasswordRequest() {
    }

    public ResetPasswordRequest(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
