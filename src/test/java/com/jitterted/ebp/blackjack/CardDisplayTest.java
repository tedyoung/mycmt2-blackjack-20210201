package com.jitterted.ebp.blackjack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.fusesource.jansi.Ansi.ansi;

class CardDisplayTest {
  private static final Rank DUMMY_RANK = Rank.TEN;

  @Test
  public void displayTenAsString() throws Exception {
    Card tenCard = new Card(Suit.HEARTS, Rank.TEN);

    // characterize ("capture") existing behavior, treating it as correct
    assertThat(tenCard.display())
        .isEqualTo("\u001B[31m┌─────────┐\u001B[1B\u001B[11D│10       │\u001B[1B\u001B[11D│         │\u001B[1B\u001B[11D│    ♥    │\u001B[1B\u001B[11D│         │\u001B[1B\u001B[11D│       10│\u001B[1B\u001B[11D└─────────┘");
  }

  @Test
  public void displayNonTenAsString() throws Exception {
    Card nonTenCard = new Card(Suit.CLUBS, Rank.SEVEN);

    assertThat(nonTenCard.display())
        .isEqualTo("\u001B[30m┌─────────┐\u001B[1B\u001B[11D│7        │\u001B[1B\u001B[11D│         │\u001B[1B\u001B[11D│    ♣    │\u001B[1B\u001B[11D│         │\u001B[1B\u001B[11D│        7│\u001B[1B\u001B[11D└─────────┘");
  }

  @Test
  public void suitOfHeartsOrDiamondsIsDisplayedInRed() throws Exception {
    // given a card with Hearts or Diamonds
    Card heartsCard = new Card(Suit.HEARTS, DUMMY_RANK);
    Card diamondsCard = new Card(Suit.DIAMONDS, DUMMY_RANK);

    // when we ask for its display representation
    String ansiRedString = ansi().fgRed().toString();

    // then we expect a red color ansi sequence
    assertThat(heartsCard.display())
        .contains(ansiRedString);
    assertThat(diamondsCard.display())
        .contains(ansiRedString);
  }

}