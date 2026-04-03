package ru.itmo.Lab5.commands;

import java.util.ArrayList;

import ru.itmo.Lab5.interfaces.Command;

public class Blank implements Command {

  public Blank() {
    ;
  }

  @Override
  public String exec(String args) {
    return "";
  }

  @Override
  public String usage() {
    return " - '':\nBlank input (just white spaces) will be ignored.";
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
