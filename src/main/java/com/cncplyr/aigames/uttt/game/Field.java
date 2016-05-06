//    Copyright 2016 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package com.cncplyr.aigames.uttt.game;

import java.util.ArrayList;

/**
 * Field class
 * <p>
 * Handles everything that has to do with the field, such
 * as storing the current state and performing calculations
 * on the field.
 *
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class Field {
    private int round;
    private int move;
    private int[][] microBoard;
    private int[][] macroBoard;

    private final int COLS = 9, ROWS = 9;
    private String lastError = "";

    public Field() {
        microBoard = new int[COLS][ROWS];
        macroBoard = new int[COLS / 3][ROWS / 3];
        clearBoard();
    }

    /**
     * Parse data about the game given by the engine
     *
     * @param key   type of data given
     * @param value value
     */
    public void parseGameData(String key, String value) {
        switch (key) {
            case "round":
                round = Integer.parseInt(value);
                break;
            case "move":
                move = Integer.parseInt(value);
                break;
            case "field":
                parseFromString(value); /* Parse Field with data */
                break;
            case "macroboard":
                parseMacroBoardFromString(value); /* Parse macroBoard with data */
                break;
        }
    }

    /**
     * Initialise field from comma separated String
     */
    public void parseFromString(String s) {
        System.err.println("Move " + move);
        s = s.replace(";", ",");
        String[] r = s.split(",");
        int counter = 0;
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                microBoard[x][y] = Integer.parseInt(r[counter]);
                counter++;
            }
        }
    }

    /**
     * Initialise macroBoard from comma separated String
     */
    public void parseMacroBoardFromString(String s) {
        String[] r = s.split(",");
        int counter = 0;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                macroBoard[x][y] = Integer.parseInt(r[counter]);
                counter++;
            }
        }
    }

    public void clearBoard() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                microBoard[x][y] = 0;
            }
        }
    }

    public ArrayList<Move> getAvailableMoves() {
        ArrayList<Move> moves = new ArrayList<>();

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                if (isInActiveMicroboard(x, y) && microBoard[x][y] == 0) {
                    moves.add(new Move(x, y));
                }
            }
        }

        return moves;
    }

    public Boolean isInActiveMicroboard(int x, int y) {
        return macroBoard[x / 3][y / 3] == -1;
    }

    /**
     * Returns reason why addMove returns false
     */
    public String getLastError() {
        return lastError;
    }


    @Override
    /**
     * Creates comma separated String with player ids for the microboards.
     * @return : String with player names for every cell, or 'empty' when cell is empty.
     */
    public String toString() {
        String r = "";
        int counter = 0;
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLS; x++) {
                if (counter > 0) {
                    r += ",";
                }
                r += microBoard[x][y];
                counter++;
            }
        }
        return r;
    }

    /**
     * Checks whether the field is full
     *
     * @return true when field is full, otherwise returns false.
     */
    public boolean isFull() {
        for (int x = 0; x < COLS; x++)
            for (int y = 0; y < ROWS; y++)
                if (microBoard[x][y] == 0)
                    return false; // At least one cell is not filled
        // All cells are filled
        return true;
    }

    public int getNrColumns() {
        return COLS;
    }

    public int getNrRows() {
        return ROWS;
    }

    public boolean isEmpty() {
        for (int x = 0; x < COLS; x++) {
            for (int y = 0; y < ROWS; y++) {
                if (microBoard[x][y] > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns the player id on given column and row
     *
     * @param column the given column
     * @param row    the given row
     */
    public int getPlayerId(int column, int row) {
        return microBoard[column][row];
    }
}
