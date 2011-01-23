package suncertify.common;

public interface ClientCallback<T> {

    void onFailure(String message);

    boolean onWarning(String message);

    void onSuccess(T result);

}
