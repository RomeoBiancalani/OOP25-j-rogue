package it.unibo.jrogue.boundary;

import javafx.scene.layout.VBox;

/**
 * Interface for menu styled GUI.
 * */
public interface MenuGUI {
    /**
     * Configure the elements of the Menu, which includes the background image.
     * */

    void initGraphics();

    /**
     * Update the view of the buttons based on the selected button.
     *
     * @param selectIndex stands for the index selected in the Menu.
     * */

    void updateSelection(int selectIndex);

    /**
     * getter for the layout.
     *
     * @return rootLayout which contain the GUI elements
     * */

    VBox getLayout();

}
