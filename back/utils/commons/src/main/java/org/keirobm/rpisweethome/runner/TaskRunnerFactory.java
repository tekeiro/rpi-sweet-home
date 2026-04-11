package org.keirobm.rpisweethome.runner;

import lombok.experimental.UtilityClass;

import java.util.concurrent.Executors;

@UtilityClass
public class TaskRunnerFactory {

    public static TaskRunner createFrom(ExecutorProps props) {
        return switch (props.getType()) {
            case SINGLE_THREAD -> new TaskRunner(Executors.newSingleThreadExecutor());
            case VIRTUAL_THREAD_POOL -> new TaskRunner(Executors.newVirtualThreadPerTaskExecutor());
            case FIXED_POOL -> new TaskRunner(Executors.newFixedThreadPool(props.getThreads()));
            default -> throw new IllegalArgumentException("Unsupported executor type: " + props.getType());
        };
    }

}
