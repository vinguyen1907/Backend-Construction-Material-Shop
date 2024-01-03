package com.example.cmsbe.models.enums;

public enum UserType {
    EMPLOYEE(Values.EMPLOYEE),
    MANAGER(Values.MANAGER);

    private UserType (String val) {
        // force equality between name of enum instance, and value of constant
        if (!this.name().equals(val))
            throw new IllegalArgumentException("Incorrect use of UserType");
    }

    public static class Values {
        public static final String EMPLOYEE = "EMPLOYEE";
        public static final String MANAGER = "MANAGER";
    }
}
