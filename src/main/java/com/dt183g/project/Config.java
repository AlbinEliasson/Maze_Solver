package com.dt183g.project;

public class Config {
    public static final boolean INFO =                      true;

    public static final boolean DEBUG_OVERRIDE =            false;
    public static final boolean DEBUG =                     true                            || DEBUG_OVERRIDE;
    public static final boolean DEBUG_MODEL =               true    && DEBUG                || DEBUG_OVERRIDE;
    public static final boolean DEBUG_ALGORITHMS =          true    && DEBUG_MODEL          || DEBUG_OVERRIDE;
    public static final boolean DEBUG_DIJKSTRAS_QUEUE =     true    && DEBUG_ALGORITHMS     || DEBUG_OVERRIDE;
    public static final boolean DEBUG_DIJKSTRAS_MIN_HEAP =  true    && DEBUG_ALGORITHMS     || DEBUG_OVERRIDE;
    public static final boolean DEBUG_ASTAR =               true    && DEBUG_ALGORITHMS     || DEBUG_OVERRIDE;
    public static final boolean DEBUG_CONTROLLER =          true    && DEBUG                || DEBUG_OVERRIDE;
    public static final boolean DEBUG_VIEW =                true    && DEBUG                || DEBUG_OVERRIDE;
}
