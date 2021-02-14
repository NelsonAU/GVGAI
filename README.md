Fork of GVGAI for investigating MCTS parameters
====

The main GVGAI repository is here: https://github.com/GAIGResearch/GVGAI

Modifications in this repository:

* The sampleMCTS and olets agents have a configurable rollout depth
* There are two Main driver loops, one for sampleMCTS and one for olets, which take rollout depth and per-move time limit as parameters:
  * src/tracks/singlePlayer/Test.java
  * src/tracks/singlePlayer/OletsTest.java
