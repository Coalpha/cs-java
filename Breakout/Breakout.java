import java.awt.*;
import acm.program.*;
import acm.graphics.*;
import java.awt.event.*;
// import java.awt.geom.Point2D;
import java.util.function.*;

import java.util.Set;
import java.util.List;
import java.awt.Point;
import java.util.Objects;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.concurrent.ThreadLocalRandom;

interface Settings {
  final int speed = 15;
  final int paddleHeight = 10;
  final Color paddleColor = Color.red;
  final Color paddleHitColor = Color.magenta;
  final int[] velocity = { 0, -2 };
  final boolean collisionDots = false;
}

class Blonk extends GRect {
  private static final long serialVersionUID = 0xdeadbeef;

  Blonk() {
    super(0, 0);
    setColor(new Color(0, 0, 0, 0));
    setFilled(true);
  }

  static int clamp(double a, double x, double b) {
    if (x < a) {
      return (int) a;
    }
    if (x > b) {
      return (int) b;
    }
    return (int) x;
  }

  Point closestPointTo(Point p) {
    return new Point(clamp(getX(), p.getX(), getRightX()), clamp(getY(), p.getY(), getBottomY()));
  }

  @Override
  public String toString() {
    return "Blonk@(" + getX() + ", " + getY() + ") # " + getRightX() + " x " + getBottomY();
  }
}

class Paddle extends Blonk {
  private static final long serialVersionUID = 0xdeadbeef;
  static int[][] bounceAngles = {
      { -5, -2 }
    , { -4, -2 }
    , { -4, -3 }
    , { -3, -4 }
    , { -1, -5 }

    , {  0, -5 }

    , { +1, -5 }
    , { +3, -4 }
    , { +4, -3 }
    , { +4, -2 }
    , { +5, -2 }
  };
  int[] getBounceAngle(Point clamped) {
    double dxfromStart = clamped.getX() - getX();
    double percentInside = dxfromStart / getWidth();
    return Paddle.bounceAngles[(int) (percentInside * (bounceAngles.length - 1))];
  }
  void gotHit() {
    setColor(Settings.paddleHitColor);
  }
  void normalColor() {
    setColor(Settings.paddleColor);
  }
}

class Block extends Blonk {
  private static final long serialVersionUID = 0xdeadbeef;
  private static Color[] colors = new Color[] { Color.lightGray, Color.gray, Color.darkGray, Color.black };

  int arraX;
  int arraY;
  int health = ThreadLocalRandom.current().nextInt(0, 4);

  Color transparent = new Color(0, 0, 0, 0);

  void updateColor() {
    if (isDead()) {
      setFillColor(transparent);
    } else {
      setFillColor(Block.colors[health]);
    }
  }

  void clearColor() {
    setColor(transparent);
  }

  Block() {
    super();
    updateColor();
  }

  // @Override
  // public void setSize(double arg0, double arg1) {
  // super.setSize(arg0, arg1);
  // }

  double hw;

  double areay(Point p) {
    return getHeight() / getWidth() * Math.abs(p.getX() - getCenterX());
  }

  double areax(Point p) {
    return getWidth() / getHeight() * Math.abs(p.getY() - getCenterY());
  }

  /** assumes that it's within the bounds of the rectangle */
  boolean pointIsInsideBottom(Point p) {
    return (areay(p) + getCenterY()) <= p.getY();
  }

  boolean pointIsInsideTop(Point p) {
    return p.getY() <= (getCenterY() - areay(p));
  }

  byte pointInsideY(Point p) {
    if (pointIsInsideTop(p)) {
      return -1;
    }
    if (pointIsInsideBottom(p)) {
      return 1;
    }
    return 0;
  }

  boolean pointIsInsideLeft(Point p) {
    return p.getX() <= (getCenterX() - areax(p));
  }

  boolean pointIsInsideRight(Point p) {
    return (areax(p) + getCenterX()) <= p.getX();
  }

  byte pointInsideX(Point p) {
    if (pointIsInsideLeft(p)) {
      return -1;
    }
    if (pointIsInsideBottom(p)) {
      return 1;
    }
    return 0;
  }

  void damage() {
    health--;
    updateColor();
  }

  boolean isDead() {
    return health < 0;
  }
}

class DisplayPoint extends GOval {
  private static final long serialVersionUID = 0xdeadbeef;

