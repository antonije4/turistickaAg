package util;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

@Named
@RequestScoped
public class Util implements Serializable {

    @Inject
    private HttpServletRequest request;

    public String getRequestParam(String key) {
        return request.getParameter(key);
    }
}
