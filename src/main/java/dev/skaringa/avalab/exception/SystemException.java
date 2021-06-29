package dev.skaringa.avalab.exception;

import dev.skaringa.avalab.api.ErrorCode;
import dev.skaringa.avalab.api.ErrorType;

public class SystemException extends BaseException {
    public SystemException(ErrorCode code, String message) {
        super(code, message);
    }

    public static SystemException notFound(String message) {
        return new SystemException(ErrorCode.NOT_FOUND, message);
    }

    @Override
    public ErrorType getType() {
        return ErrorType.SYSTEM;
    }
}
