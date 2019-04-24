#include <string>
#include <csignal>
#include <iostream>
#include <optional>
#include <algorithm>
// #define DEBUG
using namespace std;

string e("\x1B[");
string rst(e + "0m");
string pink(e + "35m");
string cyan(e + "36m");
const char MIXED_NUM_DELIMITER('+');

void interrupt(int signum) {
  cout << pink << "\nInterrupt! Exiting Gracefully...\n";
  exit(signum);
}

class Fraction {
  int nu;
  int de;
public:
  static int gcm(int nu, int de) {
    return de == 0 ? nu : Fraction::gcm(de, nu % de);
  }
  static int gcm(Fraction* f) {
    return Fraction::gcm(f->nu, f->de);
  }
  Fraction(Fraction* f) {
    this->nu = f->nu;
    this->de = f->de;
  }
  Fraction(int i) {
    this->nu = i;
    this->de = 1;
  }
  Fraction(int num, int den) {
    this->nu = num;
    this->de = den;
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
  void scale(int i) {
    this->nu *= i;
    this->de *= i;
  }
  void elacs(int i) {
    this->nu /= i;
    this->de /= i;
  }
  void simplify() {
    this->elacs(Fraction::gcm(this));
  }
  string format() {
    if (this->de == 0) {
      return "undefined";
    }
    if (this->de == 1) {
      return to_string(this->nu);
    }
    if (this->nu == this->de) {
      return "1";
    }
    if (this->de < 0) {
      this->scale(-1);
    }
    this->simplify();
    if (abs(this->nu) > this->de) {
      // if it can be a mixed number
      string prefix;
      string suffix;
      if (this->nu < 0) {
        prefix = "-(";
        suffix = ")";
        this->nu *= -1;
      }
      string res(to_string(this->nu / this->de));
      int remainder(this->nu % this->de);
      if (remainder != 0) {
        res += MIXED_NUM_DELIMITER + to_string(remainder) + '/' + to_string(this->de);
      }
      return res;
    }
    return this->toString();
  }
  string toString() {
    return to_string(this->nu) + "/" + to_string(this->de);
  }
};

Fraction* parseSide(string& side) {
  #ifdef DEBUG
  cout << "parseSide(" << side << ");\n";
  #endif
  unsigned int len(side.length());
  string last;
  bool foundMixed(false);
  bool foundDivide(false);
  int whole{};
  int nu{};
  int de{};
  for (unsigned int i{}; i < len; i++) {
    char c(side[i]);
    if (isdigit(c)) {
      last += c;
    } else if (c == MIXED_NUM_DELIMITER) {
      if (foundMixed) {
        ::cerr << "Unexpected second \"" << MIXED_NUM_DELIMITER << "\"\n";
        return nullptr;
      }
      if (last.length() < 1) {
        ::cerr << "Expected whole number component of mixed number\n";
        return nullptr;
      }
      foundMixed = true;
      whole = stoi(last);
      last = "";
    } else if (c == '/') {
      #ifdef DEBUG
      cout << "Found \"/\"\n";
      #endif
      if (foundDivide) {
        ::cerr << "Unexpected second \"/\"\n";
        return nullptr;
      }
      foundDivide = true;
      if (last.length() < 1) {
        ::cerr << "Expected numerator component of fraction\n";
        return nullptr;
      }
      #ifdef DEBUG
      cout << "Last is \"" << last << "\"\n";
      #endif
      nu = stoi(last);
      last = "";
      #ifdef DEBUG
      cout << "Flushed Last\n";
      #endif
    } else if (
      (c == '-' || c == '+')
      && last.length() == 0
    ) {
      last += c;
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
    return new Fraction(Fraction(whole) + Fraction(nu, de));
  }
  if (foundDivide) {
    return new Fraction(nu, de);
  }
  if (last.length() > 0) {
    return new Fraction(stoi(last));
  }
  ::cerr << "Expected integer(s)\n";
  return nullptr;
}

string solve(string& input) {
  unsigned int len(input.length());
  #ifdef DEBUG
  cout << "solve(" << input << ");\n";
  #endif
  int partIndex{};
  string parts[3];
  for (unsigned int i{}; i < len; i++) {
    char c(input[i]);
    if (c == ' ') {
      if (++partIndex > 2) {
        cerr << "More than two spaces found in expression";
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
    cerr << "Less than two spaces in expression ";
    goto exit;
  }
  if (parts[1].length() == 1) {
    char operand(parts[1][0]);
    Fraction* left(parseSide(parts[0]));
    if (left) {
      Fraction* right(parseSide(parts[2]));
      if (right) {
        Fraction* res;
        if (operand == '+') {
          res = new Fraction(*left + *right);
        } else if (operand == '-') {
          res = new Fraction(*left - *right);
        } else if (operand == '*') {
          res = new Fraction(*left * *right);
        } else if (operand == '/') {
          res = new Fraction(*left / *right);
        } else {
          cerr << "Unexpected operand " << operand << "\"";
          goto exit;
        }
        #ifdef DEBUG
        cout << left->toString() << ", " << right->toString();
        #endif
        string rstring = res->format();
        delete left;
        delete right;
        delete res;
        return rstring;
      } else cerr << "Error parsing right side of " << operand;
    } else cerr << "Error parsing left side of " << operand;
  } else cerr << "The operand must be a character";
  exit:
  return "";
}

int main() {
  signal(SIGINT, interrupt);
  while (true) {
    cout << cyan << "$ " << rst;
    string input{};
    getline(cin, input);
    transform(input.begin(), input.end(), input.begin(), ::tolower);
    if (input == "q" || input == "quit" || input == "exit") {
      break;
    }
    string sln(solve(input));
    cout << sln << "\n\n";
  }
  return 0;
}
