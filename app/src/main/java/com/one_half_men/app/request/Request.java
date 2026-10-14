package com.one_half_men.app.request;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.being.Being;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.user.User;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Json
@Query
@Getter
@AllArgsConstructor
@Builder
public class Request {
    private @Setter User author;
    private @Setter Optional<User> owner;
    private Set<User> participants;
    private @Setter Date date;
    private @Setter Optional<CloseReason> closeReason;
    private @Setter Location location;
    private @Setter String description;
    private Set<Being> beings;

    public boolean addParticipant(User user) {
        return false;
    }

    public void claimProxy(User user) {

    }

    public void close(CloseReason reason) {

    }

    public boolean isProxy() {
        return false;
    }

    public boolean isClosed() {
        return false;
    }
}
