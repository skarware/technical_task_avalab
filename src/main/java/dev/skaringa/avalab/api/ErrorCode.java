package dev.skaringa.avalab.api;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNEXPECTED,
    ILLEGAL_ARGUMENT,
    INVALID_ARGUMENT,
    INVALID_JSON,
    NOT_FOUND
}
