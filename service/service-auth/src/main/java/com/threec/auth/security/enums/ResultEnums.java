package com.threec.auth.security.enums;

public enum ResultEnums {
    ACCOUNT_LOCKED(70001, " TC 账号锁定"),
    ACCOUNT_EXPIRED(70002, " TC 账号过期"),
    ACCOUNT_NON_ENABLED(70003, " TC 账号未启用"),
    CREDENTIALS_EXPIRED(70004, " TC 凭证过期"),
    JWT_EXPIRED(70005, " TC JWT expired"),
    JWT_UNSUPPORTED(70006, " TC Unsupported JWT"),
    JWT_MALFORMED(70007, " TC Malformed JWT"),
    JWT_ILLEGAL_PARAM(70008, " TC Illegal param"),
    JWT_INVALID_SIGNATURE(70009, " TC Invalid signature"),
    ERROR_USERNAME_OR_PASSWORD(70010, " TC username or password error"),
    ACCESS_DENIED(70011, " TC access denied"),
    AUTHENTICATION_FAILED(70012, " TC Authentication failed, please login again"),
    LOGIN_SUCCESS(70013, " TC login success"),
    LOGOUT_SUCCESS(70014, " TC Logout success"),
    ALTERNATE_LOGIN(70015, " TC Account accessed from another device"),
    INVALID_REQUEST_DATA(70016, "TC Invalid request data"),
    SUCCESS(200, " TC 成功");

    private int code;

    private String msg;

    ResultEnums(int code, String msg) {
        setCode(code);
        setMsg(msg);
    }

    public static String getMessageByCode(int code) {
        for (ResultEnums value : ResultEnums.values()) {
            if (value.getCode() == code) {
                return value.getMsg();
            }
        }
        return null;
    }

    public static Integer getCodeByMessage(String message) {
        for (ResultEnums value : ResultEnums.values()) {
            if (value.getMsg().equals(message)) {
                return value.getCode();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    private void setMsg(String msg) {
        this.msg = msg;
    }
}
