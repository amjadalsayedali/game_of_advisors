package de.propra.game_of_advisors.advisor.domain;

import de.propra.game_of_advisors.user.domain.GitHubUserId;

import java.util.LinkedHashSet;
import java.util.Set;

public final class AdvisorProfile {

    private final GitHubUserId owenerId;

    private String name;
    private String contactInfomration;
    private Set<FieldOfStudy> fields;

    public AdvisorProfile(
            GitHubUserId owenerId,
            String name,
            String contactInfomration,
            Set<FieldOfStudy> fields
    ) {
        this.owenerId = owenerId;

        changeName(name);
        changeContactInformation(contactInfomration);
        replaceFields(fields);
    }

    public GitHubUserId owenerId() {
        return owenerId;
    }

    public String name() {
        return name;
    }

    public String contactInfomration() {
        return contactInfomration;
    }

    public Set<FieldOfStudy> fields() {
        return Set.copyOf(fields);
    }


    public void changeName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Advisor name must not be blank");
        }
        this.name = name;
    }

    public void changeContactInformation(String contactInfomration) {
        if (contactInfomration == null || contactInfomration.isBlank()) {
            throw new IllegalArgumentException("Contact infomration must not be blank");
        }
        this.contactInfomration = contactInfomration.trim();
    }

    public void replaceFields(Set<FieldOfStudy> fields) {
        if (fields == null) {
            throw new IllegalArgumentException("fields cannot be null");
        }
        this.fields = new LinkedHashSet<>(fields);
    }
}
