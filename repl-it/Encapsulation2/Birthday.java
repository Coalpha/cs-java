class Birthday {
  int year;
  int month;
  int day;
  /** Returns the Birthday in <code>month/day/year</code> format */
  @Override
  public String toString() {
    return month + "/" + day + "/" + year;
    // this format makes NO sense
  }
}
