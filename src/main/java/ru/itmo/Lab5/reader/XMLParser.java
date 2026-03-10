package ru.itmo.Lab5.reader;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import ru.itmo.Lab5.dragon.Coordinates;
import ru.itmo.Lab5.dragon.Dragon;
import ru.itmo.Lab5.dragon.DragonCave;
import ru.itmo.Lab5.interfaces.Reader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Reads Dragon objects from an XML file.
 */
public class XMLParser implements Reader {
  private static final String DATE_PATTERN = "dd-MM-yyyy";
  private File file;
  private Set<Long> iDs;

  public XMLParser(String filePath) {
    setFilePath(filePath);
    iDs = new HashSet<Long>();
  }

  @Override
  public PriorityQueue<Dragon> read() {
    PriorityQueue<Dragon> dragons = new PriorityQueue<>();
    iDs.clear();

    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(this.file);
      NodeList dragonNodes = document.getElementsByTagName("dragon");

      for (int i = 0; i < dragonNodes.getLength(); i++) {
        Node node = dragonNodes.item(i);
        Element dragonElement = (Element) node;
        Dragon dragon = parseDragon(dragonElement);

        long id = dragon.getId();
        if (iDs.contains(id)) {
          throw new IllegalArgumentException("Duplicate Dragon id found in XML: " + id);
        }
        iDs.add(id);
        dragons.add(dragon);
      }

      return dragons;
    } catch (Exception e) {
      throw new RuntimeException("Failed to read XML file: " + e.getMessage(), e);
    }
  }

  @Override
  public Set<Long> getIDs() {
    return iDs;
  }

  public void setFilePath(String filePath) {
    if (filePath == null) {
      throw new NullPointerException("filePath is null. Should not be null");
    }
    if (filePath.equals("")) {
      throw new IllegalArgumentException("filePath = " + filePath + ". Should not be empty");
    }

    File file = new File(filePath);
    if (!file.exists())
      throw new IllegalArgumentException("file not found: " + filePath);
    if (!file.isFile())
      throw new IllegalArgumentException("not a file: " + filePath);
    if (!file.canRead())
      throw new IllegalArgumentException("no read permission: " + filePath);

    this.file = file;
  }

  private Dragon parseDragon(Element dragonElement) {
    Dragon.Builder dragonBuilder = new Dragon.Builder();

    String id = getChildText(dragonElement, "id");
    try {
      dragonBuilder.id(Long.parseLong(id));
    } catch (Exception e) {
      throw new IllegalArgumentException("Wrong id: " + id + ". " + e.getMessage());
    }

    String name = getChildText(dragonElement, "name");
    try {
      dragonBuilder.name(name);
    } catch (Exception e) {
      throw new IllegalArgumentException("Wrong name: " + name + ". " + e.getMessage());
    }

    Element coordinates = getChildElement(dragonElement, "coordinates");
    if (coordinates == null) {
      throw new IllegalArgumentException("coordinates cannot be null");
    }
    String x = getChildText(coordinates, "x");
    String y = getChildText(coordinates, "y");
    try {
      dragonBuilder.coordinates(new Coordinates(Long.parseLong(x), Long.parseLong(y)));
    } catch (Exception e) {
      throw new IllegalArgumentException("Wrong coordinate: " + x + " " + y + ". " + e.getMessage());
    }

    String creationDate = getChildText(dragonElement, "creationDate");
    try {
      Date date = parseDate(creationDate);
      dragonBuilder.creationDate(date);
    } catch (Exception e) {
      throw new IllegalArgumentException("Wrong date: " + creationDate + ". " + e.getMessage());
    }

    String age = getChildText(dragonElement, "age");
    if (age.isBlank() || age.isEmpty()) {
      dragonBuilder.age(null);
    } else {
      try {
        dragonBuilder.age(Integer.parseInt(age));
      } catch (Exception e) {
        throw new IllegalArgumentException("Wrong age: " + age + ". " + e.getMessage());
      }
    }

    String wingspan = getChildText(dragonElement, "wingspan");
    try {
      dragonBuilder.wingspan(Double.parseDouble(wingspan));
    } catch (Exception e) {
      throw new IllegalArgumentException("Wrong wingspan: " + wingspan + ". " + e.getMessage());
    }

    String type = getChildText(dragonElement, "type");
    if (type.isBlank() || type.isEmpty()) {
      dragonBuilder.type(null);
    } else {
      try {
        dragonBuilder.type(type);
      } catch (Exception e) {
        throw new IllegalArgumentException("Wrong dragon type: " + type + ". " + e.getMessage());
      }
    }

    String character = getChildText(dragonElement, "character");
    if (character.isBlank() || character.isEmpty()) {
      dragonBuilder.character(null);
    } else {
      try {
        dragonBuilder.character(character);
      } catch (Exception e) {
        throw new IllegalArgumentException("Wrong dragon character: " + character + ". " + e.getMessage());
      }
    }

    Element cave = getChildElement(dragonElement, "cave");
    if (cave == null || cave.getTextContent().isBlank() || cave.getTextContent().isEmpty()) {
      dragonBuilder.cave(null);
    } else {
      String treasures = getChildText(cave, "numberOfTreasures");
      try {
        dragonBuilder.cave(new DragonCave(Double.parseDouble(treasures)));
      } catch (Exception e) {
        throw new IllegalArgumentException("Wrong treasures: " + treasures + ". " + e.getMessage());
      }
    }

    return dragonBuilder.build();
  }

  private String getChildText(Element parent, String tagName) {
    Element child = getChildElement(parent, tagName);
    if (child == null) {
      return null;
    }
    return child.getTextContent();
  }

  private Element getChildElement(Element parent, String tagName) {
    NodeList children = parent.getChildNodes();

    for (int i = 0; i < children.getLength(); i++) {
      Node node = children.item(i);

      if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(tagName)) {
        return (Element) node;
      }
    }

    return null;
  }

  private Date parseDate(String value) throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
    formatter.setLenient(false);
    return formatter.parse(value.trim());
  }

}
