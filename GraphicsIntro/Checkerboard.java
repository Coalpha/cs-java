import acm.graphics.GRect;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class Checkerboard extends Garfield {
  protected static final long serialVersionUID = 0xdeadbeef;

  private boolean down = false;
  @Override
  public void init() {
    on.resize.run(this::run);
    on.mouseDown.run((MouseEvent e) -> {
      System.out.println("x: " + e.getX() + " y: " + e.getY());
      down = true;
    });
    on.mouseUp.run(() -> {
      down = false;
    });
    on.mouseMove.run((MouseEvent e) -> {
      if (down == true) {
        System.out.println("dragged to x: " + e.getX() + " y: " + e.getY());
      } else {
        System.out.println("moved to x: " + e.getX() + " y: " + e.getY());
      }
    });
  }

  /** Number of black and white squares on each side of the checkerboard. */
  private static final int SQUARES_PER_SIDE = 8;
  private int sideLength;

  public void run() {
    ctx.removeAll();
    sideLength = (int) Math.round(Math.min(getHeight(), getWidth()) / SQUARES_PER_SIDE);
    int i = SQUARES_PER_SIDE;
    while (i --> 0) {
      drawRow(i, i % 2 == 0);
    }
  }
  /**
   * Draws a single black filled square onto the canvas
   */
  public void drawBlackSquare(int x, int y) {
    GRect blackSquare = new GRect(x, y, sideLength, sideLength);
    blackSquare.setFilled(true);
    blackSquare.setColor(Color.BLACK);
    ctx.add(blackSquare);
  }

  /**
   * Draws a checkerboard row
   */
  public void drawRow(int row, boolean offset) {
    int i = SQUARES_PER_SIDE / 2;
    while (i-- > 0) {
      drawBlackSquare(2 * i * sideLength + (offset ? sideLength : 0), row * sideLength);
    }
  }

  public static void main(String[] args) {
    Checkerboard.start(new Checkerboard());
  }
}
