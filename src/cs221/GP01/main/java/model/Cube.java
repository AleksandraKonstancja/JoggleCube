/*
   * @(#) Cube.java 1.1 2018/02/04
   *
   * Copyright (c) 2018 University of Wales, Aberystwyth.
   * All rights reserved.
   *
   */
package cs221.GP01.main.java.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * The Class which handles and looks after the Cube
 * @author Samuel Jones - srj12
 * @version 1.1
 */
public class Cube implements ICube{
    private Block cube[][][] = new Block[3][3][3];
    private ArrayList<String> bagOfLetters = new ArrayList<>();
    private HashMap<String, String> scores = new HashMap<>();

    private String cubeLanguage;

    public Cube(String letterFilename){
        loadBagOfLetters(letterFilename);
    }

    public ArrayList<String> getBagOfLetters() {
        return bagOfLetters;
    }
    public HashMap<String, String> getScores(){
        return scores;
    }
    public Block[][][] getCube() {
        return cube;
    }

    @Override
    public Block getBlock(int x, int y, int z) {
        return cube[x][y][z];
    }

    @Override
    public void populateCube() {
        //loadBagOfLetters(letterFilename);
        Random randomNumGen = new Random();
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                for(int k = 0; k<3; k++){
                    //Generate a random index for the ArrayList, and add the letter from that point into the Cube
                    int randomNumber = randomNumGen.nextInt(bagOfLetters.size()-1);
                    cube[i][j][k] = new Block(bagOfLetters.get(Math.abs(randomNumber)));
                    //Make sure that the letter is removed from the "Bag O'Letters"
                    bagOfLetters.remove(randomNumber);
                }
            }
        }
    }

    @Override
    public ArrayList<int[]> getNeighbours(int x, int y, int z) {
        ArrayList<int[]> neighbours = new ArrayList<>();

        int originalX = x;
        int originalY = y;
        int originalZ = z;

        //Create an arraylist of potential neighbours
        for(int i = -1; i<2; i++){
            x = originalX;
            x += i;
            for(int j = -1; j<2; j++){
                y = originalY;
                y+=j;
                for(int k = -1; k<2; k++){
                    z = originalZ;
                    z += k;

                    int nextNeighbour[] = new int[3];
                    nextNeighbour[0] = x;
                    nextNeighbour[1] = y;
                    nextNeighbour[2] = z;
                    neighbours.add(nextNeighbour);
                }
            }
        }

        //Remove incorrect/impossible positions
        int arraySize = neighbours.size();
        for(int i = 0; i<arraySize; i++){
            //If the neighbour is equal to the original of the co-ordinates
            if(neighbours.get(i)[0] == originalX
                    && neighbours.get(i)[1] == originalY
                    && neighbours.get(i)[2] == originalZ){
                neighbours.remove(i);
                i--;
                arraySize--;
            } else

            //If the neighbours co-ords can not be possible then remove.
                if(neighbours.get(i)[0] < 0 || neighbours.get(i)[0] > 2
                        || neighbours.get(i)[1] < 0 || neighbours.get(i)[1] > 2
                        || neighbours.get(i)[2] < 0 || neighbours.get(i)[2] > 2){
                    neighbours.remove(i);
                    i--;
                    arraySize--;
            }
        }

        //return the list of neighbours
        return neighbours;
    }

    @Override
    public boolean saveCube(PrintWriter file) {
        file.print(cubeLanguage + "\n");
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++){
                for(int k = 0; k<3; k++){
                    file.print(cube[i][j][k].getLetter());
                    if(k == 2){
                        file.print("\n");
                    } else {
                        file.print(" ");
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean loadCube(Scanner file) {
        ArrayList<String> letters = new ArrayList<>();
        String input;
        //Load in all of the letters
        while(file.hasNext() && letters.size() < 27) {
            input = file.next();
            letters.add(input);
        }
        if(!(letters.size() == 27)){
            System.out.println("Cube that is loaded is corrupt");
            return false;
        }

        //Letters contains all of the cube in order from 0,0,0 to 2,2,2
        int index = 0;
        for(int i = 0; i<3;i++){
            for(int j = 0; j<3; j++){
                for(int k = 0; k<3; k++){
                    setBlock(i,j,k,new Block(letters.get(index)));
                    index++;
                }
            }
        }
        return true;
    }

    public void setBlock(int x, int y, int z, Block block){
        cube[x][y][z] = block;
    }

    private void loadBagOfLetters(String lettersFilename){
        bagOfLetters.clear();
        String input;
        InputStream file = getClass().getResourceAsStream("/cs221/GP01/main/resource/letters/" + lettersFilename);
        Scanner in = new Scanner(file);
        //Counter
        int i = 0;
        String lastLetter = "";
        while(in.hasNext()){
            input = in.next();
            if (i%3 == 0){
                //If it is a 3 then it is a letter
                lastLetter = input;
            } else if ( i%3 == 1){
                //Its the number of times its been pulled in
                for (int j = 0; j<Integer.parseInt(input); j++){
                    bagOfLetters.add(lastLetter);
                }
            } else{
                //Its the score for the letter
                scores.put(lastLetter, input);
            }
            i++;
        }
    }

    public void setLanguage(String language) {
        this.cubeLanguage = language;
    }
}
