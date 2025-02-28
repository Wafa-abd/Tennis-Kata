package com.tennisKata.service;


import com.tennisKata.model.Player;
import com.tennisKata.model.TennisMatch;
import com.tennisKata.exception.TennisBusinessException;

public interface TennisService {

    boolean isValidSequence(String sequencePoint);

    TennisMatch addPoint(TennisMatch tennisGame, String code) throws IllegalArgumentException;

    String getScore(TennisMatch tennisGame);

    Player getWinner(TennisMatch tennisGame) throws TennisBusinessException;

}
