package util;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

public class JPAHelper {
    private JPAHelper() {
    }

    public static <T> T getUniqueResult(Class<T> clazz, Query query) {
        try {
            return clazz.cast(query.getSingleResult());
        } catch (NoResultException | EntityNotFoundException var3) {
            return null;
        }
    }
    public static <T> List<T> getResultList(Class<T> clazz, Query query) {
        List<?> result = query.getResultList();
        return (List) result;
    }
}
