package fr.sqli.formation.gamelife.dto.response;

public class ExceptionResponse {

    private String ExceptionMessage;

    public String getExceptionMessage() {
        return ExceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }

    public ExceptionResponse(String exceptionMessage) {
        ExceptionMessage = exceptionMessage;
    }
}
