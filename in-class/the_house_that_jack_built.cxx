#include <iostream>
#include <string>
using namespace std;

int idx{};
string a[12];

void print() {
  int i(idx - 1);
  while (i --> 0) {
    cout << a[i];
  }
  cout << ".\n";
}

void make(string noun, string verb) {
  cout << "This is the " << noun;
  a[idx++] = " that " + verb + " the " + noun;
  print();
}

int main() {
  make("house that Jack built", "lay in");
  make("malt", "ate");
  make("rat", "killed");
  make("cat", "worried");
  make("dog", "tossed");
  make("cow with the crumpled horn", "milk'd");
  make("maiden all forlorn", "kissed");
  make("man all tatter'd and torn", "married");
  make("priest all shaven and shorn", "waked");
  make("cook that crow'd in the morn", "kept");
  make("farmer sowing his corn", "murdered");
}
