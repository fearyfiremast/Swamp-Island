package org.game;

import org.display.*;

import java.awt.*;

public class Main {
    public static void main(String[] args) {

        // Initiates Display Elements
        Display display = new Display();

        // Sets up variables for gameManager class
        // Input handler is project class that checks keyboard for specific MC inputs
        InputHandler inputHandler = new InputHandler();
        CardLayout layout = new CardLayout();
        Container cards = new Container();

        //TODO Make dynamic levelId
        GameManager gameManager = new GameManager(inputHandler, layout, cards, "Level");

        // Sets up visual components
        MainMenu menu = new MainMenu();
        Instructions controls = new Instructions();
        Intro intro = new Intro();
        End end = new End();

        // gameManager extends Jpanel, added to gamescreen.
        GameScreen gameScreen = new GameScreen(gameManager);

        cards.addKeyListener(inputHandler);
        // Possibilities unclear but means can receive keyboard
        // focus. IE switching between multiple text boxes. A single one
        // Would require "focus" to be typed in. Input
        cards.setFocusable(true);

        // Keys(Inputs?) promote cards to have focus within a list of items.
        // Presumably set to false as the only input relation is keyboard -> MC, within this frame
        cards.setFocusTraversalKeysEnabled(false);

        cards.setLayout(layout);
        display.add(cards);

        // playGame is a button defined withing the MainMenu class
        // layout manages the card that is displayed
        // cards are the main "Scenes". Elements are comprised of frames that make up the visible elements.

        // 1. Makes buttons predefined on menu, search for and display screens by the name in string
        menu.makeButtonListener(menu.playGame,layout,cards,"intro");
        menu.makeButtonListener(display);
        menu.makeButtonListener(menu.controls,layout,cards,"controls");

        controls.makeButtonListener(controls.backToMenu,layout,cards,"main menu");

        intro.makeButtonListener(layout,cards,gameManager, gameScreen);

        end.makeButtonListener(end.playAgain,layout,cards,"intro");
        end.makeButtonListener(end.toMenu,layout,cards,"main menu");

        // TODO change how it adds to allow for more sophisticated system / swapping state (make screen enum)
        // 2. Adds screen to container with display name
        cards.add("main menu",menu);
        cards.add("controls",controls);
        cards.add("intro",intro);
        cards.add("game",gameScreen);
        cards.add("end", end);

        display.revealScreen();
    }
}