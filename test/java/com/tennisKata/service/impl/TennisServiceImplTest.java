package com.tennisKata.service.impl;

import com.tennisKata.exception.TennisBusinessException;
import com.tennisKata.model.Player;
import com.tennisKata.model.TennisMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TennisServiceImplTest {

    private TennisServiceImpl tennisService;
    private TennisMatch tennisGame;
    private Player player1;
    private Player player2;

    @BeforeEach
    void setUp() {
        tennisService = new TennisServiceImpl();
        player1 = new Player("A", "Player 1");
        player2 = new Player("B", "Player 2");
        tennisGame = new TennisMatch(player1, player2);
    }

    // Test if the sequence of points is valid (only 'A' and 'B' are allowed)
    @Test
    void IsValidSequence_False_SequenceIsNotOnlyAB() {
        assertTrue(tennisService.isValidSequence("AAAA"));
        assertFalse(tennisService.isValidSequence("AB*"));
        assertFalse(tennisService.isValidSequence("ABC"));
        assertFalse(tennisService.isValidSequence("A B"));
        assertFalse(tennisService.isValidSequence(""));
        assertFalse(tennisService.isValidSequence(null));
    }

    @Test
    void IsValidSequence_True_SequenceOnlyAB() {
        assertTrue(tennisService.isValidSequence("ABAB"));
    }

    // Test if Player 1 can win a game by scoring 4 points
    @Test
    void addPoint_should_return_Player1WinsGame() {
        tennisService.addPoint(tennisGame, "A"); // Player 1: 15-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 30-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 40-0
        tennisService.addPoint(tennisGame, "A"); // Player 1 wins the game

        assertEquals(1, tennisGame.getPlayer1Games());
        assertEquals(0, tennisGame.getPlayer2Games());
        assertEquals(0, tennisGame.getPlayer1Points());
        assertEquals(0, tennisGame.getPlayer2Points());
    }

    // Test if the game reaches deuce (40-40)
    @Test
    void addPoint_should_return_Deuce() {
        tennisService.addPoint(tennisGame, "A"); // Player 1: 15-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 30-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 40-0
        tennisService.addPoint(tennisGame, "B"); // Player 2: 40-15
        tennisService.addPoint(tennisGame, "B"); // Player 2: 40-30
        tennisService.addPoint(tennisGame, "B"); // Deuce: 40-40

        assertEquals("40", tennisService.translatePoints(tennisGame.getPlayer1Points()));
        assertEquals("40", tennisService.translatePoints(tennisGame.getPlayer2Points()));
    }

    // Test if a player can gain advantage after deuce
    @Test
    void addPoint_should_return_advantage() {
        tennisService.addPoint(tennisGame, "A"); // Player 1: 15-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 30-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 40-0
        tennisService.addPoint(tennisGame, "B"); // Player 2: 40-15
        tennisService.addPoint(tennisGame, "B"); // Player 2: 40-30
        tennisService.addPoint(tennisGame, "B"); // Deuce: 40-40
        tennisService.addPoint(tennisGame, "A"); // Player 1: Advantage

        assertEquals("Advantage", tennisService.translatePoints(tennisGame.getPlayer1Points()));
        assertEquals("40", tennisService.translatePoints(tennisGame.getPlayer2Points()));
    }

    // Test if Player 1 can win a game after gaining advantage
    @Test
    void addPoint_should_return_Player1WinsAfterAdvantage() {
        tennisService.addPoint(tennisGame, "A"); // Player 1: 15-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 30-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 40-0
        tennisService.addPoint(tennisGame, "B"); // Player 2: 40-15
        tennisService.addPoint(tennisGame, "B"); // Player 2: 40-30
        tennisService.addPoint(tennisGame, "B"); // Deuce: 40-40
        tennisService.addPoint(tennisGame, "A"); // Player 1: Advantage
        tennisService.addPoint(tennisGame, "A"); // Player 1 wins the game

        assertEquals(1, tennisGame.getPlayer1Games());
        assertEquals(0, tennisGame.getPlayer2Games());
        assertEquals(0, tennisGame.getPlayer1Points());
        assertEquals(0, tennisGame.getPlayer2Points());
    }

    // Test if a tie-break is correctly handled
    @Test
    void addPoint_should_return_TieBreakTrue() {
        tennisGame.setPlayer1Games(6); // Player 1: 6 games
        tennisGame.setPlayer2Games(6); // Player 2: 6 games
        tennisGame.setTieBreak(true);  // Tie-break

        tennisService.addPoint(tennisGame, "A"); // Player 1: 1-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 2-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 3-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 4-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 5-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 6-0
        assertTrue(tennisGame.isTieBreak()); // Tie-break still active
        assertEquals(6, tennisGame.getPlayer1Points()); // Player 1: 6 points
        assertEquals(0, tennisGame.getPlayer2Points()); // Player 2: 0 points

        tennisService.addPoint(tennisGame, "A"); // Player 1: 7-0 (wins tie-break)

        assertEquals(1, tennisGame.getPlayer1Sets());   // Player 1 wins the set
        assertEquals(0, tennisGame.getPlayer2Sets());   // Player 2: 0 sets
        assertEquals(0, tennisGame.getPlayer1Games());  // Games reset
        assertEquals(0, tennisGame.getPlayer2Games());  // Games reset
    }

    // Test a complex sequence of points to ensure the match progresses correctly
    @Test
    void getWinner_should_return_player1_winner() {
        TennisMatch tennisMatch = new TennisMatch(player1, player2);

        String sequence = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBAAAAAAAAAAAAAAAAA";

        for (char c : sequence.toCharArray()) {
            String playerCode = String.valueOf(c);
            tennisService.addPoint(tennisMatch, playerCode);
        }

        assertEquals(2, tennisMatch.getPlayer1Sets()); // Player 1: 2 sets
        assertEquals(0, tennisMatch.getPlayer2Sets()); // Player 2: 0 sets
        assertEquals(1, tennisMatch.getPlayer1Games()); // Player 1: 1 game
        assertEquals(0, tennisMatch.getPlayer2Games()); // Player 2: 0 games

        assertDoesNotThrow(() -> {
            Player winner = tennisService.getWinner(tennisMatch);
            assertEquals(player1, winner); // Player 1 is the winner
        });
    }

    // Test if the initial score is correctly returned
    @Test
    void testGetScoreInitialState() {
        String expectedScore = "Player    : A-B\n" +
                "Sets      : 0-0\n" +
                "Games     : 0-0\n" +
                "Player A  : 0/ Player B : 0\n";
        assertEquals(expectedScore.trim(), tennisService.getScore(tennisGame).trim());
    }

    // Test if the score is correctly returned after some points
    @Test
    void getScore_should_return_PlayerA_30_PlayerB_15() {
        tennisService.addPoint(tennisGame, "A"); // Player 1: 15-0
        tennisService.addPoint(tennisGame, "A"); // Player 1: 30-0
        tennisService.addPoint(tennisGame, "B"); // Player 2: 30-15

        String expectedScore = "Player    : A-B\n" +
                "Sets      : 0-0\n" +
                "Games     : 0-0\n" +
                "Player A  : 30/ Player B : 15\n";
        assertEquals(expectedScore.trim(), tennisService.getScore(tennisGame).trim());
    }

    // Test if the score is correctly returned after some games
    @Test
    void getScore_should_return_Games1_0() {
        tennisGame.setPlayer1Games(1); // Player 1: 1 game
        tennisGame.setPlayer2Games(0); // Player 2: 0 games

        String expectedScore = "Player    : A-B\n" +
                "Sets      : 0-0\n" +
                "Games     : 1-0\n" +
                "Player A  : 0/ Player B : 0\n";
        assertEquals(expectedScore.trim(), tennisService.getScore(tennisGame).trim());
    }

    // Test if the score is correctly returned after some sets
    @Test
    void getScore_should_return_Sets1_0() {
        tennisGame.setPlayer1Sets(1); // Player 1: 1 set
        tennisGame.setPlayer2Sets(0); // Player 2: 0 sets

        String expectedScore = "Player    : A-B\n" +
                "Sets      : 1-0\n" +
                "Games     : 0-0\n" +
                "Player A  : 0/ Player B : 0\n";
        assertEquals(expectedScore.trim(), tennisService.getScore(tennisGame).trim());
    }

    // Test if the score is correctly returned during a tie-break
    @Test
    void getScore_should_return_TieBreak() {
        tennisGame.setPlayer1Games(6); // Player 1: 6 games
        tennisGame.setPlayer2Games(6); // Player 2: 6 games
        tennisGame.setTieBreak(true);  // Tie-break activated
        tennisGame.setPlayer1Points(7); // Player 1: 7 points
        tennisGame.setPlayer2Points(5); // Player 2: 5 points

        String expectedScore = "Player    : A-B\n" +
                "Sets      : 0-0\n" +
                "Games     : 6-6\n" +
                "Tie-break : 7-5\n";
        assertEquals(expectedScore.trim(), tennisService.getScore(tennisGame).trim());
    }

    // Test if the score is correctly returned when a player has advantage
    @Test
    void getScore_should_return_Advantage_Player1() {
        tennisGame.setPlayer1Points(4); // Player 1: Advantage
        tennisGame.setPlayer2Points(3); // Player 2: 40

        String expectedScore = "Player    : A-B\n" +
                "Sets      : 0-0\n" +
                "Games     : 0-0\n" +
                "Player A  : Advantage/ Player B : 40\n";
        assertEquals(expectedScore.trim(), tennisService.getScore(tennisGame).trim());
    }

    // Test if the winner is correctly determined
    @Test
    void getWinner_should_return_Player2_is_the_winner() {
        tennisGame.setPlayer1Sets(2); // Player 1: 2 sets
        assertDoesNotThrow(() -> {
            Player winner = tennisService.getWinner(tennisGame);
            assertEquals(player1, winner); // Player 1 is the winner
        });

        tennisGame.setPlayer1Sets(1); // Player 1: 1 set
        tennisGame.setPlayer2Sets(2); // Player 2: 2 sets
        assertDoesNotThrow(() -> {
            Player winner = tennisService.getWinner(tennisGame);
            assertEquals(player2, winner); // Player 2 is the winner
        });

        tennisGame.setPlayer1Sets(1); // Player 1: 1 set
        tennisGame.setPlayer2Sets(1); // Player 2: 1 set
        assertThrows(TennisBusinessException.class, () -> tennisService.getWinner(tennisGame));
    }
}