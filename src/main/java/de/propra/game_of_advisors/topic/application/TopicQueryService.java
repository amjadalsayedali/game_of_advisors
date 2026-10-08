package de.propra.game_of_advisors.topic.application;


import java.util.List;
import java.util.Optional;

public class TopicQueryService {
    public List<TopicSummary> findAll() {
        return List.of(
                new TopicSummary(
                        1L,
                        "Entwicklung eines Parsergenerators in Rust",
                        "Karla Turing",
                        List.of("Compilerbau", "Rust"),
                        List.of("Compilerbau")
                ),
                new TopicSummary(
                        2L,
                        "Verifikation verteilter Systeme",
                        "Ada Lovelace",
                        List.of("Formale Methoden"),
                        List.of()
                )
        );
    }

    public Optional<TopicDetails> findById(long id) {
        return switch ((int) id){
            case 1 -> Optional.of(new TopicDetails(
                    1L,
                    "Entwicklung eines Parsergenerators in Rust",
                    """
                            In dieser Abschlussarbeit soll ein Parsergenerator in Rust entwickelt werden.
                            """,
                    "Karla Turing",
                    1L,
                    List.of("Compilerbau", "Rust"),
                    List.of("Compilerbau")
            ));
            case 2 -> Optional.of(new TopicDetails(
                    2L,
                    "Verifikation verteilter Systeme",
                    """
                            Untersuchung und Verifikation verteilter Systeme mit formalen Methoden
                            """,
                    "Ada Lovelace",
                    2L,
                    List.of("Formale Methoden"),
                    List.of()
            ));
            default -> Optional.empty();
        };
    }

    public List<TopicSummary> findByFields(List<String> fields) {
        if(fields.isEmpty()) {
            return findAll();
        }

        return findAll().stream()
                .filter(topic ->
                        topic.fields().stream()
                                .anyMatch(fields::contains)
                )
                .toList();
    }
}
