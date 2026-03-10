package ru.itmo.Lab5;

import ru.itmo.Lab5.manager.CollectionManager;
import ru.itmo.Lab5.reader.XMLParser;
import ru.itmo.Lab5.userInput.UserInputHandler;
import ru.itmo.Lab5.writer.XMLWriter;

/**
 * Entry point of the application
 * Checks the environment variable
 * Allocates the Collection Manager with a writer and reader for the file
 */
public class App {
  public static void main(String[] args) {
    try {
      String env = System.getenv("path_to_xml");
      if (env != null && !env.isBlank()) {
        XMLParser xmlParser = new XMLParser(env);
        XMLWriter xmlWriter = new XMLWriter(env);
        CollectionManager collectionManager = new CollectionManager(xmlParser, xmlWriter, env);

        // getting the commands from the user...
        UserInputHandler userInputHandler = new UserInputHandler();
        userInputHandler.readUserInput(collectionManager);

      } else {
        System.out.println("No environment variable set");
        System.out.println("Please set 'export path_to_xml=[path_to_xml]' on Linux Bash/Fish.");
        System.out.println("Please set 'set path_to_xml=[path_to_xml]'    on Windows CMD.");
        System.out.println("Please set '$env:path_to_xml=[path_to_xml]'   on Windows PowerShell.");
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.getMessage());
    }
  }
}
