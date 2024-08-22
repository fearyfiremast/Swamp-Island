package org.display;

import javax.swing.*;
import java.awt.*;

/**
 * Displays the instructions screen for the users to read and better understand the game.
 * @author      Rob Cameron
 */
public class Instructions extends Screens {

    JLabel controlTitle, goal, arrows, mcReg, mcMove, keys, log, coin, rewards, mushroom, punish, powerUps, powerInfo, juice, trap, wildMoose, moving;
    public JButton backToMenu;
    JLabel line1, line2, line3, line4, line5, line6;

    String basePath = "Swamp Island/src/main/Images/UI/";

    /**
     * Constructor. Creates all components and adds them to the panel.
     * Sets all button and label attributes used on the panel.
     * Also sets layout and background.
     */
    public Instructions() {
        super();

        controlTitle = makeLabel("Instructions", 55, new Rectangle(180,10,600,100));
        controlTitle.setVerticalAlignment(JLabel.TOP);

        goal = makeLabel("Collect all logs and make it to the exit to win",25, new Rectangle(80,560,800,100));

        backToMenu = makeButton("Back to Menu", new Rectangle(330,630,300,40));

        arrows = getSizedImage(basePath + "wasd.png",new Rectangle(125,105,150,100));

        mcReg = getSizedImage("Swamp Island/src/main/Images/sprites/mainCharacter/idle/idle_d2.png",new Rectangle(300,128,50,50));

        mcMove = getSizedImage("Swamp Island/src/main/Images/sprites/mainCharacter/move/move_r2.png",new Rectangle(350,125,50,50));

        keys = makeLabel("Use WASD keys to move",25, new Rectangle(425,100,300,100));

        log = getSizedImage(basePath + "Log.png",new Rectangle(145,225,50,50));

        coin = getSizedImage(basePath + "Coin.png",new Rectangle(210,225,50,50));

        rewards = makeLabel("Collect logs and coins to improve your score",25, new Rectangle(300,195,600,100));

        mushroom = getSizedImage(basePath  + "Mushroom.png",new Rectangle(175,295,50,50));

        punish = makeLabel("Mushrooms reduce your score",25, new Rectangle(325,265,450,100));

        wildMoose = getSizedImage("Swamp Island/src/main/Images/sprites/enemy/idle/idle_d1.png",new Rectangle(175,360,50,50)); //Imports the photo

        moving = makeLabel("Wild moose will chase and kill you",25, new Rectangle(300,335,500,100));

        powerUps = makeLabel("Power Ups:",25, new Rectangle(375,390,400,100));

        juice = getSizedImage(basePath + "MushroomJuice.png",new Rectangle(175,470,50,50));

        trap = getSizedImage(basePath + "Trap.png",new Rectangle(175,525,50,50));

        powerInfo = makeLabel("<html>Press J to drink Mushroom Juice and boost your speed<br/><br/>Press K to lay a Moose Trap which can kill a Wild Moose</html>",25, new Rectangle(250,420,700,200));

        String lineImg = basePath + "Line.png";

        line1 = getSizedImage(lineImg,new Rectangle(20,70,920,40));
        line2 = getSizedImage(lineImg,new Rectangle(20,195,920,40));
        line3 = getSizedImage(lineImg,new Rectangle(20,265,920,40));
        line4 = getSizedImage(lineImg,new Rectangle(20,335,920,40));
        line5 = getSizedImage(lineImg,new Rectangle(20,400,920,40));
        line6 = getSizedImage(lineImg,new Rectangle(20,570,920,40));

        this.setLayout(null);
        this.setBackground(new Color(134,88,68));

        JLabel[] labs = new JLabel[] {controlTitle,keys,rewards,punish,moving,goal,
                arrows,mcReg,mcMove,log,coin,powerInfo,powerUps,juice,trap,mushroom,wildMoose,line1,line2,line3,line4,line5,line6};

        for (JLabel l : labs) {
            this.add(l);
        }
        this.add(backToMenu);
    }

}
