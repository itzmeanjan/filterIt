package in.itzmeanjan.filterit;

// holds status code & message for a computation performed
// in a method, can be used as a return value
// status code, 0 - success
// anything else - failure
class ReturnVal {
  int code;
  String msg;

  ReturnVal(int c, String m) {
    code = c;
    msg = m;
  }

  public String toString() {
    return "( " + code + " ) - " + msg;
  }
}
