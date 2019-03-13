public class TheHouseThatJackBuilt {
  public static String ary[] = new String[12];
  public static int idx = 0;
  public static void main(String args[]) {
    make("house that Jack built", "lay in");
    make("malt", "ate");
    make("rat,", "killed");
    make("cat,", "worried");
    make("dog,", "tossed");
    make("cow with the crumpled horn,", "milk'd");
    make("maiden all forlorn,", "kissed");
    make("man all tatter'd and torn,", "married");
    make("priest all shaven and shorn,", "waked");
    make("cook that crow'd in the morn,", "kept");
    make("farmer sowing his corn,", "murdered");
  }
  public static void make(String noun, String verb) {
    System.out.println("This is the " + noun );
    ary[idx] = "That " + verb + " the " + noun;
    idx++;
    print();
  }
  public static void print() {
    for (int i = idx - 1; i > 0; i--) {
      System.out.println(ary[i - 1]);
    }
    System.out.println();
  }
}
