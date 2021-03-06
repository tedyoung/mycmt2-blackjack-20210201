package com.jitterted.ebp.blackjack.adapter.in.web;

import com.jitterted.ebp.blackjack.domain.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BlackjackController {

  private final Game game;

  @Autowired
  public BlackjackController(Game game) {
    this.game = game;
  }

  // "in progress" game view
  @GetMapping("/game")
  public String gameView(Model model) {
    model.addAttribute("gameView", GameView.of(game));
    return "blackjack";
  }

  @PostMapping("/start-game")
  public String startGame() {
    game.initialDeal();
    return redirectPage();
  }

  @PostMapping("/hit")
  public String hitCommand() {
    game.playerHits();
    return redirectPage();
  }

  @PostMapping("/stand")
  public String standCommand() {
    game.playerStands();
    return redirectPage();
  }

  public String redirectPage() {
    if (game.isPlayerDone()) {
      return "redirect:/done";
    }
    return "redirect:/game";
  }

  // "game over" game view
  @GetMapping("/done")
  public String doneView(Model model) {
    GameView gameView = GameView.of(game);
    model.addAttribute("gameView", gameView);

    model.addAttribute("outcome", game.determineOutcome().toString());

    return "done";
  }

}
