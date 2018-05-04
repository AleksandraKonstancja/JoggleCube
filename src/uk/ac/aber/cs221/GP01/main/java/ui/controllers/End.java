/*
   * @(#) EndController.java 1.1 2018/02/12
   *
   * Copyright (c) 2018 University of Wales, Aberystwyth.
   * All rights reserved.
   *
   */

package uk.ac.aber.cs221.GP01.main.java.ui.controllers;

import uk.ac.aber.cs221.GP01.main.java.model.JoggleCube;
import uk.ac.aber.cs221.GP01.main.java.ui.Dialog;
import uk.ac.aber.cs221.GP01.main.java.ui.Navigation;
import uk.ac.aber.cs221.GP01.main.java.ui.UI;
import uk.ac.aber.cs221.GP01.main.java.ui.ScreenType;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * End - A class that controls the Pause subscene that is defined in End.fxml
 * Used with End.fxml
 *
 * @author Rhys Evans (rhe24@aber.ac.uk)
 * @author Nathan Williams (naw21)
 * @version 1.1
 * @see INeedPrep
 */
public class End extends BaseOverlay implements INeedPrep {

    private static End endView;

    private End(){}

    @FXML
    Label scoreLabel,highScoreLabel;

    @FXML
    Button saveButton;

    Dialog inputDialog;

    Dialog informationDialog;

    /**
     * Sets text for score and highscore, also enables and disables save button
     */
    @Override
    public void prepView() {
        scoreLabel.setText(JoggleCube.getInstance().getScore() + "");
        highScoreLabel.setText(JoggleCube.getInstance().getHighestScore() + "");

        // Disable save button if grid was loaded
        if(!JoggleCube.getInstance().getGamesStateNew()){
            saveButton.setDisable(true);
        }else{
            saveButton.setDisable(false);
        }
        JoggleCube.getInstance().resetGameState();
    }

    /**
     * When the High Score button is pressed, change scene to high score screen
     *
     * @see HighScore
     * @throws IOException - if FXML file could not be found/opened
     */
    @FXML
    public void btnHighScoreClicked()  {
        Navigation.getInstance().switchScreen(ScreenType.HIGH_SCORES);
        Navigation.getInstance().hideOverlay(ScreenType.END, parentController);
    }

    /**
     * When the 'return to menu' button is clicked change scene to menu scene
     *
     * @see Start
     */
    @FXML
    public void btnMenuClicked(){
        Navigation.getInstance().switchScreen(ScreenType.START);
        Navigation.getInstance().hideOverlay(ScreenType.END, parentController);
    }

    /**
     * When the 'replay' button is clicked restart a game
     */
    @FXML
    public void btnReplayClicked() {
        Navigation.getInstance().switchScreen(ScreenType.GAME);
        Navigation.getInstance().hideOverlay(ScreenType.END, parentController);
    }

    /**
     * When the 'save' button is clicked prompt user to chose a save location
     */
    @FXML
   public void btnSaveClicked(){
        inputDialog = new Dialog();
        String result = inputDialog.showInputDialog("Save Cube", "Please enter a filename", "untitled", new ImageView(new Image(getClass().getResourceAsStream("/uk/ac/aber/cs221/GP01/main/resource/img/icon/save_icon_alt.png"))), true);

        informationDialog = new Dialog();

        // Normalize input and save to regular java String
        result = result.replace(" ", "");

        if(inputDialog.isValidInput(result) && !result.isEmpty()){
            if(JoggleCube.getInstance().saveGrid(result)){
                informationDialog.showInformationDialog("Success", "File saved successfully!");
            }else{
                informationDialog.showInformationDialog("Error", "Error Saving File, Please try again");
                // Re-prompt user recursively until file is saved
                btnSaveClicked();
            }
        }
    }

    /**
     * Getting instance of end overlay
     *
     * @return endView
     */
    public static End getInstance(){
        if(endView == null){
            synchronized (UI.class){
                if(endView == null){
                    endView = new End();
                }
            }
        }
        return endView;
    }

    /**
     * Gets score
     *
     * @return scoreLabel as string
     */
    public String getScore() {
        return scoreLabel.getText();
    }

    /**
     * Gets highscore
     *
     * @return highScoreLabel as string
     */
    public String getHighScore() {
        return highScoreLabel.getText();
    }

    public Dialog getInputDialog() {
        return inputDialog;
    }

    public Dialog getInformationDialog() {
        return informationDialog;
    }
}