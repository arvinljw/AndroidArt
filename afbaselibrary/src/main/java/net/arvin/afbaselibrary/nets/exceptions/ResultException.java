package net.arvin.afbaselibrary.nets.exceptions;

/**
 * created by arvin on 16/10/24 17:29
 * email：1035407623@qq.com
 */
public class ResultException extends RuntimeException {
    private int errCode = 0;

    private String message;

    public ResultException(int errCode, String msg) {
        super(msg);
        this.errCode = errCode;
        this.message = msg;
    }

    public int getErrCode() {
        return errCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
