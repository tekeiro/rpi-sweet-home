package org.keirobm.rpisweethome.medialib.search.port;

import org.keirobm.rpisweethome.medialib.search.model.ILinkSearchCandidate;
import org.keirobm.rpisweethome.medialib.watchlist.model.WatchlistItem;

import java.util.List;
import java.util.Optional;

public interface CalculateScorePort {

    /**
     * Calculates the score for the given tags.
     * @param currentTags Current tags in the link.
     * @return Possitive number for a good match, negative number for a immediately exclude the link, 0 for no match
     */
    Double calculateScore(List<String> currentTags);

    /**
     * Calculates the score for the given title.
     * @param cleanTitle Clean title of the link.
     * @param item Watchlist item to compare with.
     * @return Percentage of coincidence in title (from 0.0 to 100.0)
     */
    Double calculateTitleCoincidence(String cleanTitle, WatchlistItem item);

    /**
     * Selects the best candidate from the given list.
     * @param candidates List of candidates to select from.
     * @return Best candidate or Optional.empty() if no candidates.
     */
    Optional<? extends ILinkSearchCandidate> selectBestCandidate(List<? extends ILinkSearchCandidate> candidates);
}
