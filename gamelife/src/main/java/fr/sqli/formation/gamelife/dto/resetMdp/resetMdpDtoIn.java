package fr.sqli.formation.gamelife.dto.resetMdp;

public class resetMdpDtoIn {
    private String pwd;


    public resetMdpDtoIn() {
    }

    public resetMdpDtoIn(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
