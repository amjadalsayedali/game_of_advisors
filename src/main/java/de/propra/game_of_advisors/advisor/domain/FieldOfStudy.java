package de.propra.game_of_advisors.advisor.domain;

public record FieldOfStudy(String name) {

    public FieldOfStudy {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Field of study must not be blank");
        }
        name = name.trim();
    }
}
