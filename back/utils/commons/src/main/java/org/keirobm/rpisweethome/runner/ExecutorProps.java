package org.keirobm.rpisweethome.runner;

import lombok.Builder;
import lombok.Data;

@Data
public class ExecutorProps {
    private ExecutorType type;
    private Integer threads;

    public ExecutorProps() {
        this.type = ExecutorType.SINGLE_THREAD;
        this.threads = 1;
    }
}
