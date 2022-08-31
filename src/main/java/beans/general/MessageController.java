package beans.general;


import enums.MessageType;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class MessageController {

    private void showMessage(MessageType messageType, Severity severity, String message, Object...params) {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(severity);
        facesMessage.setSummary(message);
        FacesContext.getCurrentInstance().addMessage(messageType.getId(), facesMessage);
    }

    public void showErrorMessage(MessageType messageType, String message, Object...params) {
        showMessage(messageType, FacesMessage.SEVERITY_ERROR, message, params);
    }

    public void showInfoMessage(MessageType messageType, String message, Object...params) {
        showMessage(messageType, FacesMessage.SEVERITY_INFO, message, params);
    }
}
