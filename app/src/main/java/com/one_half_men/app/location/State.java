package com.one_half_men.app.location;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum State {
    ALABAMA("AL"),
    ALASKA("AK"),
    AMERICAN_SAMOA("AS"),
    ARIZONA("AZ"),
    ARKANSAS("AR"),
    CALIFORNIA("CA"),
    COLORADO("CO"),
    CONNECTICUT("CT"),
    DELAWARE("DE"),
    DISTRICT_OF_COLUMBIA("DC"),
    FEDERATED_STATES_OF_MICRONESIA("FM"),
    FLORIDA("FL"),
    GEORGIA("GA"),
    GUAM("GU"),
    HAWAII("HI"),
    IDAHO("ID"),
    ILLINOIS("IL"),
    INDIANA("IN"),
    IOWA("IA"),
    KANSAS("KS"),
    KENTUCKY("KY"),
    LOUISIANA("LA"),
    MAINE("ME"),
    MARSHALL_ISLANDS("MH"),
    MARYLAND("MD"),
    MASSACHUSETTS("MA"),
    MICHIGAN("MI"),
    MINNESOTA("MN"),
    MISSISSIPPI("MS"),
    MISSOURI("MO"),
    MONTANA("MT"),
    NEBRASKA("NE"),
    NEVADA("NV"),
    NEW_HAMPSHIRE("NH"),
    NEW_JERSEY("NJ"),
    NEW_MEXICO("NM"),
    NEW_YORK("NY"),
    NORTH_CAROLINA("NC"),
    NORTH_DAKOTA("ND"),
    NORTHERN_MARIANA_ISLANDS("MP"),
    OHIO("OH"),
    OKLAHOMA("OK"),
    OREGON("OR"),
    PALAU("PW"),
    PENNSYLVANIA("PA"),
    PUERTO_RICO("PR"),
    RHODE_ISLAND("RI"),
    SOUTH_CAROLINA("SC"),
    SOUTH_DAKOTA("SD"),
    TENNESSEE("TN"),
    TEXAS("TX"),
    UTAH("UT"),
    VERMONT("VT"),
    VIRGIN_ISLANDS("VI"),
    VIRGINIA("VA"),
    WASHINGTON("WA"),
    WEST_VIRGINIA("WV"),
    WISCONSIN("WI"),
    WYOMING("WY"),
    ;

    /**
     * Abbreviation of the state.
     * Example for South Carolina: {@code "SC"}
     */
    private final String abbreviation;

    public static Optional<State> fromAbbreviation(String abbreviation) {
        for (State state : State.values()) {
            if (state.abbreviation.equals(abbreviation)) {
                return Optional.of(state);
            }
        }

        return Optional.empty();
    }
}
