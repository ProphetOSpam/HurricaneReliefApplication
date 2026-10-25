package com.one_half_men.app.being;

import com.one_half_men.app.ToText;

import io.avaje.jsonb.Json;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Json
@EqualsAndHashCode
@ToString
public class PhoneNumber implements ToText {
    private @Getter @Setter String countryCode;
    private @Getter @Setter String areaCode;
    private @Getter @Setter String localNumber;

    public PhoneNumber(String countryCode, String areaCode, String localNumber) {
        this.countryCode = countryCode;
        this.areaCode = areaCode;
        this.localNumber = localNumber;
    }

    @Override
    public String toText() {
        return String.format("+%s (%s) %s", countryCode, areaCode, localNumber);
    }
}
