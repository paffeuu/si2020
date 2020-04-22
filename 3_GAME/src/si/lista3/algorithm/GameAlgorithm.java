package si.lista3.algorithm;

import si.lista3.engine.Connect4Engine;

public interface GameAlgorithm {
    int getBestMove(Connect4Engine engine, int player);
}
