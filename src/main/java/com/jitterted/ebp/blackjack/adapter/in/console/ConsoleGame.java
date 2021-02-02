package com.jitterted.ebp.blackjack.adapter.in.console;

import com.jitterted.ebp.blackjack.domain.Game;

public class ConsoleGame {

  private final Game game;

  public ConsoleGame(Game game) {
    this.game = game;
  }

  // CONTROLLER method
  public void start() {
    Game.displayWelcomeScreen();

    game.initialDeal();

    playerPlays();

    game.dealerTurn();

    game.displayFinalGameState();

    game.determineOutcome();

    Game.resetScreen();
  }

  // Player Game Loop
  public void playerPlays() {
    while (!game.isPlayerDone()) {
      game.displayGameState();
      String command = game.inputFromPlayer();
      handle(command);
    }
  }

  // DISPATCHER
  public void handle(String command) {
    if (command.toLowerCase().startsWith("h")) {
      game.playerHits();
    } else if (command.toLowerCase().startsWith("s")) {
      game.playerStands();
    }
  }

}
