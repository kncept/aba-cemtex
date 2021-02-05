package com.kncept.abacemtex.file.compactor;

import com.kncept.abacemtex.file.DetailRecord;

import java.util.List;

public interface Compactor {

    /**
     * extracts a 'compaction key' from the detail record
     * @param record
     * @return
     */
    public String compactionKey(DetailRecord record);

    /**
     *
     * @param records All records with a matching compaction key, in the order they appeared in the file
     * @return
     */
    public DetailRecord compact(List<DetailRecord> records);

}
