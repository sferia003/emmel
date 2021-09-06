package com.sferia.emmel;

import java.util.List;
import java.util.Map;

class EClass implements Callable {
  final String name;
  final EClass superclass;
  private final Map<String, Function> methods;

  EClass(String name, EClass superclass, Map<String, Function> methods) {
    this.superclass = superclass;
    this.name = name;
    this.methods = methods;
  }

  @Override
  public Object call(Interpreter interpreter, List<Object> arguments) {
    Instance instance = new Instance(this);
    Function initializer = findMethod("con");
    if (initializer != null) {
      initializer.bind(instance).call(interpreter, arguments);
    }
    return instance;
  }

  Function findMethod(String name) {
    if (methods.containsKey(name)) {
      return methods.get(name);
    }

    if (superclass != null) {
      return superclass.findMethod(name);
    }

    return null;
  }

  @Override
  public int numArgs() {
    Function initializer = findMethod("con");
    if (initializer == null)
      return 0;
    return initializer.numArgs();
  }

  @Override
  public String toString() {
    return name;
  }
}
