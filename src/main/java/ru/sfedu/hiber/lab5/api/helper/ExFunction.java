package ru.sfedu.hiber.lab5.api.helper;

public interface ExFunction<T, R> {
    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws Exception;
}
