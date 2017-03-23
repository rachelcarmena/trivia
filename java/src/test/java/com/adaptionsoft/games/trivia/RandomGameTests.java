package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Game;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class RandomGameTests {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private Object currentPlayer;

    @Test
    public void play_a_single_game() {
        long seed = 1L;


        Random random = new Random(3L);
        for(int i=0; i<500; i++){
            playGame(random.nextLong());
        }

    }

    private void playGame(long seed) {
        final MockSystemOutput systemOutput = MockSystemOutput.inject();


        Game aGame = new TestGame();

        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random(seed);

        String filename = "/tmp/ugly-trivia/test-" + seed + ".actual";

        boolean notAWinner;
        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }
        } while (notAWinner);

        try(
                FileWriter fw = new FileWriter(filename, false);
                BufferedWriter bw = new BufferedWriter(fw)) {
            try {
                bw.write(systemOutput.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {


        }
    }

    private class TestGame extends Game {
        protected Object getCurrentPlayer() {
            return currentPlayer;
        }
    }
}
