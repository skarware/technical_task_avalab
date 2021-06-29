package dev.skaringa.avalab.exception;

import dev.skaringa.avalab.api.ErrorCode;
import dev.skaringa.avalab.api.ErrorType;
import lombok.Getter;
import lombok.NonNull;

@Getter
public abstract class BaseException extends RuntimeException {
    @NonNull
    private final ErrorCode code;

    protected BaseException(ErrorCode code, String message) {
        super(message);
        this.code = code;
    }

    public abstract ErrorType getType();
}
