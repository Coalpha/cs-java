import acm.graphics.*;
import acm.program.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.ThreadLocalRandom;

class Block extends GRect {
  private static final long serialVersionUID = 0xdeadbeef;
  private static Color[] colors = new Color[] { Color.lightGray, Color.gray, Color.darkGray, Color.black };

  int arraX;
  int arraY;
  int health = ThreadLocalRandom.current().nextInt(0, 4);

  void updateColor() {
    setFillColor(Block.colors[health]);
  }

  Block() {
    super(0, 0);
    setFilled(true);
    updateColor();
  }

  @Override
  public String toString() {
    return "Block";
  }
}

class Ball extends GOval {
  private static final long serialVersionUID = 0xdeadbeef;

  Ball() {
    super(0, 0, 40, 40);
    System.out.println("Made a ball");
    setFilled(true);
    setFillColor(Color.pink);
  }

  int velocitX = 2;
  int velocitY = 3;

  int upperX;
  int upperY;

  void setBoundaryBox(int x, int y) {
    System.out.println("The boundaryBox was set to " + x + ", " + y);
    upperX = x;
    upperY = y;
  }

  boolean hitTop() {
    return getY() < 0;
  }

  boolean hitLeft() {
    return getX() < 0;
  }

  boolean hitBottom() {
    return getBottomY() > upperY;
  }

  boolean hitRight() {
    return getRightX() > upperX;
  }

  void scram() {
    move(velocitX, velocitY);
    if (hitTop()) {
      velocitY = Math.abs(velocitY);
    } else if (hitBottom()) {
      velocitY = Math.abs(velocitY) * -1;
    }
    if (hitLeft()) {
      velocitX = Math.abs(velocitX);
    } else if (hitRight()) {
      velocitX = Math.abs(velocitX) * -1;
    }
  }
}

public class Breakout extends GraphicsProgram {
  static final long serialVersionUID = 0xdeadbeef;
  static int blocksPerRow = 10;
  static int blocksPerCol = 5;

  public static void main(String[] a) {
    Breakout b = new Breakout();
    b.setStartupObject(null);
    b.start();
  }

  // Instance
  volatile boolean running = false;

  int height;
  int width;
  int blocksLeft;
  Block[][] blocks = new Block[blocksPerCol][blocksPerRow];
  Ball ball = new Ball();
  long lastRunCall;
  public void init() {
    height = getHeight();
    width = getWidth();
    ball.setLocation(height / 1.3, width / 2);
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        if (System.currentTimeMillis() > lastRunCall + 100) {
          run(); // Now that's how you do a closure!
        };
      }
    });
    blocksLeft = blocksPerRow * blocksPerCol;
    for (int y = 0; y < blocksPerCol; y++) {
      Block[] row = blocks[y];
      for (int x = 0; x < blocksPerRow; x++) {
        Block chain = new Block();
        chain.arraX = x;
        chain.arraY = y;
        row[x] = chain;
      }
    }
    new Thread(() -> {
      while(true) {
        if (running) {
          ball.scram();
          pause(20);
        }
      }
    }).start();
  }

  public void run() {
    running = false;
    clear();
    height = getHeight();
    width = getWidth();
    System.out.println("Height: " + height + " Width: " + width);
    int blockAreaHeight = height / 2;
    int blockWidth = width / blocksPerRow;
    int blockHeight = blockAreaHeight / blocksPerCol;
    for (int y = 0; y < blocksPerCol; y++) {
      Block[] row = blocks[y];
      for (int x = 0; x < blocksPerRow; x++) {
        Block current = row[x];
        if (current != null) {
          current.setLocation(x * blockWidth, y * blockHeight);
          current.setSize(blockWidth, blockHeight);
          add(current);
        }
      }
    }
    ball.setBoundaryBox(width, height);
    add(ball);
    lastRunCall = System.currentTimeMillis();
    running = true;
  }

  public void mousePressed(MouseEvent e) {
    GObject gob = getElementAt(e.getX(), e.getY());
    if (gob != null && gob.toString() == "Block") {
      bean((Block) gob);
    }
  }

  void bean(Block b) {
    b.health--;
    if (b.health < 0) {
      remove(b);
      blocks[b.arraY][b.arraX] = null;
      blocksLeft--;
    } else {
      b.updateColor();
    }
    isGameOver();
  }

  void isGameOver() {
  }
}
