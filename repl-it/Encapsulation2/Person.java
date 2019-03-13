public class Person {
  Name name;
  Birthday birthday;
  /** So much for a Social Security <b>Number</b> */
  String ssn;
  Person(String first, String last, int month, int day, int year) {
    name.first = first;
    name.last = last;
    birthday.month = month;
    birthday.day = day;
    birthday.year = year;
  }
  String getFirstname() { return name.first; }
  String getLastname() { return name.last; }
  void setFirstname(String f) { name.first = f; }
  void setLastname(String l) { name .last = l; }
  String getBirthday() {
    return birthday.toString();
  }
  boolean verifySSN(String s) { return ssn.equals(s); }
}
