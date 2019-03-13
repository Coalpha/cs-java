import java.awt.Point;
import java.lang.Runnable;
import java.util.ArrayList;
import java.io.IOException;
import java.util.function.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.InterruptedException;
// mfw you have to import all the things

enum CardinalDirection {
  North(0b00),
  East(0b00),
  South(0b10),
  West(0b01);

  byte value;
  private CardinalDirection(int value) {
    this.value = (byte) value;
  }
  // if you don't understand this
  // learn what enums are
  // otherwise, you'll feel like an enumskull
  // xD that's the first bad pun
}
// Some of these puns will make your head go enum
// Don't worry, it's not gonna git better. So checkout these puns.
// One could say that it all went south from here on out.
// it's a cardinal sin not to include them though
// it's one of my many methods to making this file more painful
class Direction {
  static CardinalDirection getInverse(CardinalDirection direction) {
    switch (direction) {
      case North:
        return CardinalDirection.South;
      case East:
        return CardinalDirection.West;
      case South:
        return CardinalDirection.North;
      case West:
        return CardinalDirection.East;
    }
    System.exit(1);
    return CardinalDirection.North;
  }
  CardinalDirection cardinal = CardinalDirection.North;
  // this is a variable
  // it holds the enum
  // feeling pain from these comments yet?
  Point getRelativeUnitPoint() {
    // don't worry. That's the Point.
    // this'll just return points that are one unit from the origin
    // remember that the terminal is the typical cartesian coordinate system
    // except that all points are reflected across the x-axis 
    switch (this.cardinal) {
      case North:
        return new Point(0, -1);
      case East:
        return new Point(1, 0);
      case South:
        return new Point(0, 1);
      case West:
        return new Point(-1, 0);
      default:
        System.out.println("Something went wrong in Direction.getRelativeUnitPoint()");
        // give some useful output
    }
    System.exit(1);
    // exit the program
    // something really really went wrong
    return new Point(0, 0);
    // the compiler was complaining so whatever
    // it should never get to this statment.
    // EVER.
  }
  void setDirection(CardinalDirection direction) {
    this.cardinal = direction;
    // it's literally the exact same code as the constructor
  }
  Boolean isAxisY() {
    return (this.cardinal == CardinalDirection.North ||
      this.cardinal == CardinalDirection.South);
  }
}
class Curses {
  // curses! haha, I'm going to use the hell out of that pun
  // it moves the cursor
  private static void castCurse(String str) {
    // this method actually formats and prints the control characters
    System.out.print(String.format("\033" + str));
  }
  private static Runnable curse(final String str) {
    // final because closure
    Runnable curseYou = () -> castCurse(str);
    return curseYou;
    // class Perry extends Platypus {
    //   Perry() {
    //     super.doThing();
    //   } 
    // }
    // Perry Perry_The_Platypus = new Perry();
    // curseYou.run(Perry_The_Platypus);
  }
  static Runnable clear = curse("[2J");
  // control characters for clearing the console
  static Runnable cleareol = curse("[K");
  static Runnable home = curse("[H");
  // set the cursor to the top left of the console
  static Consumer<Point> cursePos = (Point p) -> {
    castCurse("[" + (int) p.getY() + ";" + (int) p.getX() + "H");
    // grab the coordinates from the input Point p and stringify them
    // then concatinate and print to stdout
  };
}
class Repeat {
  String out = "";
  Repeat(char c, int n) {
    // this method takes Characters
    while (n--> 0) {
      // while n goes to 0
      this.out += c;
      // add the character you want to repeat to the return string
    }
  }
  Repeat(String s, int n) {
    // this method takes Strings
    while (n--> 0) {
      this.out += s;
    }
  }
  Repeat(Runnable r, int n) {
    // this method takes Runnables
    while (n--> 0) {
      r.run();
    }
  }
  // woah, they're all the same method name??!?!
  // how does that even compile??!?!?!?!??DS?SAD?SAD?AS:DAKLJSD "JAS IOADOUI JIOAD ASD ASVI
  // the compiler knows the difference between all of these
  // the JVM also knows which one to call based on what type(s) is/are being used
}
interface TestFunction<T> {
  Boolean test(T val);
}
interface BinaryCurriedFunction<T, U, R> {
  Function<U, R> apply(T val);
}
class NormalArray<T> extends ArrayList<T> {
  // then I can stick my own methods in here
  final int serialVersionUID;
  void fiveEach(Consumer<T> fn) {
    // it's called fiveEach because forEach goes forward
    // this function iterates through the Array backwards
    int i = this.size();
    while (i--> 0) {
      fn.accept(this.get(i));
      // run the Consumer.
      // As a side note,
      // I find it very disappointing that the way to run the function
      // is Consumer.accept instead of Consumer.eat. Whatever. 
    }
  }
  Boolean containsFn(TestFunction<T> fn) {
    // this method iterates though the Array
    // if it finds something that satisfies the TestFunction
    // contains returns true
    int i = this.size();
    while (i--> 0) {
      if (fn.test(this.get(i))) {
        return true;
      }
    }
    return false;
  }
}
class GameBoard {
  int sizeX;
  int sizeY;
  // these need to be here since the entire class accesses them
  private static class Borders {
    static char topLeft = '┌';
    static char topRight = '┐';
    static char bottomLeft = '└';
    static char bottomRight = '┘';
    static char horizontal = '─';
    static char vertical = '│';
    // different pieces of the border
  }
  // now we actually need a Borders object
  char floor = '.';
  // the floor character
  private NormalArray<String> rows = new NormalArray();
  private Runnable makeRow(final char left, final char center, final char right) {
    // these variables need to be final since we're gonna use them in a closure
    Runnable make = () -> {
      String centerString = new Repeat(center, this.sizeX).out;
      this.rows.add(left + centerString + right);
      // concatenate the strings and then add them to the list of rows
    };
    // make the runnable Runnable called make
    return make;
    // return the runnable Runnable called make
  };
  private Runnable makeTop = makeRow(
    GameBoard.Borders.topLeft,
    GameBoard.Borders.horizontal,
    GameBoard.Borders.topRight
  );
  private Runnable makeMiddle = makeRow(
    GameBoard.Borders.vertical,
    this.floor,
    GameBoard.Borders.vertical
  );
  private Runnable makeBottom = makeRow(
    GameBoard.Borders.bottomLeft,
    GameBoard.Borders.horizontal,
    GameBoard.Borders.bottomRight
  );
  // yep, makeRow is amazing
  GameBoard(int sizeX, int sizeY) {
    this.sizeX = sizeX;
    this.sizeY = sizeY;
    // remember those sizeX and sizeY variables that are at
    // the top of this class?
    // now we're setting them to the arguments that we recieved from
    // the constructor call
    this.makeTop.run();
    // make the top of the box
    new Repeat(this.makeMiddle, sizeY - 2);
    // calling repeat with a Runnable repeats the runnable n times
    // in this case, n is sizeY - 2.
    // why sizeY - 2 you ask?
    // well that's because the top and bottom exist
    this.makeBottom.run();
    // make the bottom of the class
  }
  private Consumer<String> println = (String str) -> System.out.println(str);
  // rows.forEach takes a Consumer so we need to wrap System.out.println
  // in a consumer in order for it to work properly
  void render() {
    Curses.clear.run();
    // that clears the terminal
    this.rows.forEach(this.println);
    // print each of the rows out
  }
  Boolean isAtEdge(Point p) {
    return (p.getX() - 1) % (sizeX + 1) == 0 || (p.getY() - 1) % (sizeY - 1) == 0;
  }
  BiConsumer<Point, Character> placeChar = (Point p, Character c) -> {
    Curses.cursePos.accept(p);
    System.out.print(c);
  };
}
class Snake {
  private static class Body {
    static char horizontal = '█';
    // originally ■
    static char vertical = '█';
  }
  private char deadHead = 'X';
  private int length = 3;
  private Direction direction = new Direction();
  private BiConsumer<Point, Character> placeChar;

