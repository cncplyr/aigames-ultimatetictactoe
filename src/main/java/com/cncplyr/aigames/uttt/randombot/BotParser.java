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

package com.cncplyr.aigames.uttt.randombot;

import com.cncplyr.aigames.uttt.game.Field;
import com.cncplyr.aigames.uttt.game.Move;

import java.util.Scanner;

/**
 * BotParser class
 * <p>
 * Main class that will keep reading output from the engine.
 * Will either update the bot state or get actions.
 *
 * @author Jim van Eeden <jim@starapple.nl>
 */

public class BotParser {

    private final Scanner scan;
    private final RandomBot bot;

    public static int mBotId = 0;

    public BotParser(RandomBot bot) {
        this.scan = new Scanner(System.in);
        this.bot = bot;
    }

    public void run() {
        Field field = new Field();

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.length() == 0) {
                continue;
            }

            String[] parts = line.split(" ");
            String command = parts[0];
            String commandType = parts[1];

            switch (command) {
                case "settings":
                    if (commandType.equals("your_botid")) {
                        mBotId = Integer.parseInt(parts[2]);
                    }
                    break;
                case "update":
                    if (commandType.equals("game")) {
                    /* new game data */
                        field.parseGameData(parts[2], parts[3]);
                    }
                    break;
                case "action":
                    if (commandType.equals("move")) {
                    /* move requested */
                        Move move = this.bot.makeTurn(field);
                        System.out.println("place_move " + move.getX() + " " + move.getY());
                    }
                    break;
                default:
                    System.out.println("unknown command");
            }
        }
    }
}
