package com.gerenciador_backlog_api.enums;

public enum Status {

    TODO("todo"),
    IN_PROGRESS("in_progress"),
    DONE("done");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    @com.fasterxml.jackson.annotation.JsonValue
    public String getValue() {
        return value;
    }

    @com.fasterxml.jackson.annotation.JsonCreator
    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid Status: " + value);
    }
}
