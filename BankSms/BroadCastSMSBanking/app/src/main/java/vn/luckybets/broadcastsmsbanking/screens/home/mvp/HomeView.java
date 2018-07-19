package vn.luckybets.broadcastsmsbanking.screens.home.mvp;

import vn.luckybets.broadcastsmsbanking.model.Error;

public interface HomeView {
    void onLoadding(int typeRequest);

    void onLoadSuccess(int typeRequest, Object obj);

    void onError(int typeRequest, Error e);
}
