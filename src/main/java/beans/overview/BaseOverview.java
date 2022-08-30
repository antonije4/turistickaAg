package beans.overview;

import java.io.Serializable;

public abstract class BaseOverview implements Serializable {

    public void init() {
        if (!processParams()) {
            //throw exception
        }
        if (!processDomain()) {
            //throw exception
        }
    }


    protected abstract boolean processParams();
    protected abstract boolean processDomain();
}
