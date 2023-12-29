package com.freediving.community.application.domain.constant;

public enum TrueFalseString {

    TRUE("true"), FALSE("false");

    private final String value;

    private TrueFalseString(String bool) {
        this.value = bool;
    }

    public String getValue() {
        return value;
    }
}
