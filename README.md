# Spring Data Mongodb Example

Command-line app to manage short recipes using an embedded MongoDB database and Spring Data.

## Requirements
The app works with Maven and Java 8+, however, it's configured to work with Java 13 by default. If you have another version, modify the `maven.compiler.release` property in the pom.xml file.

## Usage
Either import the project into an IDE and run the class `com.example.demo.DemoApplication` or in a Terminal window, execute the command `mvn spring-boot:run`.

The application accepts the following commands:
- `search`          Searches the string passed as argument in the title and instructions of all recipes. Ex: `search chicken thighs`
- `mark`            Marks a recipe as cooked. It takes as argument the ID of the recipe to mark. Ex: `mark 1`
- `find id`         Finds an order by its ID. Ex: `find id 1`
- `find level`      Finds recipes by level of difficulty. Ex: `find level easy`
- `find under`      Finds recipes under n minutes of preparation. Ex: `find under 10`
- `find between`    Finds recipes between x and y minutes of preparation. Ex: `find between 5 10`
- `find last`       Finds the last n recipes. Ex: `find last 5`
- `insert`          Inserts a new recipe. It doesn't take more arguments, however, the application will prompt for all the information to register a new recipe after issuing this command.
- `exit`            Exits the application
- `help`            Displays usage instructions