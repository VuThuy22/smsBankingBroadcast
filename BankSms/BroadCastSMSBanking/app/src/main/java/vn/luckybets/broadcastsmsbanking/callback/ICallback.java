package vn.luckybets.broadcastsmsbanking.callback;

import vn.luckybets.broadcastsmsbanking.model.Error;

public interface ICallback<T> {
    void onSuccess(T result);

    void onError(Error e);
}
