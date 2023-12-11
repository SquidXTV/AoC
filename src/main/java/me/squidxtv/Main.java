package me.squidxtv;

import me.squidxtv.util.Day;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {

    private static Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) throws URISyntaxException, IOException {
        System.out.println("Which year should be executed?");
        int year = Integer.parseInt(SCANNER.nextLine());
        switch (year) {
            case 2023 -> y2023();
            default -> throw new IllegalStateException("Year not existing: " + year);
        }
    }

    private static void y2023() throws URISyntaxException, IOException {
        System.out.println("Which day should be executed?");
        int day = Integer.parseInt(SCANNER.nextLine());
        Day d = switch (day) {
            case 1 -> new me.squidxtv.y2023.Day01();
            case 2 -> new me.squidxtv.y2023.Day02();
            case 3 -> new me.squidxtv.y2023.Day03();
            case 4 -> new me.squidxtv.y2023.Day04();
            case 5 -> new me.squidxtv.y2023.Day05();
            case 6 -> new me.squidxtv.y2023.Day06();
            case 7 -> new me.squidxtv.y2023.Day07();
            case 8 -> new me.squidxtv.y2023.Day08();
            case 9 -> new me.squidxtv.y2023.Day09();
            case 10 -> new me.squidxtv.y2023.Day10();
            case 11 -> new me.squidxtv.y2023.Day11();
            default -> throw new IllegalStateException("Day not existing: " + day);
        };
        d.execute();
    }

}
