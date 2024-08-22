package org.display;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Super class for all panels. Contains functions to make labels and panels with the theme.
 * @author      Rob Cameron
 */
public class Screens extends JPanel {

    /**
     * Empty constructor which just calls super class
     */
    public Screens() {
        super();
    }

    /**
     * Makes a label with the specified words and font size.
     * Follows the chosen theme of the game, with the light colour used in many locations.
     *
     * @param       text String of the words to be on the label
     * @param       fontSize Integer of the font size to use
     * @param       r Rectangle of the bounds of the label
     * @return      JLabel of the text
     */
    protected JLabel makeLabel(String text, int fontSize, Rectangle r) {

        JLabel lab = new JLabel(text);
        addDetails(lab,fontSize,new Color(222,214,190), r);
        lab.setHorizontalAlignment(SwingConstants.CENTER);

        return lab;
    }

    /**
     * Helper function for makeLabel and makeButton. Uses superclass JComponent to set font, foreground, and bounds.
     *
     * @param       j JComponent that is either a JLabel or JButton to have details added
     * @param       fontSize Integer for the size of font on the label/button
     * @param       c Color of the foreground
     * @param       r Rectangle with the bounds of the component
     */
    private void addDetails(JComponent j, int fontSize, Color c, Rectangle r) {

        j.setFont(new Font("Art Nuvo Stamp", Font.BOLD, fontSize));
        j.setForeground(c);
        j.setBounds(r);

    }

    /**
     * Makes a JButton with the specified text on it.
     * Follows the game's theme, with text being in dark colour and background light.
     * Font is automatically size 30.
     *
     * @param       text String of the words to be on the button
     * @param       r Rectangle of the bounds of the button
     * @return      JButton with the text on it
     */
    protected JButton makeButton(String text, Rectangle r) {

        JButton button = new JButton(text);
        addDetails(button,30,new Color(134,88,68),r);
        button.setBackground(new Color(222,214,190));

        return button;
    }

    /**
     * Gets an image file and returns it as a specific size JLabel.
     *
     * @param       name The name of the file
     * @param       r Rectangle of the width and height of the image, bounds of the JLabel
     * @return      JLabel containing the image.
     * @see         Image
     * @see         ImageIcon
     * @see         BufferedImage
     * @see         JLabel
     */
    protected JLabel getSizedImage(String name, Rectangle r) {
        Image img;
        ImageIcon icon = new ImageIcon();
        try
        {
            BufferedImage bufferedImage = ImageIO.read(new File(name));
            img = bufferedImage.getScaledInstance(r.width, r.height, Image.SCALE_DEFAULT);
            icon = new ImageIcon(img);
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }

        JLabel lab = new JLabel(icon);
        lab.setBounds(r);

        return lab;
    }

    /**
     * Makes the action listener for a generic button.
     * Will show the card (screen on UI) specified by the string
     *
     * @param       button JButton to make the listener for
     * @param       l Card layout that the panel is on
     * @param       c Container that the panels are in
     * @param       s String of the name of the screen to switch to
     */
    public void makeButtonListener(JButton button,CardLayout l, Container c, String s) {

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                l.show(c,s);
            }
        });

    }

}
