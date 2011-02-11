package suncertify.common;

import java.io.Serializable;

public interface ClientCallback<T> extends Serializable {

    void onFailure(String message);

    boolean onWarning(String message);

    void onSuccess(T result);

}
