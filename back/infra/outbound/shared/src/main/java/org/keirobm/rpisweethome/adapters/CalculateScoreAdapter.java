package org.keirobm.rpisweethome.adapters;

import lombok.RequiredArgsConstructor;
import org.keirobm.rpisweethome.medialib.search.model.ILinkSearchCandidate;
import org.keirobm.rpisweethome.medialib.search.model.QualityTag;
import org.keirobm.rpisweethome.medialib.search.port.CalculateScorePort;
import org.keirobm.rpisweethome.medialib.search.port.QualityTagPersistencePort;
import org.keirobm.rpisweethome.medialib.search.port.QualityTagPort;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CalculateScoreAdapter implements CalculateScorePort {

    private static final double EXCLUDE_SCORE = -9999;

    private final QualityTagPort qualityTagPort;

    @Override
    public Double calculateScore(List<String> currentTags) {
        final var matched = this.qualityTagPort.applyQualityTags(currentTags);
        if (matched.stream().anyMatch(qt -> Boolean.TRUE.equals(qt.getExcludeImmediately()))) {
            return EXCLUDE_SCORE;
        }
        return matched.stream().mapToDouble(QualityTag::getScore).sum();
    }

    @Override
    public Double calculateTitleCoincidence(String cleanTitle, WatchlistItem item) {
        if (cleanTitle == null || item.getTitle() == null) return 0.0;

        Set<String> cleanWords = tokenize(cleanTitle);
        Set<String> itemWords = tokenize(item.getTitle());

        if (itemWords.isEmpty() || cleanWords.isEmpty()) return 0.0;

        long matched = itemWords.stream().filter(cleanWords::contains).count();

        if (matched == 0) return -10.0;

        // Recall: fraction of item title words found in the link title (most important)
        double recall = (double) matched / itemWords.size();
        // Precision: fraction of link title words that belong to the item title (penalises extra words)
        double precision = (double) matched / cleanWords.size();

        return (recall * 0.7 + precision * 0.3) * 100.0;
    }

    private Set<String> tokenize(String text) {
        return Arrays.stream(text.toLowerCase().replaceAll("[^a-z0-9\\s]", " ").trim().split("\\s+"))
                .filter(w -> !w.isBlank())
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<? extends ILinkSearchCandidate> selectBestCandidate(List<? extends ILinkSearchCandidate> candidates) {
        return candidates.stream()
                .filter(c -> c.getScore() >= 0)
                .max(Comparator.comparingDouble(c -> c.getScore() * (c.getTitleCoincidence() / 100.0)));
    }



}
