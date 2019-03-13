public class Letter {
  public static void main(String args[]) {
    make("Cole", "3");
    make("Brendan", "7");
    make("Marita", "17");
    if (args.length > 1) {
      make(args[0], args[1]);
    }
  }
  public static void make(String person, String n) {
    System.out.println("~~~START PSA~~~");
    System.out.println("Henol " + person + "!");
    System.out.println("This is a PSA from the Java team at Oracle:");
    System.out.println("It is now recommended to use " + n + " spaces per tab");
    System.out.println("~~~END PSA~~~");
    System.out.println();
  }
}