  DisplayPoint(Point p, int r) {
    super(p.getX() - r / 2, p.getY() - r / 2, r, r);
    setFilled(true);
    setColor(new Color(0, 0, 0, 0));
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

  int velocitX = Settings.velocity[0];
  int velocitY = Settings.velocity[1];

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

  void velocityDown() {
    velocitY = Math.abs(velocitY);
  }

  void velocityUp() {
    velocitY = Math.abs(velocitY) * -1;
  }

  void velocityRight() {
    velocitX = Math.abs(velocitX);
  }

  void velocityLeft() {
    velocitX = Math.abs(velocitX) * -1;
  }

  int scram() {
    move(velocitX, velocitY);
    if (hitTop()) {
      velocityDown();
    } else if (hitBottom()) {
      // vzelocityUp();
      return 1;
    }
    if (hitLeft()) {
      velocityRight();
    } else if (hitRight()) {
      velocityLeft();
    }
    return 0;
  }

  Point getCenter() {
    return new Point((int) getCenterX(), (int) getCenterY());
  }

  int radius() {
    return (int) Math.round(getHeight() / 2);
  }

  boolean has(Point p) {
    return getCenter().distance(p) < radius();
  }

  void setVelocity(int[] i) {
    velocitX = i[0];
    velocitY = i[1];
  }
}

enum XCollide {
  NO, LEFT, RIGHT,
}

enum YCollide {
  NO, TOP, BOTTOM,
}

class BallCollision {
  BallCollision(XCollide xC, YCollide yC) {
    x = xC;
    y = yC;
    whereHasCollided = xC != XCollide.NO || yC != YCollide.NO;
  }

  boolean whereHasCollided;
  XCollide x;
  YCollide y;

  @Override
  public String toString() {
    String s = "yeco ";
    if (whereHasCollided) {
      if (x == XCollide.LEFT) {
        s += "left";
      } else if (x == XCollide.RIGHT) {
        s += "rigt";
      } else {
        s += "nolf";
      }
      s += " ";
      if (y == YCollide.TOP) {
        s += "top";
      } else if (y == YCollide.BOTTOM) {
        s += "bot";
      } else {
        s += "noy";
      }
      return s;
    } else {
      return "noco";
    }
  }
}

public class Breakout extends GraphicsProgram {
  static final long serialVersionUID = 0xdeadbeef;

  interface config {
    static int countX = 10;
    static int countY = 5;
    static int blockCount = countX * countY;
  }

  // hooray for pure functions, amirite?
  static int ballSize(int width, int height) {
    return Math.min(width, height) / Math.max(config.countX, config.countY) - 10;
  }

  static int[] blockAreaWidthBounds(int width) {
    return new int[] { 0, width };
  }

  static int[] blockAreaHeightBounds(int height) {
    return new int[] { 0, height / 2 };
  }

  List<Consumer<Breakout>> nextTick = new ArrayList<>();

  public static void main(String[] a) {
    Breakout b = new Breakout();
    b.setStartupObject(null);
    b.start();
  }

  Thread thread;
  boolean paused = true;
  boolean runInsteadOfUnpause = false;

  void unpause() {
    if (runInsteadOfUnpause) {
      run();
      runInsteadOfUnpause = false;
    } else {
      paused = false;
    }
  }

  void pause() {
    paused = true;
  }

  public void mouseClicked(MouseEvent e) {
    // System.out.println("Hey, I'm actually working now for some reason");
    if (paused) {
      unpause();
    } else {
      pause();
    }
  }

