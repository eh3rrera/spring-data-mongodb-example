package com.example.demo.service;

import com.example.demo.util.Commands;
import org.springframework.context.ApplicationContext;

/**
 * Factory class to get a service instance
 */
public class ServiceFactory {

    /**
     * Gets the correct service class according to the give command
     * @param applicationContext Spring application context
     * @param cmdEnum Command
     * @return Service class
     */
    public static RecipeService get(ApplicationContext applicationContext, Commands cmdEnum) {
        RecipeService service = null;

        switch (cmdEnum) {
            case SEARCH:
                service = applicationContext.getBean(SearchRecipeService.class);
                break;
            case FIND:
                service = applicationContext.getBean(FindRecipeService.class);
                break;
            case INSERT:
                service = applicationContext.getBean(InsertRecipeService.class);
                break;
            case MARK:
                service = applicationContext.getBean(MarkRecipeService.class);
                break;
            default:
                throw new RuntimeException("Invalid command received");
        }

        return service;
    }
}
