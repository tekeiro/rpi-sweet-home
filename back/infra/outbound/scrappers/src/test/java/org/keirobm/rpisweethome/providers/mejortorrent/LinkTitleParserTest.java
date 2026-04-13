package org.keirobm.rpisweethome.providers.mejortorrent;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.keirobm.rpisweethome.providers.mejortorrent.LinkTitleParser.ParsedTitle;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LinkTitleParserTest {

    @ParameterizedTest(name = "[{index}] \"{0}\" → title={1}, season={3}")
    @MethodSource("parseCases")
    void parse_shouldExtractTitleTagsAndSeason(
            String rawTitle,
            String expectedTitle,
            List<String> expectedTags,
            Optional<Integer> expectedSeason) {

        ParsedTitle result = LinkTitleParser.parse(rawTitle);

        assertThat(result.title()).isEqualTo(expectedTitle);
        assertThat(result.tags()).isEqualTo(expectedTags);
        assertThat(result.season()).isEqualTo(expectedSeason);
    }

    static Stream<Arguments> parseCases() {
        return Stream.of(
                // Movies — no season
                arguments(
                        "Weapons (BluRay-1080p)",
                        "Weapons",
                        List.of("BluRay-1080p"),
                        Optional.empty()),
                arguments(
                        "Weapons [Subs. integrados]. (DVDRip)",
                        "Weapons",
                        List.of("Subs. integrados", "DVDRip"),
                        Optional.empty()),
                arguments(
                        "A Choice of Weapons: Inspired by Gordon Parks (HDTV-720p)",
                        "A Choice of Weapons: Inspired by Gordon Parks",
                        List.of("HDTV-720p"),
                        Optional.empty()),
                // Duplicate tags are deduplicated
                arguments(
                        "Weapons [4K] (4K)",
                        "Weapons",
                        List.of("4K"),
                        Optional.empty()),
                // No tags at all
                arguments(
                        "A Plain Title",
                        "A Plain Title",
                        List.of(),
                        Optional.empty()),

                // Incomplete tags — must be discarded
                arguments(
                        "Title [108...",
                        "Title",
                        List.of(),
                        Optional.empty()),
                arguments(
                        "Title (incomplete...",
                        "Title",
                        List.of(),
                        Optional.empty()),
                // Incomplete tag mixed with a complete one — incomplete is discarded
                arguments(
                        "Los Protegidos: Un nuevo poder - 1ª Temporada [108... (HDTV-1080p)",
                        "Los Protegidos: Un nuevo poder",
                        List.of("HDTV-1080p"),
                        Optional.of(1)),

                // Season extraction
                arguments(
                        "Breaking Bad - 2ª Temporada (BluRay-1080p)",
                        "Breaking Bad",
                        List.of("BluRay-1080p"),
                        Optional.of(2)),
                arguments(
                        "Serie XYZ - 10ª Temporada [HDTV-720p]",
                        "Serie XYZ",
                        List.of("HDTV-720p"),
                        Optional.of(10)),
                // Season only, no quality tag
                arguments(
                        "Mi Serie - 3ª Temporada",
                        "Mi Serie",
                        List.of(),
                        Optional.of(3))
        );
    }
}
