package repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Function;

public class DomainHelper implements Serializable {

    protected <T> void addFilter(JPAQuery<?> query, T value, Function<T, BooleanExpression> predicate) {

        if (value instanceof Collection<?>) {
            if (!((Collection<?>) value).isEmpty()) {
                query.where(predicate.apply(value));
            }
        } else {
            if (value != null) {
                if (value instanceof String && ((String) value).isEmpty()) {
                    return;
                }
                query.where(predicate.apply(value));
            }
        }
    }
}
