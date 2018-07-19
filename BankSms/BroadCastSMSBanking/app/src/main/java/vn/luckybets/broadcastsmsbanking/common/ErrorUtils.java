package vn.luckybets.broadcastsmsbanking.common;

import vn.luckybets.broadcastsmsbanking.model.Error;

public class ErrorUtils {

    public static final int SYS_ERROR = -1;
    public static final int SUCCESS=1;

    public static Error getError(int code) {
        switch (code) {
            case SYS_ERROR:
                return new Error(SYS_ERROR, "Lỗi hệ thống");
            case SUCCESS:
                return new Error(SYS_ERROR, "Request thành công");
            default:
                return new Error(SYS_ERROR, "Lỗi hệ thống");

        }
    }
}
