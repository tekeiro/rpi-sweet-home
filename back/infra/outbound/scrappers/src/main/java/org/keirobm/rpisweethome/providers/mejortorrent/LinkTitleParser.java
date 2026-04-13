package org.keirobm.rpisweethome.providers.mejortorrent;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses raw link titles from MejorTorrent into a clean title and a list of quality/format tags.
 *
 * <p>Input examples:
 * <pre>
 *   "Weapons [4K] (4K)"                                      → title: "Weapons",                                     tags: ["4K", "4K"]
 *   "Weapons (BluRay-1080p)"                                  → title: "Weapons",                                     tags: ["BluRay-1080p"]
 *   "Weapons [Subs. integrados]. (DVDRip)"                    → title: "Weapons",                                     tags: ["Subs. integrados", "DVDRip"]
 *   "A Choice of Weapons: Inspired by Gordon Parks (HDTV-720p)" → title: "A Choice of Weapons: Inspired by Gordon Parks", tags: ["HDTV-720p"]
 * </pre>
 */
public final class LinkTitleParser {

    private static final Pattern TAG_PATTERN = Pattern.compile("[\\[(]([^\\]\\)]+)[\\]\\)]");

    private LinkTitleParser() {}

    public record ParsedTitle(String title, List<String> tags) {}

    /**
     * Parses a raw link title into its clean title and associated tags.
     *
     * @param rawTitle the full title string as scraped from the page
     * @return a {@link ParsedTitle} containing the clean title and tag list
     */
    public static ParsedTitle parse(String rawTitle) {
        LinkedHashSet<String> seen = new LinkedHashSet<>();
        Matcher matcher = TAG_PATTERN.matcher(rawTitle);
        while (matcher.find()) {
            seen.add(matcher.group(1).trim());
        }
        List<String> tags = new ArrayList<>(seen);

        // Title is everything before the first [ or (
        String title = rawTitle.replaceAll("\\s*[\\[(].*$", "").trim();

        return new ParsedTitle(title, tags);
    }
}
