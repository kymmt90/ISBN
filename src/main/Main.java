package main;

import isbn.Isbn;

public class Main {
    public static void main(String[] args) {
        Isbn ej = Isbn.of("978-4-621-06605-8");
        Isbn ej2 = Isbn.of("9784621066058");
        System.out.println(ej + ": " + ej.getLinguisticArea());
        System.out.println(ej2 + ": " + ej2.getLinguisticArea());
    }
}
