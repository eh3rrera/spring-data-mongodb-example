package com.example.demo.util;

import com.example.demo.domain.DifficultyLevel;

public class ValidationUtils {
    /**
     * Checks if a string reference is null or empty
     * @param str String to check
     * @return true if the string reference is null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * Validates the arguments give to the program
     * @param args Arguments to validate
     * @return A null reference if there are no errors or the error message otherwise
     */
    public static String validateArgs(String[] args) {
        String error;

        // Validate that there's at least one argument
        if(args == null || args.length == 0) {
            error = "The program didn't receive any arguments";
        } else {
            // Validate commands
            error = validateCommand(args[0]);

            // Validate arguments (number and type)
            if( isEmpty(error) ) {
                error = validateArgsNumberType(args);
            }
        }

        return error;
    }

    /**
     * Validate if the user entered a valid command
     * @param cmd Command to validate
     * @return Null if the command is valid, error message otherwise
     */
    private static String validateCommand(String cmd) {
        for (Commands c : Commands.values()) {
            if (c.getCmd().equals(cmd.toLowerCase())) {
                return null;
            }
        }

        return "Invalid command";
    }

    /**
     * Validates the number and type of the arguments according to the given command
     * @param args Arguments of the command
     * @return Null if the arguments are valid, error message otherwise
     */
    private static String validateArgsNumberType(String[] args) {
        String error = null;
        String cmd = args[0].toLowerCase();

        if(Commands.MARK.getCmd().equals(cmd)) {
            if(args.length == 2) {
                try {
                    Long.parseLong(args[1]);
                } catch(Exception e) {
                    error = "The second argument must be an integer representing the recipe ID";
                }
            } else if(args.length == 1) {
                error = "The program didn't receive the ID of the recipe";
            } else {
                error = "The program didn't receive the correct number of arguments";
            }
        } else if(Commands.SEARCH.getCmd().equals(cmd)) {
            if(args.length == 1) {
                error = "The program didn't receive something to search for";
            }
        } else if(Commands.FIND.getCmd().equals(cmd)) {
            // Validate commands
            error = validateFindCommand(args[1]);

            // Validate arguments (number and type)
            if( isEmpty(error) ) {
                error = validateFindArgsNumberType(args);
            }
        }

        return error;
    }

    /**
     * Validates the number and type of the arguments according to the given find command
     * @param args Arguments of the command
     * @return Null if the arguments are valid, error message otherwise
     */
    private static String validateFindArgsNumberType(String[] args) {
        String error = null;
        String cmd = args[1].toLowerCase();

        if (FindCommands.ID.getCmd().equals(cmd)) {
            if(args.length == 3) {
                try {
                    Long.parseLong(args[2]);
                } catch(Exception e) {
                    error = "The third argument must be an integer representing the recipe ID";
                }
            } else if(args.length == 2) {
                error = "The program didn't receive the ID of the recipe";
            } else {
                error = "The program didn't receive the correct number of arguments";
            }
        } else if (FindCommands.BETWEEN.getCmd().equals(cmd)) {
            if(args.length == 4) {
                // Validate min minutes
                try {
                    Long.parseLong(args[2]);
                } catch(Exception e) {
                    error = "The third argument must be an integer representing the minimum minutes of preparation time";
                }
                // Validate max minutes
                try {
                    Long.parseLong(args[3]);
                } catch(Exception e) {
                    error = "The fourth argument must be an integer representing the maximum minutes of preparation time";
                }
            } else if(args.length == 1) {
                error = "The program didn't receive the minimum and maximum minutes";
            } else {
                error = "The program didn't receive the correct number of arguments";
            }
        } else if (FindCommands.LAST.getCmd().equals(cmd)) {
            if(args.length == 3) {
                try {
                    Long.parseLong(args[2]);
                } catch(Exception e) {
                    error = "The third argument must be an integer representing the number of recipes to be shown";
                }
            } else if(args.length == 2) {
                error = "The program didn't receive the number of recipes";
            } else {
                error = "The program didn't receive the correct number of arguments";
            }
        } else if (FindCommands.LEVEL.getCmd().equals(cmd)) {
            if(args.length == 3) {
                try {
                    DifficultyLevel.valueOf(args[2].toUpperCase());
                } catch(Exception e) {
                    error = "The third argument must represent a difficulty level: " + DifficultyLevel.listOfValues();
                }
            } else if(args.length == 2) {
                error = "The program didn't receive the difficulty level";
            } else {
                error = "The program didn't receive the correct number of arguments";
            }
        } else if (FindCommands.UNDER.getCmd().equals(cmd)) {
            if(args.length == 3) {
                try {
                    Long.parseLong(args[2]);
                } catch(Exception e) {
                    error = "The third argument must be an integer representing the number of minutes of max preparation time";
                }
            } else if(args.length == 2) {
                error = "The program didn't receive the number of minutes";
            } else {
                error = "The program didn't receive the correct number of arguments";
            }
        }

        return error;
    }

    /**
     * Validate if the user entered a valid find command
     * @param cmd Command to validate
     * @return Null if the command is valid, error message otherwise
     */
    private static String validateFindCommand(String cmd) {
        for (FindCommands c : FindCommands.values()) {
            if (c.getCmd().equals(cmd.toLowerCase())) {
                return null;
            }
        }

        return "Invalid find command";
    }
}
