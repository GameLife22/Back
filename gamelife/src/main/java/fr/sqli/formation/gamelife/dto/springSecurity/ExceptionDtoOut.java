package fr.sqli.formation.gamelife.dto.springSecurity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionDtoOut {
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LogManager.getLogger();

    private String exceptionName;
    private String exceptionMessage;
    private String exceptionCause;

    /**
     * Constructor of the object.
     */
    public ExceptionDtoOut() {
        super();
    }

    /**
     * Constructor of the object.
     *
     * @param pExceptionMessage a message
     */
    public ExceptionDtoOut(String pExceptionMessage) {
        super();
        this.setExceptionName(this.getClass().getName());
        this.setExceptionMessage(pExceptionMessage);
    }

    /**
     * Constructor of the object.
     *
     * @param pExceptionMessage a message
     */
    public ExceptionDtoOut(String pExceptionMessage, Throwable pException) {
        super();
        this.setExceptionName(pException.getClass().getName());
        this.setExceptionMessage(pExceptionMessage);
        if (pException != null) {
            try (var sw = new StringWriter(); var pw = new PrintWriter(sw);) {
                pException.printStackTrace(pw);
                this.setExceptionCause(sw.toString());
            } catch (Exception e) {
                ExceptionDtoOut.LOG.fatal("Erreur lors de la recuperation de la cause", e);
            }
        }
    }

    /**
     * Constructor of the object.
     *
     * @param pException where to find information for building the DTO
     */
    public ExceptionDtoOut(Throwable pException) {
        super();
        this.setExceptionName(pException.getClass().getName());
        this.setExceptionMessage(pException.getMessage());
        if (pException.getCause() != null) {
            try (var sw = new StringWriter(); var pw = new PrintWriter(sw);) {
                pException.getCause().printStackTrace(pw);
                this.setExceptionCause(sw.toString());
            } catch (Exception e) {
                ExceptionDtoOut.LOG.fatal("Erreur lors de la recuperation de la cause", e);
            }
        }
    }

    /**
     * Gets the attribute value.
     *
     * @return the exceptionCause value.
     */
    public String getExceptionCause() {
        return this.exceptionCause;
    }

    /**
     * Sets the attribute value.
     *
     * @param pExceptionCause the new value for exceptionCause attribute
     */
    public void setExceptionCause(String pExceptionCause) {
        this.exceptionCause = pExceptionCause;
    }

    /**
     * Gets the attribute value.
     *
     * @return the exceptionName value.
     */
    public String getExceptionName() {
        return this.exceptionName;
    }

    /**
     * Sets the attribute value.
     *
     * @param pExceptionName the new value for exceptionName attribute
     */
    public void setExceptionName(String pExceptionName) {
        this.exceptionName = pExceptionName;
    }

    /**
     * Gets the attribute value.
     *
     * @return the exceptionMessage value.
     */
    public String getExceptionMessage() {
        return this.exceptionMessage;
    }

    /**
     * Sets the attribute value.
     *
     * @param pExceptionMessage the new value for exceptionMessage attribute
     */
    public void setExceptionMessage(String pExceptionMessage) {
        this.exceptionMessage = pExceptionMessage;
    }

}
