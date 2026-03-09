package ru.itmo.Lab5.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import ru.itmo.Lab5.interfaces.Reader;

/**
 * Reads Dragon objects from an XML file.
 * This class is not used.
 * Is my first attempt with a reader
 */
public class XMLReader implements Reader {
  private File file;
  private Set<Long> iDs;

  // constructor
  public XMLReader(String filePath) {
    setFilePath(filePath);
    iDs = new HashSet<Long>();
  }

  @Override
  public PriorityQueue<Dragon> read() {
    PriorityQueue<Dragon> pq = new PriorityQueue<Dragon>();
    String line;
    int lineNumber = 0;
    String attrb;

    try (Scanner scanner = new Scanner(file)) {

      while (scanner.hasNextLine()) {
        line = scanner.nextLine();
        lineNumber++;

        if (line.contains("<dragon>") && !line.contains("</dragon>")) {

          Dragon.Builder builder = new Dragon.Builder();

          while (scanner.hasNextLine()) {
            attrb = scanner.nextLine();
            lineNumber++;
            if (attrb.contains("</dragon>")) {
              break;
            }
            if (attrb.contains("<id>")) {
              String id = attrb.replace("<id>", "").replace("</id>", "").trim();
              // System.out.println("id: " + id);
              try {
                Long lID = Long.parseLong(id);
                if (!iDs.add(lID)) {
                  throw new IllegalArgumentException("Error on line " + lineNumber + ", duplicate id: " + lID);
                }
                builder.id(lID);
              } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber + ", NaN");
              } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber);
              }
            } else if (attrb.contains("<name>")) {
              String name = attrb.replace("<name>", "").replace("</name>", "").trim();
              // System.out.println("name: " + name);
              try {
                builder.name(name);
              } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber);
              } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber);
              }
            } else if (attrb.contains("<coordinates>")) {
              // System.out.println("coordinates");
              String x;
              if (scanner.hasNextLine())
                x = scanner.nextLine();
              else
                throw new IllegalArgumentException("File incomplete");
              lineNumber++;
              if (!x.contains("<x>")) {
                throw new IllegalArgumentException("Error on line " + lineNumber + ", no coordinate x");
              }
              x = x.replace("<x>", "").replace("</x>", "").trim();
              long long_x;
              try {
                long_x = Long.parseLong(x);
              } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber + ", NaN");
              }
              // System.out.println("x: " + x);
              String y;
              if (scanner.hasNextLine())
                y = scanner.nextLine();
              else
                throw new IllegalArgumentException("File incomplete");
              lineNumber++;
              if (!y.contains("<y>")) {
                throw new IllegalArgumentException("Error on line " + lineNumber + ", no coordinate y");
              }
              y = y.replace("<y>", "").replace("</y>", "").trim();
              Long long_y;
              try {
                long_y = Long.parseLong(y);
              } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber + ", NaN");
              }
              // System.out.println("y: " + y);

              builder.coordinates(new Coordinates(long_x, long_y));

            } else if (attrb.contains("<creationDate>")) {
              String str_creationDate = attrb.replace("<creationDate>", "").replace("</creationDate>", "").trim();
              // System.out.println("creationDate: " + str_creationDate);

              Date creationDate;
              SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
              formatter.setLenient(false);
              try {
                creationDate = formatter.parse(str_creationDate);
              } catch (ParseException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber);
              }

              builder.creationDate(creationDate);

            } else if (attrb.contains("<age>")) { // age can be null
              String age = attrb.replace("<age>", "").replace("</age>", "").trim();
              // System.out.println("age: " + age);

              if (age.equals("null") || age.equals("")) {
                builder.age(null);
              } else {
                try {
                  builder.age(Integer.parseInt(age));
                } catch (NumberFormatException e) {
                  System.out.println(e.getMessage());
                  throw new IllegalArgumentException("Error on line " + lineNumber + ", NaN");
                } catch (IllegalArgumentException e) {
                  System.out.println(e.getMessage());
                  throw new IllegalArgumentException("Error on line " + lineNumber);
                }
              }

            } else if (attrb.contains("<wingspan>")) {
              String wingSpan = attrb.replace("<wingspan>", "").replace("</wingspan>", "").trim();
              // System.out.println("wingspan: " + wingSpan);

              try {
                builder.wingspan(Double.parseDouble(wingSpan));
              } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber + ", NaN");
              } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber);
              }

            } else if (attrb.contains("<type>")) { // type can be null
              String type = attrb.replace("<type>", "").replace("</type>", "").trim();
              // System.out.println("type: " + type);

              if (type.equals("null") || type.equals("")) {
                builder.type(null);
              } else {
                try {
                  builder.type(type);
                } catch (IllegalArgumentException e) {
                  System.out.println(e.getMessage());
                  throw new IllegalArgumentException("Error on line " + lineNumber);
                }
              }

            } else if (attrb.contains("<character>")) { // character can be null
              String character = attrb.replace("<character>", "").replace("</character>", "").trim();
              // System.out.println("character: " + character);

              if (character.equals("null") || character.equals("")) {
                builder.character(null);
              } else {
                try {
                  builder.character(character);
                } catch (IllegalArgumentException e) {
                  System.out.println(e.getMessage());
                  throw new IllegalArgumentException("Error on line " + lineNumber);
                }
              }

            } else if (attrb.contains("<cave>")) { // cave can be null
              if (attrb.contains("</cave>")) {
                builder.cave(null);
                continue;
              }
              // System.out.println("cave");
              if (!scanner.hasNextLine())
                throw new IllegalArgumentException("Error on line: " + lineNumber + ", End of file.");
              String numberOfTreasures = scanner.nextLine().replace("<numberOfTreasures>", "")
                  .replace("</numberOfTreasures>", "").trim();
              lineNumber++;
              // System.out.println("numberOfTreasures: " + numberOfTreasures);

              DragonCave cave;

              try {
                cave = new DragonCave(Double.parseDouble(numberOfTreasures));
                builder.cave(cave);
              } catch (NullPointerException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber);
              } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                throw new IllegalArgumentException("Error on line " + lineNumber);
              }

            }
          }

          Dragon dragon = builder.build();
          pq.add(dragon);
        }
      }
      if (pq.isEmpty()) {
        throw new IllegalArgumentException("No dragons");
      }
    } catch (FileNotFoundException e) {
      System.out.println("File not found");
      throw new IllegalArgumentException(
          "There's a problem with the file, it was not found.\nCode should never reach this point...");
    }

    return pq;
  }

  @Override
  public Set<Long> getIDs() {
    return iDs;
  }

  // chatGPT:
  // In Java, checking permissions isn't 100% reliable.
  // Permissions can change between checking and opening.
  // On some filesystems, canWrite() may return true, but then opening fails.
  // Windows/Linux handle permissions differently.
  //
  // final check should be opening the file and handling the IOException if any...

  public void setFilePath(String filePath) {
    if (filePath == null) {
      throw new NullPointerException("filePath is null. Should not be null");
    }
    if (filePath.equals("")) {
      throw new IllegalArgumentException("filePath = " + filePath + ". Should not be empty");
    }

    File file = new File(filePath);
    if (!file.exists())
      throw new IllegalArgumentException("file not found");
    if (!file.isFile())
      throw new IllegalArgumentException("not a file");
    if (!file.canRead())
      throw new IllegalArgumentException("no read permission");

    this.file = file;
  }
}
