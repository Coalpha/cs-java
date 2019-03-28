#define DEBUG
#include <cctype>
#include <string>
#include <iostream>
#include <optional>
#include <algorithm>
using namespace std;

class Fraction {
  int nu;
  int de;
public:
  Fraction() {
    this->nu = 0;
    this->de = 1;
  }
  Fraction(int i) {
    this->nu = i;
    this->de = 1;
  }
  Fraction(int nu, int de) {
    this->nu = nu;
    this->de = de;
  }
  Fraction operator+(const Fraction& f) {
    return Fraction (
      this->nu * f.de + this->de * f.nu,
      this->de * f.de
    );
  }
  Fraction operator-(const Fraction& f) {
    return *this + Fraction(-f.nu, f.de);
  }
  Fraction operator*(const Fraction& f) {
    return Fraction (
      this->nu * f.nu,
      this->de * f.de
    );
  }
  Fraction operator/(const Fraction& f) {
    return *this * Fraction(f.de, f.nu);
  }
  string format() {
    // this->simplify();
    if (this->de == 1) {
      return to_string(this->de);
    }
    if (abs(this->nu) > this->de) {
      string res = to_string(this->nu / this->de);
      string frac;
      frac += to_string(abs(this->nu % this->de)) + "/" + to_string(this->de);
      if (frac != "") {
        res += "_" + frac;
      }
      return res;
    }
    return this->toString();
  }
  string toString() {
    return to_string(this->nu) + "/" + to_string(this->de);
  }
};
string e("\x1B[");
string rst(e + "0m");
string red(e + "31m");
typedef Fraction* p_frac; // pointer fraction
p_frac parseSide(string& side) {
  #ifdef DEBUG
  cout << "parseSide(" << side << ");\n";
  #endif
  int len(side.length());
  string last;
  bool foundMixed(false);
  bool foundDivide(false);
  int whole{};
  int nu{};
  int de{};
  for (int i{}; i < len; i++) {
    char c(side[i]);
    if (isdigit(c)) {
      last += c;
    } else if (c == '_') {
      if (foundMixed) {
        ::cerr << "Unexpected second \"_\"\n";
        return nullptr;
      }
      if (last.length() < 1) {
        ::cerr << "Expected whole number component of mixed number\n";
        return nullptr;
      }
      foundMixed = true;
      whole = stoi("0" + last);
    } else if (c == '/') {
      if (foundDivide) {
        ::cerr << "Unexpected second \"/\"\n";
        return nullptr;
      }
      if (last.length() < 1) {
        ::cerr << "Expected numerator component of fraction\n";
        return nullptr;
      }
      nu = stoi(last);
      last = "0";
    } else {
      ::cerr << "Unexpected \"" << c << "\"\n";
      return nullptr;
    }
  }
  if (foundDivide) {
    if (last.length() < 1) {
      ::cerr << "Expected denominator component of fraction\n";
      return nullptr;
    }
    de = stoi(last);
  } else if (foundMixed) {
    ::cerr << "Expected fractional component of mixed number\n";
    return nullptr;
  }
  if (foundMixed) {
    return &(Fraction(whole) + Fraction(nu, de));
  }
  if (foundDivide) {
    return &Fraction(nu, de);
  }
  if (last.length() > 0) {
    return &Fraction(stoi(last));
  }
  ::cerr << "Expected integer(s)\n";
  return nullptr;
};
string solve(string& input) {
  int len(input.length());
  cerr << red;
  #ifdef DEBUG
  cout << "solve(" << input << ");\n";
  #endif
  int partIndex{};
  string parts[3];
  for (int i{}; i < len; i++) {
    char c(input[i]);
    if (c == ' ') {
      if (++partIndex > 2) {
        cerr << "More than two spaces found in expression\n";
        goto exit;
      };
    } else {
      parts[partIndex] += c;
    }
  }
  #ifdef DEBUG
  cout << parts[0] << ", " << parts[1] << ", " << parts[2] << '\n';
  #endif
  if (partIndex != 2) {
    cerr << "Less than two spaces in expression\n";
    goto exit;
  }
  if (parts[1].length() == 1) {
    char operand(parts[1][0]);
    p_frac p_left(parseSide(parts[0]));
    if (p_left) {
      Fraction left(*p_left);
      p_frac p_right(parseSide(parts[2]));
      if (p_right) {
        Fraction right(*p_right);
        Fraction res { 0, 1 }; // oof
        if (operand == '+') {
          res = left + right;
        } else if (operand == '-') {
          res = left - right;
        } else if (operand == '*') {
          res = left * right;
        } else if (operand == '/') {
          res = left / right;
        } else {
          cerr << "Unexpected operand " << operand << "\"\n";
          goto exit;
        }
        return res.toString();
      } else cerr << "Error parsing right side of " << operand << '\n';
    } else cerr << "Error parsing left side of " << operand << '\n';
  } else cerr << "The operand must be a character\n";
  exit:
  return "";
}
int main() {
  while (true) {
    cout << "$ ";
    string input{};
    getline(cin, input);
    transform(input.begin(), input.end(), input.begin(), ::tolower);
    if (input == "q" || input == "quit" || input == "exit") {
      break;
    }
    string sln(solve(input));
    cout << sln;
  }
  return 0;
}
