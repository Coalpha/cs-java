import java.awt.*;
import acm.program.*;
import acm.graphics.*;
import java.awt.event.*;

import java.util.Set;
import java.util.LinkedHashSet;
import java.util.concurrent.ThreadLocalRandom;

class Block extends GRect {
  private static final long serialVersionUID = 0xdeadbeef;
  private static Color[] colors = new Color[] {
    Color.lightGray,
    Color.gray,
    Color.darkGray,
    Color.black
  };

  int arraX;
  int arraY;
  int health = ThreadLocalRandom.current().nextInt(0, 4);

  void updateColor() {
    setFillColor(Block.colors[health]);
  }

  Block() {
    super(0, 0);
    setColor(new Color(0, 0, 0, 0));
    setFilled(true);
    updateColor();
  }

  @Override
  public String toString() {
    return "Block@(" + getX() + ", " + getY() + ") # " + getRightX() + " x " + getBottomY();
  }
}

class Ball extends GOval {
  private static final long serialVersionUID = 0xdeadbeef;

  Ball() {
    super(0, 0, 0, 0);
    setFilled(true);
    setFillColor(Color.pink);
    setColor(new Color(0, 0, 0, 0));
  }

  int velocitX = 2;
  int velocitY = 3;

  int upperX;
  int upperY;

  void setBoundaryBox(int x, int y) {
    // System.out.println("The boundaryBox was set to " + x + ", " + y);
    upperX = x;
    upperY = y;
  }

  void setSize(int i) {
    setSize(i, i);
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

enum XCollide {
  LEFT,
  RIGHT,
}
enum YCollide {
  TOP,
  BOTTOM,
}
interface BallCollision {
  XCollide x = XCollide.LEFT;
  YCollide y = YCollide.TOP;
}
public class Breakout extends GraphicsProgram {
  static final long serialVersionUID = 0xdeadbeef;
  interface config {
    static int countX = 10;
    static int countY = 5;
    static int blockCount = countX * countY;
  }
  static int[] blockAreaWidthBounds(int width) {
    return new int[]{ 0, width };
  }
  static int[] blockAreaHeightBounds(int height) {
    return new int[]{ 0, height / 2 };
  }

  public static void main(String[] a) {
    Breakout b = new Breakout();
    b.setStartupObject(null);
    b.start();
  }

  Thread thread;
  volatile boolean running = false;
  /** in deciseconds */
  static int originalCountDown = 1;
  volatile int countDown = originalCountDown;
  public void init() {
    Breakout that = this;
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        running = false;
        countDown = originalCountDown;
      }
    });
    thread = new Thread(() -> {
      while (true) {
        if (that.running) {
          advanceFrame();
        } else {
          try {
            if (countDown --> 0) {
              Thread.sleep(100);
            } else {
              that.run();
            }
          } catch(Exception e) {}
        }
      }
    });
    thread.start();
  }

  int height;
  int width;
  static class BlockData {
    static int blocksLeft;
    static int[] boundsX;
    static int[] boundsY;
    static int areaWidth;
    static int areaHeight;
    static int blockWidth;
    static int blockHeight;
    static Block[][] blocks = new Block[config.countY][config.countX];
    static void calc(int width, int height) {
      /** 0 is the lower bound. 1 is the upper bound */
      boundsX = blockAreaWidthBounds(width);
      boundsY = blockAreaHeightBounds(height);
      areaWidth = boundsX[1] - boundsX[0];
      areaHeight = boundsY[1] - boundsY[0];
      blockWidth = (areaWidth) / config.countX;
      blockHeight = (areaHeight) / config.countY;
    }
    static Block block(int arraX, int arraY) {
      if (arraX < config.countX && arraY < config.countY) {
        return blocks[arraY][arraX];
      }
      return null;
    }
    static Block getBlockAtPoint(int x, int y) {
      return block(x / blockWidth, y / blockHeight);
    }
    static Block getBlockAtPoint(double x, double y) {
      return getBlockAtPoint((int) x, (int) y);
    }
    static Block getBlockAtPoint(GPoint p) {
      return getBlockAtPoint(p.getX(), p.getY());
    }
    static void remove(Block b) {
      blocks[b.arraY][b.arraX] = null;
    }
  }
  Ball ball = new Ball();
  public void run() {
    clear();

    width = getWidth();
    height = getHeight();

    BlockData.calc(width, height);
    // Annoyingly, this segment can't be refactored into Streams
    // Each block needs to know it's position within the this.blocks matrix
    for (int y = 0; y < config.countY; y++) {
      Block[] row = BlockData.blocks[y];
      for (int x = 0; x < config.countX; x++) {
        Block cell = new Block();
        cell.arraX = x;
        cell.arraY = y;
        row[x] = cell;
        cell.setLocation(
          x * BlockData.blockWidth + BlockData.boundsX[0],
          y * BlockData.blockHeight + BlockData.boundsY[0]
        );
        cell.setSize(
          BlockData.blockWidth,
          BlockData.blockHeight
        );
        add(cell);
      }
    }
    BlockData.blocksLeft = config.blockCount;

    ball.setLocation(height / 1.3, width / 2);
    ball.setBoundaryBox(width, height);
    ball.setSize(WIDTH, HEIGHT);
    add(ball);
    running = true;
  }

  void advanceFrame() {
    ball.scram();
    pause(15);
    Block atCenter = BlockData.getBlockAtPoint(ball.getLocation());
    if (atCenter != null) {
      remove(atCenter);
    }
  }

  void doCollide() {
    Set<Block> blocks = new LinkedHashSet<>();
    blocks.add(BlockData.getBlockAtPoint(ball.getLocation()));
    blocks.add(BlockData.getBlockAtPoint(ball.getRightX(), ball.getY()));
    blocks.add(BlockData.getBlockAtPoint(ball.getX(), ball.getBottomY()));
    blocks.add(BlockData.getBlockAtPoint(ball.getRightX(), ball.getBottomY()));
    // Objects::nonNull
  }

  void bean(Block b) {
    b.health--;
    if (b.health < 0) {
      remove(b);
      BlockData.blocks[b.arraY][b.arraX] = null;
      BlockData.blocksLeft--;
    } else {
      b.updateColor();
    }
    isGameOver();
  }

  void isGameOver() {
  }
}
