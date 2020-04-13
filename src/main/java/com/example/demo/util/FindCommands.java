package com.example.demo.util;

/**
 * Enum that represents the sub-commands of find
 */
public enum FindCommands {
    LEVEL("level"),
    UNDER("under"),
    LAST("last"),
    BETWEEN("between"),
    ID("id")
    ;

    private final String cmd;

    /**
     * Constructor
     * @param cmd A command of the application
     */
    FindCommands(final String cmd) {
        this.cmd = cmd;
    }

    /**
     * Getter for the command
     * @return Command as the string
     */
    public String getCmd() {
        return cmd;
    }
}
