package com.tennisKata.model;

public class TennisMatch {

    private Player player1;
    private Player player2;
    private int player1Sets;
    private int player2Sets;
    private int player1Games;
    private int player2Games;
    private int player1Points;
    private int player2Points;
    private boolean isTieBreak;

    public TennisMatch(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getPlayer1Sets() {
        return player1Sets;
    }

    public void setPlayer1Sets(int player1Sets) {
        this.player1Sets = player1Sets;
    }

    public int getPlayer2Sets() {
        return player2Sets;
    }

    public void setPlayer2Sets(int player2Sets) {
        this.player2Sets = player2Sets;
    }

    public int getPlayer1Games() {
        return player1Games;
    }

    public void setPlayer1Games(int player1Games) {
        this.player1Games = player1Games;
    }

    public int getPlayer2Games() {
        return player2Games;
    }

    public void setPlayer2Games(int player2Games) {
        this.player2Games = player2Games;
    }

    public int getPlayer1Points() {
        return player1Points;
    }

    public void setPlayer1Points(int player1Points) {
        this.player1Points = player1Points;
    }

    public int getPlayer2Points() {
        return player2Points;
    }

    public void setPlayer2Points(int player2Points) {
        this.player2Points = player2Points;
    }

    public boolean isTieBreak() {
        return isTieBreak;
    }

    public void setTieBreak(boolean tieBreak) {
        isTieBreak = tieBreak;
    }

}