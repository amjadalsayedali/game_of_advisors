package de.propra.game_of_advisors.topic.application;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
                        "Verifikation verteiler Systeme",
                        "Ada Lovelace",
                        List.of("Formale Methoden"),
                        List.of()
                )
        );
    }
}
