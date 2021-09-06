package com.sferia.emmel;

import java.util.List;

interface Callable {
  int numArgs();

  Object call(Interpreter interpreter, List<Object> arguments);
}
