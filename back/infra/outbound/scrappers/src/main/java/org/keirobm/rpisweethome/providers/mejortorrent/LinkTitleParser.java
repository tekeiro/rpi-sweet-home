package org.keirobm.rpisweethome.providers.mejortorrent;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses raw link titles from MejorTorrent into a clean title, a list of quality/format tags,
 * and an optional season number.
 *
 * <p>Incomplete tags (unclosed {@code [} or {@code (}) are automatically discarded.
 *
 * <p>Input examples:
 * <pre>
 *   "Weapons [4K] (4K)"                                        → title: "Weapons",                                      season: empty, tags: ["4K"]
 *   "Weapons (BluRay-1080p)"                                    → title: "Weapons",                                      season: empty, tags: ["BluRay-1080p"]
 *   "Weapons [Subs. integrados]. (DVDRip)"                      → title: "Weapons",                                      season: empty, tags: ["Subs. integrados", "DVDRip"]
 *   "A Choice of Weapons: Inspired by Gordon Parks (HDTV-720p)" → title: "A Choice of Weapons: Inspired by Gordon Parks", season: empty, tags: ["HDTV-720p"]
 *   "Los Protegidos - 1ª Temporada [108... (HDTV-1080p)"        → title: "Los Protegidos",                               season: 1,     tags: ["HDTV-1080p"]
 * </pre>
 */
public final class LinkTitleParser {

    /**
     * Matches only fully closed {@code [content]} or {@code (content)} tags,
     * enforcing that bracket types match (a {@code [} must close with {@code ]},
     * and {@code (} must close with {@code )}).
     */
    private static final Pattern TAG_PATTERN = Pattern.compile("\\[([^\\[\\]]+)\\]|\\(([^()]+)\\)");

    /** Matches " - Nª Temporada" season markers (e.g. "- 1ª Temporada"). */
    private static final Pattern SEASON_PATTERN = Pattern.compile("\\s*-\\s*(\\d+)ª\\s+Temporada", Pattern.CASE_INSENSITIVE);

    private LinkTitleParser() {}

    public record ParsedTitle(String title, List<String> tags, Optional<Integer> season) {}

    /**
     * Parses a raw link title into its clean title, associated tags, and optional season number.
     *
     * <p>Incomplete tags (unclosed brackets or parentheses) are discarded. Season markers of the
     * form "- Nª Temporada" are extracted and removed from the title.
     *
     * @param rawTitle the full title string as scraped from the page
     * @return a {@link ParsedTitle} containing the clean title, tag list, and season if present
     */
    public static ParsedTitle parse(String rawTitle) {
        // Collect only fully closed tags — incomplete ones are discarded by the regex.
        // Group 1 captures [...] content; group 2 captures (...) content.
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        Matcher matcher = TAG_PATTERN.matcher(rawTitle);
        while (matcher.find()) {
            String tag = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            seen.add(tag.trim());
        }
        List<String> tags = new ArrayList<>(seen);

        // Title is everything before the first [ or (
        String titlePart = rawTitle.replaceAll("\\s*[\\[(].*$", "").trim();

        // Extract season number from "- Nª Temporada" and remove it from the title
        Matcher seasonMatcher = SEASON_PATTERN.matcher(titlePart);
        Optional<Integer> season;
        String title;
        if (seasonMatcher.find()) {
            season = Optional.of(Integer.parseInt(seasonMatcher.group(1)));
            title = seasonMatcher.replaceAll("").trim();
        } else {
            season = Optional.empty();
            title = titlePart;
        }

        return new ParsedTitle(title, tags, season);
    }
}
