package com.jitterted.ebp.blackjack.domain.port;

import com.jitterted.ebp.blackjack.domain.Deck;
import com.jitterted.ebp.blackjack.domain.Game;
import com.jitterted.ebp.blackjack.domain.Rank;
import com.jitterted.ebp.blackjack.domain.StubDeck;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class GameMonitorTest {

  @Test
  public void playerStandsCompletesGameSendsToMonitor() throws Exception {
    // creates the spy based on the interface
    GameMonitor gameMonitorSpy = spy(GameMonitor.class);
    Game game = new Game(new Deck(), gameMonitorSpy);
    game.initialDeal();

    game.playerStands();

    // verify that the roundCompleted method was called with any instance of a Game class
    verify(gameMonitorSpy).roundCompleted(any(Game.class));
  }

  @Test
  public void playerHitsGoesBustThenGameSendsResultToMonitor() throws Exception {
    GameMonitor gameMonitorSpy = spy(GameMonitor.class);
    Deck playerGoesBustUponHitDeck = new StubDeck(Rank.TEN, Rank.EIGHT, Rank.NINE, Rank.QUEEN, Rank.FOUR);
    Game game = new Game(playerGoesBustUponHitDeck, gameMonitorSpy);
    game.initialDeal();

    game.playerHits();

    verify(gameMonitorSpy).roundCompleted(any(Game.class));
  }

  @Test
  public void playerHitsDoesNotGoBustThenNoResultsSentToMonitor() throws Exception {
    GameMonitor gameMonitorSpy = spy(GameMonitor.class);
    Deck playerDoesNotBustDeck = new StubDeck(Rank.TEN, Rank.EIGHT, Rank.FOUR, Rank.KING, Rank.FIVE);
    Game game = new Game(playerDoesNotBustDeck, gameMonitorSpy);
    game.initialDeal();

    game.playerHits();

    verify(gameMonitorSpy, never()).roundCompleted(any(Game.class));
  }
}