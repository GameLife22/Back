package fr.sqli.formation.gamelife.dto;

public class ExceptionDtoOut {

    private String ExceptionMessage;

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }

    public ExceptionDtoOut(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }
}