  public void init() {
    Breakout that = this;
    addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        pause();
        runInsteadOfUnpause = true;
      }
    });
    thread = new Thread(() -> {
      while (true) {
        if (that.paused) {
          pause(100);
        } else {
          nextTick.stream().forEach((Consumer<Breakout> c) -> c.accept(that));
          if (!tick()) {
            gameOver();
          }
        }
      }
    });
    thread.start();
  }

  int height;
  int width;

  class BlockDataClass {
    int blocksLeft;
    int[] boundsX;
    int[] boundsY;
    int areaWidth;
    int areaHeight;
    int blockWidth;
    int blockHeight;
    Block[][] blocks = new Block[config.countY][config.countX];

    void calc(int width, int height) {
      /** 0 is the lower bound. 1 is the upper bound */
      boundsX = blockAreaWidthBounds(width);
      boundsY = blockAreaHeightBounds(height);
      areaWidth = boundsX[1] - boundsX[0];
      areaHeight = boundsY[1] - boundsY[0];
      blockWidth = (areaWidth) / config.countX;
      blockHeight = (areaHeight) / config.countY;
    }

    Block block(int arraX, int arraY) {
      if (
        arraX > 0
        && arraY > 0
        && arraX < config.countX
        && arraY < config.countY
      ) {
        return blocks[arraY][arraX];
      }
      return null;
    }

    Block getBlockAtPoint(int x, int y) {
      return block((x - boundsX[0]) / blockWidth, (y - boundsY[0]) / blockHeight);
    }

    Block getBlockAtPoint(double x, double y) {
      return getBlockAtPoint((int) x, (int) y);
    }

    Block getBlockAtPoint(GPoint p) {
      return getBlockAtPoint(p.getX(), p.getY());
    }

    int remove(Block b) {
      blocks[b.arraY][b.arraX] = null;
      return --blocksLeft;
    }
  }

  BlockDataClass BlockData;

  void removeBlock(Block b) {
    remove(b);
    BlockData.remove(b);
  }

  void beanBlock(Block b) {
    b.damage();
    if (b.isDead()) {
      removeBlock(b);
    }
  }

  DisplayPoint temp(Point p) {
    final DisplayPoint dP = new DisplayPoint(p, 6);
    add(dP);
    nextTick.add((Breakout B) -> B.remove(dP));
    return dP;
  }

  Ball ball = new Ball();

  public void run() {
    clear();
    BlockData = new BlockDataClass();

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
        cell.setLocation(x * BlockData.blockWidth + BlockData.boundsX[0],
            y * BlockData.blockHeight + BlockData.boundsY[0]);
        cell.setSize(BlockData.blockWidth, BlockData.blockHeight);
        add(cell);
      }
    }
    BlockData.blocksLeft = config.blockCount;

    paddle = new Paddle();
    paddle.setSize(BlockData.blockWidth * 2, Settings.paddleHeight);
    paddle.setLocation(width / 2, height - height / 20);
    paddle.setFilled(true);
    paddle.normalColor();
    add(paddle);

    ball.setLocation(width / 2, height / 1.3);
    ball.setBoundaryBox(width, height);
    ball.setSize(ballSize(width, height));
    add(ball);
    ball.velocitX = 0;
    ball.velocitY = -4;
  }

  /**
   * advances the game
   * 
   * @return if the game should keep running
   */
  boolean tick() {
    if (ball.scram() != 0) { // that means that it's hit the bottom
      return false;
    }
    doCollide();
    pause(Settings.speed);
    return true;
  }

  void doCollide() {
    if (ball.getY() < BlockData.areaHeight) {
      doBlockCollide();
    } else if (ball.getBottomY() > paddle.getY()) {
      paddleCollide();
    }
  }

  volatile Paddle paddle;

  void paddleCollide() {
    Point clamped = ballClamped(paddle);
    if (ball.has(clamped)) {
      temp(clamped);
      paddle.gotHit();
      nextTick.add((Breakout b) -> paddle.normalColor());
      ball.setVelocity(paddle.getBounceAngle(clamped));
    }
  }

  public void mouseMoved(MouseEvent e) {
    paddle.setX(e.getX());
  }

  Set<Block> collisionHashSet = new LinkedHashSet<>();

  void doBlockCollide() {
    // blocks.add(BlockData.getBlockAtPoint(ball.getLocation())); // center
    collisionHashSet.add(BlockData.getBlockAtPoint(ball.getX(), ball.getY())); // top left
    collisionHashSet.add(BlockData.getBlockAtPoint(ball.getRightX(), ball.getY())); // top right
    collisionHashSet.add(BlockData.getBlockAtPoint(ball.getX(), ball.getBottomY())); // bottom left
    collisionHashSet.add(BlockData.getBlockAtPoint(ball.getRightX(), ball.getBottomY())); // bottom right
    collisionHashSet.stream().filter(Objects::nonNull).forEach((Block b) -> {
      // final Color previous = b.getFillColor();
      BallCollision bC = whereHasCollided(b);
      if (bC.x == XCollide.LEFT) {
        // remember, the block is being hit from the LEFT side
        // therefore the ball should also bounce left
        ball.velocityLeft();
      } else if (bC.x == XCollide.RIGHT) {
        ball.velocityRight();
      }
      if (bC.y == YCollide.TOP) {
        ball.velocityUp();
      } else if (bC.y == YCollide.BOTTOM) {
        ball.velocityDown();
      }
      // System.out.println(bC.toString());
      if (bC.whereHasCollided) {
        beanBlock(b);
      }
    });
    collisionHashSet.clear();
  }

  Point ballClamped(Blonk b) {
    return b.closestPointTo(ball.getCenter());
  }

  BallCollision whereHasCollided(Block b) {
    Point clamped = ballClamped(b);
    XCollide xC = XCollide.NO;
    YCollide yC = YCollide.NO;
    // blue
    // red pur
    // cyan
    // dP.setFillColor(Color.BLACK);
    if (ball.has(clamped)) {
      if (b.pointIsInsideLeft(clamped)) {
        xC = XCollide.LEFT;
        // dP.setFillColor(Color.RED);
      } else if (b.pointIsInsideRight(clamped)) {
        xC = XCollide.RIGHT;
        // dP.setFillColor(Color.MAGENTA);
      } else {
        xC = XCollide.NO;
      }
      if (b.pointIsInsideTop(clamped)) {
        yC = YCollide.TOP;
        // dP.setFillColor(Color.BLUE);
      } else if (b.pointIsInsideBottom(clamped)) {
        // dP.setFillColor(Color.CYAN);
        yC = YCollide.BOTTOM;
      } else {
        yC = YCollide.NO;
      }
      // if (Settings.collisionDots) {
      // final DisplayPoint dP = new DisplayPoint(clamped, 6);
      // Color fc;
      // add(dP);
      // nextTick.add((Breakout B) -> B.remove(dP));
      // }
    }
    return new BallCollision(xC, yC);
  }

  void gameOver() {
    pause();
    runInsteadOfUnpause = true;
    clear();
  }
}
