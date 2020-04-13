package com.example.demo;

import com.example.demo.domain.DifficultyLevel;
import com.example.demo.domain.Ingredient;
import com.example.demo.domain.Recipe;
import com.example.demo.dto.ParamsDto;
import com.example.demo.service.RecipeService;
import com.example.demo.service.ServiceFactory;
import com.example.demo.util.Commands;
import com.example.demo.util.FindCommands;
import com.example.demo.util.ValidationUtils;
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.demo.util.ValidationUtils.*;

public class App {
    private ApplicationContext applicationContext;

    /** Prompt to enter a command */
    private static final String INITIAL_PROMPT = "Enter command: ";

    public App(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void run(String... args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command = "";

        try {
            while(!Commands.EXIT.getCmd().equalsIgnoreCase(command)) {
                System.out.print(INITIAL_PROMPT);
                String line = reader.readLine();
                String[] tokens = line.split(" ");

                if(tokens != null && tokens.length > 0) {
                    command = tokens[0];

                    if(!Commands.EXIT.getCmd().equalsIgnoreCase(command)) {
                        processCommand(tokens);
                    }
                } else {
                    System.out.println("Invalid command, try again.");
                    command = "";
                }
            }
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Process the commands of the application
     * @param args Arguments given to the application
     */
    private void processCommand(String args[]) {
        String error = validateArgs(args);

        if( isEmpty(error) ) {
            Commands command = Commands.valueOf(args[0].toUpperCase());

            if(command.equals(Commands.HELP)) {
                displayHelp(null);
            } else {
                FindCommands findCommand = null;
                if(command.equals(Commands.FIND)) {
                    findCommand = FindCommands.valueOf(args[1].toUpperCase());
                }
                RecipeService service = ServiceFactory.get(applicationContext, command);

                // Get parameters for the service from arguments (or ask for them in the case of inserting)
                ParamsDto paramsDTO = getParameters(command, findCommand, args);
                System.out.println(service.execute(paramsDTO));
            }
        } else {
            displayHelp(error);
        }
    }

    /**
     * Given a command (ex. get), validate the arguments give to the program
     * @param cmdEnum Command
     * @param findCmd Sub-command for the find command
     *  @param args Arguments of the command
     * @return Object with the information (arguments) to be processed
     */
    private ParamsDto getParameters(Commands cmdEnum, FindCommands findCmd, String[] args) {
        ParamsDto paramsDto = new ParamsDto();

        if(cmdEnum == Commands.MARK) {
            paramsDto.setRecipeId(Long.parseLong(args[1]));
        } else if(cmdEnum == Commands.SEARCH) {
            String text = String.join(" ",
                    Arrays.asList(args).subList(1, args.length)
            );
            paramsDto.setTextSearch(text);
        } else if(cmdEnum == Commands.FIND) {
            if(findCmd == FindCommands.ID) {
                paramsDto.setRecipeId(Long.parseLong(args[2]));
            } else if(findCmd == FindCommands.LAST) {
                paramsDto.setLastRecipes(Integer.parseInt(args[2]));
            } else if(findCmd == FindCommands.LEVEL) {
                paramsDto.setDifficultyLevel(DifficultyLevel.valueOf(args[2].toUpperCase()));
            } else if(findCmd == FindCommands.UNDER) {
                paramsDto.setUnderMinutes(Integer.parseInt(args[2]));
            } else if(findCmd == FindCommands.BETWEEN) {
                int n1 = Integer.parseInt(args[2]);
                int n2 = Integer.parseInt(args[3]);

                paramsDto.setMaxMinutes(Math.max(n1, n2));
                paramsDto.setMinMinutes(Math.min(n1, n2));
            }
        } else {
            paramsDto.setRecipe(askForRecipeDetails());
        }

        return paramsDto;
    }

    /**
     * Method that ask for the information to insert an order
     * @return Object with the information to insert an order
     */
    private Recipe askForRecipeDetails() {
        Recipe recipe = new Recipe();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean invalidInput;
        try {
            // Ask for title
            System.out.print("Title: ");
            String line = reader.readLine();
            recipe.setTitle(line);

            // Ask for instructions
            System.out.print("Instructions: ");
            line = reader.readLine();
            recipe.setInstructions(line);

            // Ask for difficulty level
            do {
                System.out.print("Difficulty level (easy, normal, hard): ");
                line = reader.readLine();
                try {
                    recipe.setDifficultyLevel(DifficultyLevel.valueOf(line.toUpperCase()));
                    invalidInput = false;
                } catch(Exception e) {
                    invalidInput = true;
                }
            } while(invalidInput);

            // Ask for preparation time
            do {
                System.out.print("Preparation time (in minutes): ");
                line = reader.readLine();
                try {
                    recipe.setPreparationTimeMin(Integer.parseInt(line));
                    invalidInput = false;
                } catch(Exception e) {
                    invalidInput = true;
                }
            } while(invalidInput);

            System.out.println("Ingredients: ");
            boolean insertMoreIngredients = true;
            List<Ingredient> list = new ArrayList<>();
            do {
                Ingredient ingredient = new Ingredient();

                // Ask for quantity
                do {
                    System.out.print("Quantity (ex. 0.5): ");
                    line = reader.readLine();
                    try {
                        ingredient.setQuantity(Double.parseDouble(line));
                        invalidInput = false;
                    } catch(Exception e) {
                        invalidInput = true;
                    }
                } while(invalidInput);

                // Ask for unit
                System.out.print("Unit (ex. pounds): ");
                line = reader.readLine();
                ingredient.setUnit(line);

                // Ask for description
                System.out.print("Description: ");
                line = reader.readLine();
                ingredient.setUnit(line);

                list.add(ingredient);

                // Ask to insert more ingredients
                do {
                    System.out.print("Insert more ingredients? (y/n): ");
                    line = reader.readLine();
                    if(isEmpty(line)) {
                        invalidInput = true;
                    } else if("y".equalsIgnoreCase(line)) {
                        insertMoreIngredients = true;
                        invalidInput = false;
                    } else if("n".equalsIgnoreCase(line)) {
                        insertMoreIngredients = false;
                        invalidInput = false;
                    } else {
                        invalidInput = true;
                    }
                } while(invalidInput);

            } while(insertMoreIngredients);

            recipe.setIngredients(list);
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }

        return recipe;
    }

    /**
     * Method that display information about how to use the application
     * @param error Error message to display
     */
    private void displayHelp(String error) {
        if( !ValidationUtils.isEmpty(error) ) {
            System.err.println("Error: " + error);
        }
        System.out.println("Usage: " + INITIAL_PROMPT + " <command> [<args>]");
        System.out.println("search          Searches the string passed as argument in the title and instructions of all recipes. Ex: search chicken thighs");
        System.out.println("mark            Marks a recipe as cooked. It takes as argument the ID of the recipe to mark. Ex: mark 1");
        System.out.println("find id         Finds an order by its ID. Ex: find id 1");
        System.out.println("find level      Finds recipes by level of difficulty. Ex: find level easy");
        System.out.println("find under      Finds recipes under n minutes of preparation. Ex: find under 10");
        System.out.println("find between    Finds recipes between x and y minutes of preparation. Ex: find between 5 10");
        System.out.println("find last       Finds the last n recipes. Ex: find last 5");
        System.out.println("insert          Inserts a new recipe. It doesn't take more arguments, however, the application will prompt for all the information to register a new recipe after issuing this command.");
        System.out.println("exit            Exits the application");
        System.out.println("help            Displays usage instructions");
    }
}