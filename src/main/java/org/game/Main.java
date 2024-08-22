package org.game;

import org.display.*;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        Display display = new Display();
        InputHandler inputHandler = new InputHandler();
        CardLayout layout = new CardLayout();
        Container cards = new Container();
        GameManager gameManager = new GameManager(inputHandler, layout, cards);


        MainMenu menu = new MainMenu();
        Instructions controls = new Instructions();
        Intro intro = new Intro();
        End end = new End();
        GameScreen gameScreen = new GameScreen(gameManager);

        cards.addKeyListener(inputHandler);
        cards.setFocusable(true);
        cards.setFocusTraversalKeysEnabled(false);

        cards.setLayout(layout);
        display.add(cards);

        menu.makeButtonListener(menu.playGame,layout,cards,"intro");
        menu.makeButtonListener(display);
        menu.makeButtonListener(menu.controls,layout,cards,"controls");

        controls.makeButtonListener(controls.backToMenu,layout,cards,"main menu");

        intro.makeButtonListener(layout,cards,gameManager, gameScreen);

        end.makeButtonListener(end.playAgain,layout,cards,"intro");
        end.makeButtonListener(end.toMenu,layout,cards,"main menu");

        // TODO change how it adds to allow for more sophisticated system / swapping state
        cards.add("main menu",menu);
        cards.add("controls",controls);
        cards.add("intro",intro);
        cards.add("game",gameScreen);
        cards.add("end", end);

        display.revealScreen();

    }
}