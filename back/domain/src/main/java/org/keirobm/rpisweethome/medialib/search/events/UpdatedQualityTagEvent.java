package org.keirobm.rpisweethome.medialib.search.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.keirobm.rpisweethome.common.events.BaseEvent;
import org.keirobm.rpisweethome.medialib.search.model.QualityTag;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdatedQualityTagEvent extends BaseEvent {
    private final QualityTag tag;

    public String toString() {
        return String.format("UpdatedQualityTagEvent{ '%s' }", tag.getTag());
    }
}
