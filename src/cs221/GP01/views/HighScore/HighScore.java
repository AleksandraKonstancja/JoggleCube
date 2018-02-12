/*
   * @(#) HighScore.java 1.0 2018/02/12
   *
   * Copyright (c) 2018 University of Wales, Aberystwyth.
   * All rights reserved.
   *
   */

package cs221.GP01.views.HighScore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *  HighScore - Encapsulate and represent a given High Score entry
 *  Represent a Given High Score Entry -> Date/time of Score, Score
 * @author Rhys Evans (rhe24@aber.ac.uk)
 * @version 0.1  DRAFT
 */
public class HighScore {

    /**
     * Achieved score
     */
    private final Integer score;

    /**
     * The Date of the achieved time
     */
    private final String date;

    /**
     * The name of the Highscore Holder
     */
    private final String name;

    /**
     * Constructor for a high score
     * Convert score and date value to string
     * @param score as an int
     */
    public HighScore(Integer score, String name){
        // Convert Score to String
        this.score = score;

        // Assign name
        this.name = name;

        // Get Current Date
        Date currDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        // Display date as string
        this.date = dateFormat.format(currDate);
    }

    /**
     * Get the date of a given high score entry
     * @return Date of the score
     */
    public String getDate(){
        return date;
    }

    /**
     * Get the score value of a given high score entry
     * @return The score
     */
    public Integer getScore(){
        return score;
    }

    /**
     * Get the name of a given high score holder
     * @return The highscore holder's name
     */
    public String getName(){
        return name;
    }

}
