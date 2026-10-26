package com.one_half_men.app.request;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.one_half_men.annotations.Query;
import com.one_half_men.app.being.Being;
import com.one_half_men.app.db.Key;
import com.one_half_men.app.location.Location;
import com.one_half_men.app.shelter.Resource;
import com.one_half_men.app.user.Specialty;
import com.one_half_men.app.user.User;

import io.avaje.jsonb.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Json
@Query
@Getter
@ToString
@AllArgsConstructor
@Builder
public class Request {
    private @Setter Key<User> author;
    private @Setter @Builder.Default Optional<Key<User>> owner = Optional.empty();
    private @Builder.Default Boolean proxy = false;
    private @Builder.Default Set<Key<User>> participants = new HashSet<>();
    private @Setter LocalDate date;
    private @Builder.Default Optional<CloseReason> closeReason = Optional.empty();
    private @Setter Location location;
    private @Setter String description;
    private @Builder.Default Set<Being> beings = new HashSet<>();
    private @Builder.Default Set<Resource> requiredResources = new HashSet<>();
    private @Builder.Default Set<Specialty> requiredSpecialties = new HashSet<>();

    /** @return If the user has permissions to edit this request */
    public boolean canEdit(Key<User> user) {
        return user.equals(author) || (owner.isPresent() && owner.get().equals(user));
    }

    public boolean addParticipant(Key<User> user) {
        return participants.add(user);
    }

    /**
     * Claims the Request under an Owner if not claimed
     * 
     * @param user User claiming request as owner
     * @return If operation succeeded (no previous owner)
     */
    public boolean claimProxy(Key<User> user) {
        if (owner.isPresent() || !getProxy())
            return false;

        owner = Optional.of(user);
        return true;
    }

    /**
     * Closes the request for the specified reason
     *
     * @param reason Reason for closing the request
     */
    public void close(CloseReason reason) {
        closeReason = Optional.of(reason);
    }

    /** @return If the request has been closed */
    public boolean isClosed() {
        return closeReason.isPresent();
    }
}
