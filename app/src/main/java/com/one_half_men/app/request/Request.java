package com.one_half_men.app.request;

import java.net.Authenticator;
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
    private @Setter @Builder.Default Optional<User> owner = Optional.empty();
    private Boolean proxy;
    private Set<User> participants;
    private @Setter Date date;
    private @Setter @Builder.Default Optional<CloseReason> closeReason = Optional.empty();
    private @Setter Location location;
    private @Setter String description;
    private Set<Being> beings;

    public User getEditor() {
        return owner.isPresent() ? owner.get() : author;
    }

    public boolean addParticipant(User user) {
        return participants.add(user);
    }

    /**
     * Claims the Request under an Owner if not claimed
     * 
     * @param user User claiming request as owner
     * @return If operation succeeded (no previous owner)
     */
    public boolean claimProxy(User user) {
        if (owner.isPresent() || !getProxy()) return false;
        owner = Optional.of(user);
        return true;
    }

    public void close(CloseReason reason) {
        closeReason = Optional.of(reason);
    }

    public boolean isClosed() {
        return closeReason.isPresent();
    }
}
