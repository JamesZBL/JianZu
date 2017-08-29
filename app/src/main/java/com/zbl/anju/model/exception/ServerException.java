package com.zbl.anju.model.exception;

import com.zbl.anju.util.UIUtils;

/**
 * @author James
 * @描述 服务器异常
 */
public class ServerException extends Exception {

    public ServerException(int errorCode) {
        this(UIUtils.getString(com.zbl.anju.R.string.error_code) + errorCode);
    }

    public ServerException(String message) {
        super(message);
    }

}
