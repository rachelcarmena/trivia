package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Console;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Players;
import com.adaptionsoft.games.uglytrivia.TracerBullet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class RandomGameTests {

    private static final int EVEN_ROLL = 2;
    private static final String PLAYER_NAME = "ALVARO";
    @Mock
    Players players;
    @Mock
    Console console;

    @Test
    public void should_keep_a_player_in_penalty_box() {
        Game aGame = new Game(players, console);
        given(players.currentPlayerName()).willReturn(PLAYER_NAME);
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);

        aGame.roll(EVEN_ROLL);

        verify(players).setGettingOutOfPenaltyBox(false);
        verify(console).informAboutTheCurrentPlayer(PLAYER_NAME);
        verify(console).informAboutTheRole(EVEN_ROLL);
    }

    @Test
    public void play_several_games() {
        long seed = 1L;

        Random random = new Random(3L);
        for (int i = 0; i < 500; i++) {
            playGame(random.nextLong());
        }
    }

    private void playGame(long seed) {
        final MockSystemOutput systemOutput = MockSystemOutput.inject();

        Game aGame = new Game();

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

        try (
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
}