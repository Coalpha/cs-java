#include <string>
#include <numeric>
#include <iostream>

using namespace std;

template <size_t rows, size_t cols>
bool isMagic(int (&rect)[rows][cols]) {
  if (rows != cols) {
    return false;
  }
  int cellSum(-1);
  int colSum[cols] = {0};
  for (unsigned int y{}; y < rows; y++) {
    int rowSum = accumulate(begin(rect[y]), end(rect[y]), 0);
    if (cellSum == -1) {
      cellSum = rowSum;
    } else if (cellSum != rowSum) {
      return false;
    }
    int x = cols;
    while (x-- > 0) {
      int currint = rect[y][x];
      colSum[x] += currint;
    }
  }
  // Check if the columns are equal
  for (unsigned int x{}; x < cols; x++) {
    if (colSum[x] != cellSum) {
      return false;
    }
  }
  // Check if diagonals are equal
  {
    int diagonalSum{};
    for (unsigned int i{}; i < rows; i++) {
      diagonalSum += rect[i][i];
    }
    if (diagonalSum != cellSum) {
      return false;
    }
  }
  return true;
}

int main() {
  cout << boolalpha;
  int a[][3] = {
    {1, 1, 1},
    {1, 1, 1},
    {1, 1, 1}
  };
  cout << isMagic(a) << '\n';
  int b[][3] = {
    {1, 1, 2},
    {3, 2, 3},
    {1, 4, 1}
  };
  cout << isMagic(b) << '\n';
  int c[][3] = {
    {1, 1, 1},
    {2, 2, 2}
  };
  cout << isMagic(c) << '\n';
  int d[][3] = {
    {8, 1, 6},
    {3, 5, 7},
    {4, 9, 2}
  };
  cout << isMagic(d) << '\n';
  return 0;
}
