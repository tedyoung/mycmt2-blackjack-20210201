package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;

public class Game {

  private final Deck deck;
  private final GameMonitor gameMonitor;

  private final Hand dealerHand = new Hand();
  private final Hand playerHand = new Hand();
  private boolean playerDone;

  public Game() {
    this(new Deck());
  }

  public Game(Deck deck) {
    this.deck = deck;
    this.gameMonitor = game -> {}; // "no-op" or Null Object Pattern implementation of GameMonitor
  }

  public Game(Deck deck, GameMonitor gameMonitor) {
    this.deck = deck;
    this.gameMonitor = gameMonitor;
  }

  public void initialDeal() {
    dealRoundOfCards();
    dealRoundOfCards();
  }

  private void dealRoundOfCards() {
    // why: players first because this is the rule
    playerHand.drawFrom(deck);
    dealerHand.drawFrom(deck);
  }

  public GameOutcome determineOutcome() {
    if (playerHand.isBusted()) {
      return GameOutcome.PLAYER_BUSTED;
    }
    if (dealerHand.isBusted()) {
      return GameOutcome.DEALER_BUSTED;
    }
    if (playerHand.beats(dealerHand)) {
      return GameOutcome.PLAYER_BEATS_DEALER;
    }
    if (playerHand.pushes(dealerHand)) {
      return GameOutcome.PLAYER_PUSHES;
    }
    return GameOutcome.PLAYER_LOSES;
  }

  private void dealerTurn() {
    // Dealer makes its choice automatically based on a simple heuristic (<=16, hit, 17>stand)
    if (!playerHand.isBusted()) {
      while (dealerHand.dealerMustDrawCard()) {
        dealerHand.drawFrom(deck);
      }
    }
  }

  public Hand playerHand() {
    return playerHand;
  }

  public Hand dealerHand() {
    return dealerHand;
  }

  // Event/Command
  public void playerHits() {
    playerHand.drawFrom(deck);
    updatePlayerDoneTo(playerHand.isBusted());
  }

  private void updatePlayerDoneTo(boolean playerDone) {
    this.playerDone = playerDone;
    if (playerDone) {
      gameMonitor.roundCompleted(this);
    }
  }

  // Event/Command
  public void playerStands() {
    dealerTurn();
    updatePlayerDoneTo(true);
  }

  // Query about State
  public boolean isPlayerDone() {
    return playerDone;
  }
}
