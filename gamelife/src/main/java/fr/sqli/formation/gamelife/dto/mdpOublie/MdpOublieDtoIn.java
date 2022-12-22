package fr.sqli.formation.gamelife.dto.mdpOublie;

public class MdpOublieDtoIn {
    private String login;


    public MdpOublieDtoIn() {
    }

    public MdpOublieDtoIn(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
