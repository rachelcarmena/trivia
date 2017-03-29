package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.trivia.domain.Game;
import com.adaptionsoft.games.trivia.domain.Players;
import com.adaptionsoft.games.trivia.domain.Questions;
import com.adaptionsoft.games.trivia.delivery.Status;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Random;

import static com.adaptionsoft.games.trivia.StringIsEqualsAsPreviousInMatcher.isEqualsAsPreviousIn;
import static com.adaptionsoft.games.trivia.domain.Game.MAX_GOLD_COINS;
import static com.adaptionsoft.games.trivia.domain.Game.PLAYER_PLACE_LIMIT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
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
    InOrder inOrder;
    private Game aGame;

    private Game getGame(Players players) {
        return new Game(players, status, new Questions());
    }

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        aGame = getGame(players);
        given(players.currentPlayerName()).willReturn(PLAYER_NAME);
        inOrder = inOrder(players, status);
    }

    @Test
    public void should_inform_when_player_added() {
        aGame = getGame(new Players());

        assertThat(aGame.add(PLAYER_NAME), is(true));

        inOrder.verify(status).informAboutAddedPlayer(PLAYER_NAME, 1);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void should_keep_a_player_in_penalty_box_when_even_roll() {
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);

        aGame.roll(EVEN_ROLL);

        inOrder.verify(status).informAboutCurrentPlayerAndRoll(PLAYER_NAME, EVEN_ROLL);
        inOrder.verify(status).informAboutNotToGetOutOFPenaltyBox(PLAYER_NAME);
        inOrder.verify(players).setGettingOutOfPenaltyBox(false);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @Parameters(method = "get_pairs_of_place_and_category")
    public void should_move_when_player_in_penalty_box_and_odd_roll(int currentPlayerPlace, String category) {
        given(players.currentPlayerPlace()).willReturn(currentPlayerPlace);
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);

        aGame.roll(ODD_ROLL);

        inOrder.verify(status).informAboutCurrentPlayerAndRoll(PLAYER_NAME, ODD_ROLL);
        inOrder.verify(players).setGettingOutOfPenaltyBox(true);
        inOrder.verify(status).informAboutUserGettingOutOfPenaltyBox(PLAYER_NAME);
        inOrder.verify(players).moveCurrentPlayer(ODD_ROLL);
        inOrder.verify(status).informAboutLocationCategoryAndQuestion(PLAYER_NAME, currentPlayerPlace, category, category + " " + FIRST_QUESTION);
    }

    @Test
    @Parameters(method = "get_pairs_of_place_and_category")
    public void should_move_when_player_not_in_penalty_box_and_any_roll(int currentPlayerPlace, String category) {
        given(players.currentPlayerPlace()).willReturn(currentPlayerPlace);
        given(players.currentPlayerIsInPenaltyBox()).willReturn(false);

        aGame.roll(ANY_ROLL);

        inOrder.verify(status).informAboutCurrentPlayerAndRoll(PLAYER_NAME, ANY_ROLL);
        inOrder.verify(players).moveCurrentPlayer(ANY_ROLL);
        inOrder.verify(status).informAboutLocationCategoryAndQuestion(PLAYER_NAME, currentPlayerPlace, category, category + " " + FIRST_QUESTION);
    }

    private Object get_pairs_of_place_and_category() {
        Object[] pairs = new Object[PLAYER_PLACE_LIMIT];
        for (int index = 0; index < PLAYER_PLACE_LIMIT; index++) {
            pairs[index] = new Object[]{index, Game.currentCategory(index)};
        }
        return pairs;
    }

    @Test
    public void should_change_to_next_player_when_correctly_answered_and_current_player_in_penalty_box_and_not_getting_out() {
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);
        given(players.isGettingOutOfPenaltyBox()).willReturn(false);

        boolean notWinner = aGame.wasCorrectlyAnswered();

        assertTrue(notWinner);
        inOrder.verify(players).nextPlayer();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @Parameters(method = "get_pairs_of_coins_and_winner")
    public void should_increase_coins_and_change_to_next_player_when_correctly_answered_and_current_player_getting_out_from_penalty_box(int coinsNumber, boolean notWinnerExpectedValue) {
        given(players.currentPlayerIsInPenaltyBox()).willReturn(true);
        given(players.isGettingOutOfPenaltyBox()).willReturn(true);
        given(players.currentPlayerGoldCoins()).willReturn(coinsNumber);

        boolean notWinner = aGame.wasCorrectlyAnswered();

        assertThat(notWinner, is(notWinnerExpectedValue));
        inOrder.verify(status).informAboutCorrectAnswer();
        inOrder.verify(players).increaseGoldCoins();
        inOrder.verify(status).informAboutGoldCoins(PLAYER_NAME, coinsNumber);
        inOrder.verify(players).nextPlayer();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    @Parameters(method = "get_pairs_of_coins_and_winner")
    public void should_increase_coins_and_change_to_next_player_when_correctly_answered_and_current_player_not_in_penalty_box(int coinsNumber, boolean notWinnerExpectedValue) {
        given(players.currentPlayerIsInPenaltyBox()).willReturn(false);
        given(players.currentPlayerGoldCoins()).willReturn(coinsNumber);

        boolean notWinner = aGame.wasCorrectlyAnswered();

        assertThat(notWinner, is(notWinnerExpectedValue));
        inOrder.verify(status).informAboutCorrectAnswer();
        inOrder.verify(players).increaseGoldCoins();
        inOrder.verify(status).informAboutGoldCoins(PLAYER_NAME, coinsNumber);
        inOrder.verify(players).nextPlayer();
        inOrder.verifyNoMoreInteractions();
    }

    private Object get_pairs_of_coins_and_winner() {
        Object[] pairs = new Object[MAX_GOLD_COINS + 1];
        for (int index = 0; index < MAX_GOLD_COINS; index++) {
            pairs[index] = new Object[]{index, true};
        }
        pairs[MAX_GOLD_COINS] = new Object[]{MAX_GOLD_COINS, false};
        return pairs;
    }

    @Test
    public void should_move_player_to_penalty_box_and_change_to_next_player_when_wrong_answer() {
        boolean notWinner = aGame.wrongAnswer();

        assertTrue(notWinner);
        inOrder.verify(status).informAboutWrongAnswer();
        inOrder.verify(status).informAboutUserGettingInPenaltyBox(PLAYER_NAME);
        inOrder.verify(players).movePlayerToPenaltyBox();
        inOrder.verify(players).nextPlayer();
        inOrder.verifyNoMoreInteractions();
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