import java.util.Arrays;
import java.util.Scanner;

class Main {
	public static void main(String[] a) {
		Scanner input = new Scanner(System.in);
		int[] ary = new int[5];
		for (int i = 0; i < ary.length; i++) {
			ary[i] = input.nextInt();
		}
		System.out.println("{" + String.join(",", Arrays.stream(ary).mapToObj(Integer::toString).toArray(String[]::new)) + "}");
		input.close();
	}
}
