package com.tennisKata;


import com.tennisKata.model.Player;
import com.tennisKata.model.TennisMatch;
import com.tennisKata.exception.TennisBusinessException;
import com.tennisKata.service.impl.TennisServiceImpl;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //initialization service
        var tennisService = new TennisServiceImpl();

        //initialization players
        var scanner = new Scanner(System.in);
        System.out.println("Enter player name 1 : ");
        var inputPlayer1 = scanner.nextLine();
        var player1 = new Player("A", inputPlayer1);

        System.out.println("Enter player name 2 : ");
        var inputPlayer2 = scanner.nextLine();
        var player2 = new Player("B", inputPlayer2);

        //Simulate a match
        var match = new TennisMatch(player1, player2);
        String sequencePoint;
        while (true) {
            System.out.println("Enter the sequence of points (A for " + player1.getName() + ", B for " + player2.getName() + ") : ");
            sequencePoint = scanner.nextLine().toUpperCase();

            if (tennisService.isValidSequence(sequencePoint)) {
                break;
            } else {
                System.out.println("The sequence of points is not valid. Please try again.");
            }

        }
        scanner.close();

        //Show score
        (List.of(sequencePoint.split(""))).forEach(player -> {
            tennisService.addPoint(match, player);
            System.out.println(tennisService.getScore(match));
        });

        // Show final score
        System.out.println(tennisService.getScore(match));
        try {
            System.out.println( tennisService.getWinner(match).getName()+" wins the match");
        } catch (TennisBusinessException e) {
            System.out.println(e.getMessage());
        }
    }

}