package com.jitterted.ebp.blackjack;

public class GameApplication {

  // Assemble the DOMAIN and INJECT into the ConsoleGame (Adapter/Controller)
  public static void main(String[] args) {
    Game game = new Game();
    ConsoleGame consoleGame = new ConsoleGame(game);
    consoleGame.start();
  }

}
