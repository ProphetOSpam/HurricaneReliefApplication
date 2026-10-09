package com.one_half_men.app.request;

import java.util.Date;
import java.util.Optional;
import java.util.SortedSet;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.being.Being;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.user.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Query
@Builder
public class Request {
    private @Getter @Setter User author;
    private @Getter @Setter Optional<User> owner;
    private @Getter SortedSet<User> participants;
    private @Getter @Setter Date date;
    private @Getter @Setter Optional<CloseReason> closeReason;
    private @Getter @Setter Location location;
    private @Getter @Setter String description;
    private @Getter SortedSet<Being> beings;

    public Request(User author, Optional<User> owner, SortedSet<User> participants, Date date, Optional<CloseReason> closeReason, Location location, String description, SortedSet<Being> beings) {
        this.author = author;
        this.owner = owner;
        this.participants = participants;
        this.date = date;
        this.closeReason = closeReason;
        this.location = location;
        this.description = description;
        this.beings = beings;
    }

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
