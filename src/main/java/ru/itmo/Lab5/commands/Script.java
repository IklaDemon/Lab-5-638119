package ru.itmo.Lab5.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Script {
  private File scriptFilePath;
  private Set<String> history;
  private ArrayList<String> commands;

  public Script() {
    this.history = new HashSet<String>();
    this.commands = new ArrayList<String>();
  }

  public void load(String scriptPath) {
    this.scriptFilePath = this.getFile(scriptPath);
  }

  public ArrayList<String> getCommands() {
    commands.clear();
    history.clear();

    if (scriptFilePath != null) {
      try {
        history.add(scriptFilePath.getCanonicalPath());
        this.readScript(scriptFilePath);
      } catch (IOException e) {
        throw new IllegalArgumentException("Error while getting canonical path of: " + scriptFilePath.getName());
      }
    }
    return new ArrayList<>(this.commands);
  }

  private void readScript(File script) {
    String canonicalPath;
    try {
      canonicalPath = script.getCanonicalPath();
    } catch (IOException e) {
      throw new IllegalArgumentException("Error while getting canonical path of: " + script.getName());
    }
    try (Scanner scanner = new Scanner(script)) {
      while (scanner.hasNextLine()) {
        String command = scanner.nextLine().strip();
        String[] commandParts = command.split("\\s+");

        if (commandParts.length == 2 && commandParts[0].equals("executes")) {
          try {
            File nextScript = this.getFile(new File(script.getParentFile(), commandParts[1]).getAbsolutePath());
            if (this.history.add(nextScript.getCanonicalPath()) == true)
              this.readScript(nextScript);
            else {
              System.out.print("Loop detected with instruction: " + command + "\n");
            }
          } catch (IOException | IllegalArgumentException | NullPointerException e) {
            System.out.print("Failed to open script in command: " + command + ". " + e.getMessage() + "\n");
          }
        } else {
          this.commands.add(command);
        }
      }
    } catch (FileNotFoundException e) {
      System.out.print("File " + script.getName() + " not found.\n");
    } finally {
      history.remove(canonicalPath);
    }
  }

  private File getFile(String filePath) {
    if (filePath == null) {
      throw new NullPointerException("filePath is null. Should not be null");
    }
    if (filePath.equals("")) {
      throw new IllegalArgumentException("filePath = " + filePath + ". Should not be empty");
    }
    File file = new File(filePath);
    if (file.exists() && !file.isDirectory()) {
      if (!file.canRead()) {
        throw new IllegalArgumentException("file is not readable");
      }
      return file;
    } else {
      throw new IllegalArgumentException("filePath does not exists or is a directory");
    }
  }
}
