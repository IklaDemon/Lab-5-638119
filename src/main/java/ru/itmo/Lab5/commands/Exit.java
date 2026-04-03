package ru.itmo.Lab5.commands;

import java.util.ArrayList;

import ru.itmo.Lab5.interfaces.Command;

public class Exit implements Command {
  public Exit() {
    ;
  }

  @Override
  public String exec(String args) {
    return "exit";
  }

  @Override
  public String usage() {
    return " - exit:\nExits the program without saving";
  }

  @Override
  public int numberOfArgs() {
    return 0;
  }

  @Override
  public boolean requiresDragon() {
    return false;
  }
}
