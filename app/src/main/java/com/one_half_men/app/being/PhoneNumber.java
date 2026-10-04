package com.one_half_men.app.being;

import lombok.Getter;
import lombok.Setter;

public class PhoneNumber {
    private @Getter @Setter String countryCode;
    private @Getter @Setter String areaCode;
    private @Getter @Setter String localNumber;

    public PhoneNumber(String countryCode, String areaCode, String localNumber) {
        this.countryCode = countryCode;
        this.areaCode = areaCode;
        this.localNumber = localNumber;
    }

    @Override
    public String toString() {
        return null;
    }
}
