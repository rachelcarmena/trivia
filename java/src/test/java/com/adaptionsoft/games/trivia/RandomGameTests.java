package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.Status;
import com.adaptionsoft.games.uglytrivia.Game;
import com.adaptionsoft.games.uglytrivia.Players;
import com.adaptionsoft.games.uglytrivia.Questions;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Random;

import static com.adaptionsoft.games.trivia.StringIsEqualsAsPreviousInMatcher.isEqualsAsPreviousIn;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
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
    Status status;
    private Game aGame;

    private Game getGame(Players players) {
        return new Game(players, status, new Questions());
    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        aGame = getGame(players);
        given(players.currentPlayerName()).willReturn(PLAYER_NAME);
    }

    @Test
    public void should_inform_when_player_added() {
        aGame = getGame(new Players());

        assertThat(aGame.add(PLAYER_NAME), is(true));

        verify(status).informAboutAddedPlayer(PLAYER_NAME);
        verify(status).informAboutNumberOfPlayers(1);
    }

    @Test
    public void should_keep_a_player_in_penalty_box_when_even_roll() {
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);

        aGame.roll(EVEN_ROLL);

        verify(players).setGettingOutOfPenaltyBox(false);
        verify(status).informAboutCurrentPlayerAndRoll(PLAYER_NAME, EVEN_ROLL);
        verify(status).informAboutNotToGetOutOFPenaltyBox(PLAYER_NAME);
    }

    @Test
    @Parameters({"0, Pop", "4, Pop", "8, Pop", "1, Science", "5, Science", "9, Science", "2, Sports", "6, Sports", "10, Sports", "3, Rock"})
    public void should_move_when_player_in_penalty_box_and_odd_roll(int currentPlayerPlace, String category) {
        given(players.currentPlayerPlace()).willReturn(currentPlayerPlace);
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);

        aGame.roll(ODD_ROLL);

        verify(status).informAboutCurrentPlayerAndRoll(PLAYER_NAME, ODD_ROLL);
        verify(players).setGettingOutOfPenaltyBox(true);
        verify(status).informAboutUserGettingOutOfPenaltyBox(PLAYER_NAME);
        verify(players).moveCurrentPlayer(ODD_ROLL);
        verify(status).informAboutLocationCategoryAndQuestion(PLAYER_NAME, currentPlayerPlace, category, category + " " + FIRST_QUESTION);
    }

    @Test
    @Parameters({"0, Pop", "4, Pop", "8, Pop", "1, Science", "5, Science", "9, Science", "2, Sports", "6, Sports", "10, Sports", "3, Rock"})
    public void should_move_when_player_not_in_penalty_box_and_any_roll(int currentPlayerPlace, String category) {
        given(players.currentPlayerPlace()).willReturn(currentPlayerPlace);
        given(players.currentPlayerIsInPenaltyBox()).willReturn(false);

        aGame.roll(ANY_ROLL);

        verify(status).informAboutCurrentPlayerAndRoll(PLAYER_NAME, ANY_ROLL);
        verify(players).moveCurrentPlayer(ANY_ROLL);
        verify(status).informAboutLocationCategoryAndQuestion(PLAYER_NAME, currentPlayerPlace, category, category + " " + FIRST_QUESTION);
    }

    @Test
    public void should_change_to_next_player_when_correctly_answered_and_current_player_in_penalty_box_and_not_getting_out() {
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);
        given(players.isGettingOutOfPenaltyBox()).willReturn(false);

        boolean notWinner = aGame.wasCorrectlyAnswered();

        assertTrue(notWinner);
        verify(players).nextPlayer();
        verifyNoMoreInteractions(status);
    }

    @Test
    @Parameters({"0, true", "1, true", "2, true", "3, true", "4, true", "5, true", "6, false"})
    public void should_increase_coins_and_change_to_next_player_when_correctly_answered_and_current_player_getting_out_from_penalty_box(int coinsNumber, boolean notWinnerExpectedValue) {
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);
        given(players.isGettingOutOfPenaltyBox()).willReturn(true);
        given(players.currentPlayerGoldCoins()).willReturn(coinsNumber);

        boolean notWinner = aGame.wasCorrectlyAnswered();

        assertThat(notWinner, is(notWinnerExpectedValue));
        verify(status).informAboutCorrectAnswer();
        verify(players).increaseGoldCoins();
        verify(status).informAboutGoldCoins(PLAYER_NAME, coinsNumber);
        verify(players).nextPlayer();
    }

    @Test
    @Parameters({"0, true", "1, true", "2, true", "3, true", "4, true", "5, true", "6, false"})
    public void should_increase_coins_and_change_to_next_player_when_correctly_answered_and_current_player_not_in_penalty_box(int coinsNumber, boolean notWinnerExpectedValue) {
        given(players.currentPlayerIsInPenaltyBox()).willReturn(false);
        given(players.currentPlayerGoldCoins()).willReturn(coinsNumber);

        boolean notWinner = aGame.wasCorrectlyAnswered();

        assertThat(notWinner, is(notWinnerExpectedValue));
        verify(status).informAboutCorrectAnswer();
        verify(players).increaseGoldCoins();
        verify(status).informAboutGoldCoins(PLAYER_NAME, coinsNumber);
        verify(players).nextPlayer();
    }

    @Test
    public void should_move_player_to_penalty_box_and_change_to_next_player_when_wrong_answer() {
        boolean notWinner = aGame.wrongAnswer();

        assertTrue(notWinner);
        verify(status).informAboutWrongAnswer();
        verify(status).informAboutUserGettingInPenaltyBox(PLAYER_NAME);
        verify(players).movePlayerToPenaltyBox();
        verify(players).nextPlayer();
    }

    @Test
    @Parameters
    public void playSeveralGames(long seed) throws IOException {
        String consoleOutput = playGame(seed);
        assertThat(consoleOutput, isEqualsAsPreviousIn("test-" + seed + ".expected"));
    }

    public Object[] parametersForPlaySeveralGames() {
        int numberOfPlays = 500;
        Object[] params = new Object[numberOfPlays];
        Random random = new Random(3L);
        for (int i = 0; i < numberOfPlays; i++) {
            params[i] = random.nextLong();
        }
        return params;
    }

    private String playGame(long seed) {
        final MockSystemOutput systemOutput = MockSystemOutput.inject();

        Game aGame = new Game();
        aGame.add("Chet");
        aGame.add("Pat");
        aGame.add("Sue");

        Random rand = new Random(seed);

        boolean notAWinner;
        do {
            aGame.roll(rand.nextInt(5) + 1);
            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }
        } while (notAWinner);

        return systemOutput.toString();
    }
}