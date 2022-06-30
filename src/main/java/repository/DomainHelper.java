package repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;

import java.util.Collection;
import java.util.function.Function;

public class DomainHelper {

    protected <T> void addFilter(JPAQuery<?> query, T value, Function<T, BooleanExpression> predicate) {

        if (value instanceof Collection<?>) {
            if (!((Collection<?>) value).isEmpty()) {
                query.where(predicate.apply(value));
            }
        } else {
            if (value != null) {
                query.where(predicate.apply(value));
            }
        }
    }
}
