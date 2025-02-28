package com.tennisKata.service.impl;

import com.tennisKata.exception.TennisBusinessException;
import com.tennisKata.model.Player;
import com.tennisKata.model.TennisMatch;
import com.tennisKata.service.TennisService;

public class TennisServiceImpl implements TennisService {
    private final int SETS_TO_WIN = 2;
    @Override
    public boolean isValidSequence(String sequencePoint) {
        return sequencePoint != null && sequencePoint.matches("^[AB]+$");
    }

    @Override
    public TennisMatch addPoint(TennisMatch tennisGame, String code) {
        if (code.equals(tennisGame.getPlayer1().getCode())) {
            tennisGame.setPlayer1Points(tennisGame.getPlayer1Points() + 1);
        } else if (code.equals(tennisGame.getPlayer2().getCode())) {
            tennisGame.setPlayer2Points(tennisGame.getPlayer2Points() + 1);
        } else {
            throw new IllegalArgumentException("Unknown player : " + code);
        }
        this.checkGame(tennisGame);
        return tennisGame;
    }

    private void checkGame(TennisMatch tennisGame) {
        if (tennisGame.isTieBreak()) {
            this.checkTieBreak(tennisGame);
        } else {
            if (tennisGame.getPlayer1Points() >= 4 && tennisGame.getPlayer1Points() >= tennisGame.getPlayer2Points() + 2) {
                tennisGame.setPlayer1Games(tennisGame.getPlayer1Games() + 1);
                this.resetPoints(tennisGame);
                this.checkSet(tennisGame);
            } else if (tennisGame.getPlayer2Points() >= 4 && tennisGame.getPlayer2Points() >= tennisGame.getPlayer1Points() + 2) {
                tennisGame.setPlayer2Games(tennisGame.getPlayer2Games() + 1);
                this.resetPoints(tennisGame);
                this.checkSet(tennisGame);
            }
        }

    }

    @Override
    public String getScore(TennisMatch tennisGame) {
        var score = new StringBuilder();
        score.append("Player    : ").append(tennisGame.getPlayer1().getCode()).append("-").append(tennisGame.getPlayer2().getCode()).append("\n");
        score.append("Sets      : ").append(tennisGame.getPlayer1Sets()).append("-").append(tennisGame.getPlayer2Sets()).append("\n");
        score.append("Games     : ").append(tennisGame.getPlayer1Games()).append("-").append(tennisGame.getPlayer2Games()).append("\n");
        if (tennisGame.isTieBreak()) {
            score.append("Tie-break : ").append(tennisGame.getPlayer1Points()).append("-").append(tennisGame.getPlayer2Points()).append("\n");
        } else {
            score.append("Player ").append(tennisGame.getPlayer1().getCode()).append("  : ").append(translatePoints(tennisGame.getPlayer1Points())).append("/ Player ").append(tennisGame.getPlayer2().getCode()).append(" : ").append(translatePoints(tennisGame.getPlayer2Points())).append("\n");

        }
        return score.toString();
    }

    @Override
    public Player getWinner(TennisMatch tennisGame) throws TennisBusinessException {
        if (tennisGame.getPlayer1Sets() >= SETS_TO_WIN) {
            return tennisGame.getPlayer1();
        } else if (tennisGame.getPlayer2Sets() >= SETS_TO_WIN) {
            return tennisGame.getPlayer2();
        } else {
            throw new TennisBusinessException("Match in progress");
        }
    }

    String translatePoints(int points) {
        switch (points) {
            case 0:
                return "0";
            case 1:
                return "15";
            case 2:
                return "30";
            case 3:
                return "40";
            default:
                return "Advantage";
        }
    }

    private void checkSet(TennisMatch tennisGame) {
        if (tennisGame.getPlayer1Games() >= 6 && tennisGame.getPlayer1Games() >= tennisGame.getPlayer2Games() + 2) {
            tennisGame.setPlayer1Sets(tennisGame.getPlayer1Sets() + 1);
            this.resetGames(tennisGame);
        } else if (tennisGame.getPlayer2Games() >= 6 && tennisGame.getPlayer2Games() >= tennisGame.getPlayer1Games() + 2) {
            tennisGame.setPlayer2Sets(tennisGame.getPlayer2Sets() + 1);
            this.resetGames(tennisGame);
        } else if (tennisGame.getPlayer1Games() == 6 && tennisGame.getPlayer2Games() == 6) {
            tennisGame.setTieBreak(true);
        }

    }

    private void resetGames(TennisMatch tennisGame) {
        tennisGame.setPlayer1Games(0);
        tennisGame.setPlayer2Games(0);
    }

    void checkTieBreak(TennisMatch tennisGame) {
        if (tennisGame.getPlayer1Points() >= 7 && tennisGame.getPlayer1Points() >= tennisGame.getPlayer2Points() + 2) {
            tennisGame.setPlayer1Sets(tennisGame.getPlayer1Sets() + 1);
            resetGames(tennisGame);
            resetPoints(tennisGame);
            tennisGame.setTieBreak(false);
        } else if (tennisGame.getPlayer2Points() >= 7 && tennisGame.getPlayer2Points() >= tennisGame.getPlayer1Points() + 2) {
            tennisGame.setPlayer2Sets(tennisGame.getPlayer2Sets() + 1);
            resetGames(tennisGame);
            resetPoints(tennisGame);
            tennisGame.setTieBreak(false);
        }
    }

    public void resetPoints(TennisMatch tennisGame) {
        tennisGame.setPlayer1Points(0);
        tennisGame.setPlayer2Points(0);
    }

}
