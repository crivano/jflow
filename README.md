JFlow
====

![](https://github.com/crivano/jflow/workflows/Java%20CI/badge.svg)

JFlow is a simple open-source library for defining and running workflows in java. It is specifically designed to be easily included in other applications.

Main goals are:
- To have zero dependencies
- To be embeddable just by implementing interfaces
- To have a simple user interface to edit workflow definitions
- To have a easily readable codebase
- To support persistence on different databases

Web Editor
====

![JFlow](https://user-images.githubusercontent.com/4137623/72182221-cbde2d80-33c9-11ea-93ad-61e223398cf0.png)

A workflow process definition is basically an array of task definitions, and it can be easily edited using a web application. An example of an web app that edits process definitions can be seen on this [live demo](https://crivano.github.io/jflow/front-end/angularjs/index.html) (in Portuguese). It's source code is available at the `front-end` directory.

Example / Usage
====

A worflow can be defined like this:

```Java
// Create the process definition
ProcessDefinition pd = new ProcessDefinitionSupport();

// Create the task definition
TaskDefinition td = new TaskDefinitionSupport("1", "test", TaskKindSupport.FORM, "Form", null,
  ResponsibleKindSupport.REGISTRANT, null, null, null, null);
pd.getTaskDefinition().add(td);

// Create the process instance without responsible support
HashMap<String, Object> variable = new HashMap<String, Object>();
ProcessInstance pi = new ProcessInstanceSupport(pd, variable, null) {
  @Override
  public Responsible calcResponsible(TaskDefinition tarefa) {
    return null;
  }
};
   
// Create the engine without persistence or handling of any kind
Engine engine = new EngineImpl(null, null);
```

And can be run like this:

```Java
// Start the process instance
engine.start(pi, pd, variable);

// The form is the first and only task definition, engine should wait for an
// user event to continue
assertEquals(ProcessInstanceStatus.PAUSED, pi.getStatus());

// Resume after the user has filled the form
engine.resume(TaskForm.getEvent(td, pi), null, null);

// Workflow should be ended by now
assertEquals(ProcessInstanceStatus.FINISHED, pi.getStatus());

```

Graph
====

JFlow is also capable of generating a graph of the process instance in the GraphViz DOT language, you will need an GraphViz processor to convert it to a .SVG or .PNG:

```Java
String dot = GraphViz.getDot(pi, "Start", "Finish");
```

For the example above, the `dot` variable will contain something like this:

```Dot
"start"[shape="oval"][color="black"][fontcolor="black"][label=<Start>];
"start"->"1";
"finish"[shape="oval"][color="black"][fontcolor="black"][label=<Finish>];
"1"[shape="rectangle"][color="blue"][fontcolor="blue"][label=<Form>];
"1"->"finish";
```

Enclose it between a `Digraph G {` and a `}`, submit to a [GraphViz Engine](https://dreampuf.github.io/GraphvizOnline/#digraph%20G%20%7B%0A%0A%22start%22%5Bshape%3D%22oval%22%5D%5Bcolor%3D%22black%22%5D%5Bfontcolor%3D%22black%22%5D%5Blabel%3D%3CStart%3E%5D%3B%0A%22start%22-%3E%221%22%3B%0A%22finish%22%5Bshape%3D%22oval%22%5D%5Bcolor%3D%22black%22%5D%5Bfontcolor%3D%22black%22%5D%5Blabel%3D%3CFinish%3E%5D%3B%0A%221%22%5Bshape%3D%22rectangle%22%5D%5Bcolor%3D%22blue%22%5D%5Bfontcolor%3D%22blue%22%5D%5Blabel%3D%3CForm%3E%5D%3B%0A%221%22-%3E%22finish%22%3B%0A%0A%7D) and you will end up with a nice graph:

![Graphviz_Online](https://user-images.githubusercontent.com/4137623/72162148-2d89a200-33a0-11ea-906b-5a94d4b3d2c2.png)

Development
====

JFlow is very much in-development, and is in no way, shape, or form guaranteed to be stable or bug-free.  Bugs, suggestions, or pull requests are all very welcome.

License
====
Copyright 2020 Renato Crivano

Licensed under the GNU AFFERO GENERAL PUBLIC LICENSE, Version 3

http://www.gnu.org/licenses/agpl-3.0.html
