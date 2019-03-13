import java.util.Timer;
import java.util.function.*;
import java.util.ArrayList;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Snhaik {
  private static int N = 20;
  private static int W = N * 2;
  private static String TL = "┌";
  private static String TR = "┐";
  private static String BL = "└";
  private static String BR = "┘";
  private static String V = "│";
  private static String H = "─";
  private static String F = ".";
  private static String B = "█";
  private static Point P;
  private static String foond = "%";
  private static String D = "N";
  private static int X = N;
  private static int Y = N;
  private static int L = 3;
  private static ArrayList<Point> A = new ArrayList(L);
  private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
  private static Runnable row = () -> {
    System.out.print(V);
    prepeat(F, W);
    System.out.println(V);
  };
  private static void bean() {
    beanEnd();
    overWrite(X, Y, "#");
    System.exit(1);
  }
  private static Runnable doupdate = () -> {
    wDir();
    if (D == "N") {
      move(0, -1);
      if (Y == 0) {
        bean();
      }
    } else if (D == "E") {
      move(1, 0);
      if (X == W) {
        bean();
      }
    } else if (D == "S") {
      move(0, 1);
      if (Y == N + 1) {
        bean();
      }
    } else if (D == "W") {
      move(-1, 0);
      if (X == 0) {
        bean();
      }
    }
    if (X == (int) P.getX() && Y == (int) P.getY()) {
      // I'm too lazy to invert this
      PlaceFood();
    } else {
      beanEnd();
    }
    try {
      String s = in.readLine();
      if (s.equalsIgnoreCase("quit")) {
        System.exit(0);
      } else if (s.equalsIgnoreCase("w")) {
        D = "N";
      } else if (s.equalsIgnoreCase("d")) {
        D = "E";
      } else if (s.equalsIgnoreCase("s")) {
        D = "S";
      } else if (s.equalsIgnoreCase("a")) {
        D = "W";
      }
    } catch (IOException lASDKJASLKDALJSKDASLJDK) {
      System.exit(1);
    }
    checkL();
  };
  public static void main(String args[]) throws InterruptedException {
    if (args.length == 1) {
      System.out.println("Player 1 Controls:");
      System.out.println(" w\nasd\n");
      System.out.println("Player 2 Controls:");
      System.out.println("lol it's just return / enter");
      System.out.println("Player 1 should try to get as long as possible");
      System.out.println("\nPlayer 2 should try to get Player 1 to commit git");
    } else {
      clear();
      top();
      dO(row, N);
      bottom();
      cPOS(N, 2);
      move(0, -1);
      move(0, -1);
      move(0, -1);
      PlaceFood();
      while(true) {
        doupdate.run();
      }
    }
  }
  public static void prepeat(String s, int i) {
    String res = "";
    while (i --> 0) {
      res += s;
    }
    System.out.print(res);
  }
  public static void lf() {
    System.out.println();
  }
  public static void top() {
    System.out.print(TL);
    prepeat(H, W);
    System.out.println(TR);
  }
  public static void bottom() {
    System.out.print(BL);
    prepeat(H, W);
    System.out.println(BR);
  }
  public static void makeBox() {
    System.out.println();
  }
  public static void dO(Runnable it, int n) {
    while(n --> 0) {
      it.run();
    }
  }
  /** Prints a VT100 Sequence */
  public static void pvt100(String str) {
    System.out.print(String.format("\033" + str));
  }
  /** Clear the terminal */
  public static void clear() {
    pvt100("[2J");
  }
  /** Cursor to top left */
  public static void cTL() {
    pvt100("[H");
  }
  /** Cursor position */
  public static void cPOS(int y, int x) {
    pvt100("[" + (x + 1) + ";" + (y + 1) + "H");
  }
  /** Cursor to bottom left */
  public static void cBL() {
    pvt100("[H");
  }
  /** Reset the cursor position */
  public static void resetCPOS() {
    cPOS(0, N + 3);
  }
  /** overWrite sets the cursor to a certain position and then prints the String provided*/
  public static void overWrite(int x, int y, String s) {
    cPOS(x, y);
    System.out.print(s);
    resetCPOS();
  }
  public static void move(int dx, int dy) {
    X = X + dx;
    Y = Y + dy;
    A.add(new Point(X, Y));
    overWrite(X, Y, B);
  }
  public static void beanEnd() {
    Point P = A.get(0);
    A.remove(0);
    int cx = (int) Math.round(P.getX());
    int cy = (int) Math.round(P.getY());
    overWrite(cx, cy, F);
  }
  public static void wDir() {
    overWrite(W, N + 2, D);
  }
  /** Writes a Food character to the terminal, not used */
  public static void PlaceFood() {
    int fx = (int) Math.floor(Math.random() * W) + 1;
    int fy = (int) Math.floor(Math.random() * N) + 1;
    if (checkK(fx, fy)) {
      PlaceFood();
    } else {
      P = new Point(fx, fy);
      overWrite(fx, fy, foond);
    }
  }
  public static boolean checkK(int x, int y) {
    int s = A.size() - 1;
    while(s --> 0) {
      Point p = A.get(s);
      int px = (int) p.getX();
      int py = (int) p.getY();
      if (px == x && py == y) {
        return true;
      }
    }
    return false;
  }
  public static void checkL() {
    if (checkK(X, Y)) {
      bean();
    }
  }
}
