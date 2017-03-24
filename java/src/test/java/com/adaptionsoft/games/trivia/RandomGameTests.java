package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Console;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Players;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@RunWith(JUnitParamsRunner.class)
public class RandomGameTests {

    private static final int EVEN_ROLL = 2;
    private static final int ODD_ROLL = 3;
    private static final String PLAYER_NAME = "ALVARO";
    private static final int ANY_ROLL = 10;
    private static final Object FIRST_QUESTION = "Question 0";
    @Mock
    Players players;
    @Mock
    Console console;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void should_keep_a_player_in_penalty_box() {
        Game aGame = new Game(players, console);
        given(players.currentPlayerName()).willReturn(PLAYER_NAME);
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);

        aGame.roll(EVEN_ROLL);

        verify(players).setGettingOutOfPenaltyBox(false);
        verify(console).informAboutTheCurrentPlayer(PLAYER_NAME);
        verify(console).informAboutTheRole(EVEN_ROLL);
        verify(console).informAboutNotToGetOutOFPenaltyBox(PLAYER_NAME);
    }

    @Test
    @Parameters({"0, Pop", "4, Pop", "8, Pop", "1, Science", "5, Science", "9, Science", "2, Sports", "6, Sports", "10, Sports", "3, Rock"})
    public void should_move_roll_when_player_not_in_penalty_box(int currentPlayerPlace, String category) {
        Game aGame = new Game(players, console);
        given(players.currentPlayerName()).willReturn(PLAYER_NAME);
        given(players.currentPlayerPlace()).willReturn(currentPlayerPlace);
        given(players.currentPlayerIsInPenaltyBox()).willReturn(false);

        aGame.roll(ANY_ROLL);

        verify(console).informAboutTheCurrentPlayer(PLAYER_NAME);
        verify(console).informAboutTheRole(ANY_ROLL);
        verify(players).moveCurrentPlayer(ANY_ROLL);
        verify(console).informAboutNewLocation(PLAYER_NAME, currentPlayerPlace);
        verify(console).informAboutCategory(category);
        verify(console).informAboutQuestion(category + " " + FIRST_QUESTION);
    }

    @Test
    @Parameters({"0, Pop", "4, Pop", "8, Pop", "1, Science", "5, Science", "9, Science", "2, Sports", "6, Sports", "10, Sports", "3, Rock"})
    public void should_move_roll_when_player_should_get_out_of_penalty_box(int currentPlayerPlace, String category) {
        Game aGame = new Game(players, console);
        given(players.currentPlayerName()).willReturn(PLAYER_NAME);
        given(players.currentPlayerPlace()).willReturn(currentPlayerPlace);
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);

        aGame.roll(ODD_ROLL);

        verify(console).informAboutTheCurrentPlayer(PLAYER_NAME);
        verify(console).informAboutTheRole(ODD_ROLL);
        verify(players).setGettingOutOfPenaltyBox(true);
        verify(console).informAboutUserGettingOutOfPenaltyBox(PLAYER_NAME);
        verify(players).moveCurrentPlayer(ODD_ROLL);
        verify(console).informAboutNewLocation(PLAYER_NAME, currentPlayerPlace);
        verify(console).informAboutCategory(category);
        verify(console).informAboutQuestion(category + " " + FIRST_QUESTION);
    }

    @Test
    public void play_several_games() {
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