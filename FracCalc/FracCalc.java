// package com.gradescope.fraccalc;

import java.util.Scanner;
import java.lang.RuntimeException;

/** Fraction class that does all of the math */
class Fraction {
  /** Sometimes you need to start out with nothing */
  static Fraction NA = new Fraction(0, 1);

  /**
   * <b>Gets</b> the greatest common denominator
   * @param a denominator 1
   * @param b denominator 2
   */
  static long gcm(int a, int b) {
    // Euclidean algorithm using mod
    // https://stackoverflow.com/questions/6618994/simplifying-fractions-in-java
    return b == 0 ? a : Fraction.gcm(b, a % b);
  }

  int numerator;
  int denominator;

  /** Make a new fraction */
  Fraction(int nu, int de) {
    if (de == 0)
      throw new RuntimeException("Tried to divide by zero!");
    this.numerator = nu;
    this.denominator = de;
    if (de < 0)
      this.mut_scale(-1);
    // fraction times -1/-1
  }

  /** Scales and mutates the fraction! Does not make a new one! */
  void mut_scale(int i) {
    this.numerator *= i;
    this.denominator *= i;
  }

  Fraction inverseNumerator() {
    return new Fraction(this.numerator * -1, this.denominator);
  }

  Fraction reciprocal() {
    return new Fraction(this.denominator, this.numerator);
  }

  public Fraction simplify() {
    int gcm = (int) Fraction.gcm(this.numerator, this.denominator);
    return new Fraction(this.numerator / gcm, this.denominator / gcm);
  }

  public String toMixedNumber() {
    if (this.denominator == 1) {
      // if it's a fraction like 5/1, just return 5
      return Integer.toString(this.numerator);
    }
    if (Math.abs(this.numerator) > this.denominator) {
      // for stuff like -10 / 2 or 10 / 2
      String whole = Integer.toString((int) Math.floor(this.numerator / this.denominator));
      String frac = new Fraction(Math.abs(this.numerator % this.denominator),
          // why Math.abs? because we don't want -2_-2/3 or something
          this.denominator).toString();
      if (!(frac.equals(""))) {
        whole += "_" + frac;
        // if there's no fractional counterpart after converting to mixed number
      }
      return whole;
    }
    // otherwise just return the toString
    return this.toString();
  }

  @Override
  public String toString() {
    return this.numerator + "/" + this.denominator;
  }
}

/** Since Java doesn't have namespaces... */
interface Operator {
  static Fraction add(Fraction left, Fraction right) {
    return new Fraction(
      left.numerator * right.denominator + right.numerator * left.denominator,
      left.denominator * right.denominator
    );
  }

  static Fraction multiply(Fraction left, Fraction right) {
    return new Fraction(
      left.numerator * right.numerator,
      left.denominator * right.denominator
    );
  }
}

public class FracCalc {
  /** Main is only if this file is used as a command line thingie */
  public static void main(String[] a) {
    while (true) {
      System.out.print("$ ");
      Scanner console = new Scanner(System.in);
      String input = console.nextLine();
      if (input.matches("[qQeE]|quit|exit")) {
        console.close();
        System.exit(0);
      }
      System.out.println(produceAnswer(input));
    }
  }
  /** Solves an expression */
  public static String produceAnswer(String input) {
    System.out.println("inputted: " + input);
    String[] leftCenterRight = input.split(" ");
    Fraction computedFraction;
    if (leftCenterRight.length == 1) {
      computedFraction = parseSide(leftCenterRight[0]);
    } else {
      Fraction left = parseSide(leftCenterRight[0]);
      String center = leftCenterRight[1];
      Fraction right = parseSide(leftCenterRight[2]);
      if (center.length() != 1) {
        throw new RuntimeException("The operand must be of length 1!");
      }
      switch (center.charAt(0)) {
      case '+':
        computedFraction = Operator.add(left, right);
        break;
      case '-':
        computedFraction = Operator.add(left, right.inverseNumerator());
        break;
      case '*':
        computedFraction = Operator.multiply(left, right);
        break;
      case '/':
        computedFraction = Operator.multiply(left, right.reciprocal());
        break;
      default:
        return "Confused!";
      }
    }
    return computedFraction.simplify().toMixedNumber();
  }
  /** It parses something like 1_1/3 */
  static Fraction parseSide(String input) {
    String[] parts = input.split("[/_]");
    if (input.contains("_")) {
      // mixed number
      int wholeNumber = Integer.parseInt(parts[0]);
      int numerator = Integer.parseInt(parts[1]) * (wholeNumber < 0 ? -1 : 1);
      // that last bit with the ternary operator is Math.sign basically
      int denominator = Integer.parseInt(parts[2]);
      return new Fraction(wholeNumber * denominator + numerator, denominator);
      // 5_1/3 is (5 * 3) + 1 / 3
    }
    if (input.contains("/")) {
      int numerator = Integer.parseInt(parts[0]);
      int denominator = Integer.parseInt(parts[1]);
      return new Fraction(numerator, denominator);
    }
    return new Fraction(Integer.parseInt(input), 1);
  }
}