  NormalArray<Point> bodyPoints = new NormalArray();
  Point pos = new Point(2, 2);
  Snake(BiConsumer<Point, Character> placeChar) {
    // since I might want to use Snake in the future
    // I'm having it take a BiConsumer for placing characters in
    // the console so that I can change the implimentation of placeChar
    // without my Snake class breaking. Extensability for the win.
    this.direction.setDirection(CardinalDirection.South);
    this.placeChar = placeChar;
  }
  void charHere(char c) {
    this.placeChar.accept(this.pos, c);
    // THIS function takes a character to place at the Snake's current position
  }
  void bodyCharHere() {
    if (this.direction.isAxisY()) {
      this.charHere(Snake.Body.vertical);
    } else {
      this.charHere(Snake.Body.horizontal);
    }
    // this might seem kind of dumb right now but before,
    // I had different characters for the vertical and horizontal Snake body
  }
  void makeTrail() {
    // make trail is called before the Snake's position is updated within move
    this.bodyPoints.add(this.pos);
    this.bodyCharHere(); 
  }
  void deleteTail(char replacement) {
    // this is the part that removes the trail left behind
    this.placeChar.accept(this.bodyPoints.get(0), replacement);
    this.bodyPoints.remove(0);
  }
  void move() {
    this.makeTrail();
    Point delta = this.direction.getRelativeUnitPoint();
    this.pos = new Point(
      (int) this.pos.getX() + (int) delta.getX(),
      (int) this.pos.getY() + (int) delta.getY()
    );
    this.bodyCharHere();
  }
  Boolean turn(CardinalDirection direction) {
    CardinalDirection current = this.direction.cardinal;
    if (current != direction && current != Direction.getInverse(direction)) {
      // The mechanics behind Snake dictate that you can't do a 180
      this.direction.setDirection(direction);
      return true;
    }
    return false;
  }
  Boolean isCannibal() {
    // basically "did you run into yourself"
    if (bodyPoints.contains(this.pos)) {
      this.charHere(this.deadHead);
      return true;
    }
    return false;
  }
}
class KeyLogger {
  private BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  // an alternative to Scanner
  // I can change the implimentation if I feel like it
  String takeInput() throws IOException {
    return this.in.readLine();
  }
}
class Foond {
  private static char symbol = '%';
  private static Point rand0(GameBoard gameBoard) {
    // rand0 gets a random point within the walls of the gameBoard provided
    int x = (int) Math.round(Math.random() * (gameBoard.sizeX - 3)) + 2;
    int y = (int) Math.round(Math.random() * (gameBoard.sizeY - 3)) + 2;
    // believe me, I don't know what those - 3s and + 2s do either
    return new Point(x, y);
  }
  static Point pos;
  private static TestFunction<Point> foondPosEqualsPoint = (Point p) -> pos.equals(p);
  static void placeFoond(GameBoard gameBoard, Point headPos, NormalArray<Point> bodyPoints) {
    pos = rand0(gameBoard);
    while (pos.equals(headPos) || bodyPoints.containsFn(foondPosEqualsPoint)) {
      pos = rand0(gameBoard);
    }
  }
  static void render(BiConsumer<Point, Character> placeChar) {
    placeChar.accept(pos, symbol);
  }
}
class SnakeGame {
  private static int sizeY = 20;
  private static int sizeX = sizeY * 2;
  private static GameBoard gameBoard;
  private static Snake snake;
  private static KeyLogger k;
  private static Boolean lost = false;
  // prep the vars
  // preheat the main method to 275 degrees...
  // celcius
  // it's about to get hot because here's where the action ramps up
  private static void quit() {
    System.out.println("Yer bad");
    System.exit(0);
  }
  private static void gameOver() throws IOException {
    lost = true;
    while (true) {
      Curses.clear.run();
      System.out.print("Hah, you lost\nYou know you want to play again [y/n]\n");
      String response = k.takeInput();
      if (response.equalsIgnoreCase("y") || response.equalsIgnoreCase("yes")) {
        lost = false;
        init();
      } else if (response.equalsIgnoreCase("n") || response.equalsIgnoreCase("no")) {
        quit();
      }
      // otherwise you've entered in something that's not y/yes or n/no
    }
  }
  private static void defaultCursorPosition() {
    Curses.cursePos.accept(new Point(0, gameBoard.sizeY + 2));
  }
  private static void moveSnake() throws IOException {
    snake.move();
    if (gameBoard.isAtEdge(snake.pos) || snake.isCannibal()) {
      gameOver();
    }
    if (snake.pos.equals(Foond.pos)) {
      placeFoond();
    } else {
      snake.deleteTail(gameBoard.floor);
    }
  }
  private static void resetCursor() {
    defaultCursorPosition();
    Curses.cleareol.run();
  }
  private static void handleKeys() throws IOException {
    String str = k.takeInput();
    if (str.equalsIgnoreCase("quit") || str.equalsIgnoreCase("q")) {
      quit();
    } else if (str.equalsIgnoreCase("w")) {
      snake.turn(CardinalDirection.North);
    } else if (str.equalsIgnoreCase("d")) {
      snake.turn(CardinalDirection.East);
    } else if (str.equalsIgnoreCase("s")) {
      snake.turn(CardinalDirection.South);
    } else if (str.equalsIgnoreCase("a")) {
      snake.turn(CardinalDirection.West);
    }
    moveSnake();
    resetCursor();
  };
  private static void placeFoond() {
    Foond.placeFoond(gameBoard, snake.pos, snake.bodyPoints);
    Foond.render(gameBoard.placeChar);
  }
  public static void main(String[] args) throws InterruptedException, IOException {
    k = new KeyLogger();
    init();
  }
  private static void init() throws IOException {
    // this function makes sense when you remember that
    // sometimes you want to play more than one game
    // init pretty cool?
    gameBoard = new GameBoard(sizeX, sizeY);
    gameBoard.render();
    snake = new Snake(gameBoard.placeChar);
    snake.move();
    snake.move();
    snake.move();
    placeFoond();
    resetCursor();
    while (!lost) {
      handleKeys();
    }
  }
}
