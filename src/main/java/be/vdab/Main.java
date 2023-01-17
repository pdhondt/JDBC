package be.vdab;

import be.vdab.repositories.LeverancierRepository;
import be.vdab.repositories.PlantRepository;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*System.out.print("Woonplaats: ");
        var scanner = new Scanner(System.in);
        var woonplaats = scanner.nextLine();
        var repository = new LeverancierRepository();
        try {
            repository.findByWoonplaats(woonplaats).forEach(System.out::println);
            System.out.println("\nNamen van alle leveranciers:");
            repository.findAllNamen().forEach(System.out::println);
            System.out.print("\nAantal leveranciers: ");
            System.out.println(repository.findAantal());
            System.out.println("\nAantal jaar leverancier:");
            repository.findAll().forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*System.out.print("Naam plant: ");
        var scanner = new Scanner(System.in);
        var naam = scanner.nextLine();
        var repository = new PlantRepository();
        try {
            System.out.print(repository.verhoogPrijzenMet10ProcentByNaam(naam));
            System.out.println(" plant(en) aangepast.");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        System.out.print("id: ");
        var scanner = new Scanner(System.in);
        var id = scanner.nextLong();
        var repository = new LeverancierRepository();
        try {
            repository.findById(id)
                    .ifPresentOrElse(System.out::println, () -> System.out.println("Niet gevonden"));
        }
        catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}