package de.propra.game_of_advisors.advisor.application;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdvisorQueryService {
    public List<AdvisorSummary> findAll() {
        return List.of(
                new AdvisorSummary(
                        1L,
                        "Karla Turing",
                        List.of("Compilerbau", "Formale Methoden")
                ),
                new AdvisorSummary(
                        2L,
                        "Ada Lovelace",
                        List.of("Java", "Software Engineering")
                ),
                new AdvisorSummary(
                        3L,
                        "Grace Hopper",
                        List.of("Compilerbau", "Programmiersprachen")

                )
        );
    }

    public Optional<AdvisorDetails> findById(long id) {
        return switch((int) id) {
            case 1 -> Optional.of(new AdvisorDetails(
                    1L,
                    "Karla Turing",
                    "karla.turing@hhu.de",
                    List.of("Compilerbau", "Formale Methoden"),
                    List.of(new InformationFileView(
                                    1L,
                                    "Betreuungsleitfaden",
                                    "Informationen zum Ablauf meiner Betreuung.",
                                    "Markdown",
                                    "Karla Turing",
                                    "30.09.2026"
                            ),
                            new InformationFileView(
                                    2L,
                                    "Exposé-Vorlage",
                                    "Vorlage für ein Exposé.",
                                    "PDF",
                                    "Karla Turing",
                                    "29.09.2026"
                            )
                    ),
                    List.of(
                            new TopicSummary(
                                    1L,
                                    "Entwicklung eines Parsergenerators in Rust",
                                    List.of("Compilerbau", "Rust"),
                                    List.of("Compilerbau")
                            )
                    )
            ));
            case 2 -> Optional.of(new AdvisorDetails(
                    2L,
                    "Ada Lovelace",
                    "ada.lovelace@hhu.de",
                    List.of("Java", "Software Engineering"),
                    List.of(),
                    List.of()
            ));
            default -> Optional.empty();
        };
    }

//    public List<AdvisorSummary> findByField(String field) {
//        return findAll().stream()
//                .filter(advisor -> advisor.fields().contains(field))
//                .toList();
//    }

    public List<AdvisorSummary> findByFields(List<String> fields) {
        if(fields.isEmpty()) {
            return findAll();
        }
        return findAll().stream()
                .filter(advisor -> advisor.fields().stream()
                        .anyMatch(fields::contains)
                )
                .toList();
    }

}
