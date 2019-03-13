import javax.script.*;
import java.util.Scanner;

class Parser {
  static ScriptEngine njine = new ScriptEngineManager().getEngineByName("nashorn");
  static Scanner s = new Scanner(System.in);
  static class ANSI {
    static String reset = "\u001b[0m";
    static String red = "\u001b[31m";
    static String green = "\u001b[32m";
    static String yellow = "\u001b[33m";
    static String purple = "\u001b[35m";
    static String cyan = "\u001b[36m";
  }
  public static void main(String[] args) throws ScriptException, NoSuchMethodException {
    njine.eval("function parse(s){return eval(s.replace(/(\\d+)_(\\d+)\\/(\\d+)/, '($1 + $2 / $3)'))}");
    Invocable inv = (Invocable) njine;
    System.out.println(ANSI.green + "Type something in\n");
    while (true) {
      System.out.print(ANSI.cyan + "$ " + ANSI.reset);
      String inp = s.nextLine();
      System.out.print(ANSI.red + inp + ANSI.yellow + " = " + ANSI.purple);
      Object res = inv.invokeFunction("parse", inp);
      System.out.println(res + "\n");
    }
  }
}
