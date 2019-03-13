import java.util.Arrays;

class Main {
  public static void main(String[] args) {
    int[][] a = {
      { 1, 1, 1 },
      { 1, 1, 1 },
      { 1, 1, 1 }
    };
    System.out.println(isMagic(a)); // true
    int[][] b = {
      { 1, 1, 2 },
      { 3, 2, 3 },
      { 1, 4, 1 }
    };
    System.out.println(isMagic(b)); // false
    int[][] c = {
      { 1, 1, 1 },
      { 2, 2, 2 }
    };
    System.out.println(isMagic(c)); // false
    int[][] d = {
      { 8, 1, 6 },
      { 3, 5, 7 },
      { 4, 9, 2 }
    };
    System.out.println(isMagic(d)); // true
  }

  static boolean isMagic(int[][] a) {
    int size = a.length;
    Integer cellSum = null;
    int[] cols = new int[size];
    for (int[] row : a) {
      if (row.length != size) {
        return false;
      }
      int rowSum = Arrays.stream(row).sum();
      if (cellSum == null) {
        cellSum = rowSum;
      } else if (cellSum != rowSum) {
        return false;
      }
      int j = size;
      while (j --> 0) {
        int currint = row[j];
        cols[j] += currint;
      }
    }
    /** Check if the columns are equal */
    for (int colsum : cols) {
      if (colsum != cellSum) {
        return false;
      }
    }
    /** Check if diagonals are equal */
    {
      int i = size;
      int diagonalSum = 0;
      while (i --> 0) {
        diagonalSum += a[i][i];
      }
      if (diagonalSum != cellSum) {
        return false;
      }
    }
    return true;
  }
}
