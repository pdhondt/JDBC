package be.vdab;

import be.vdab.exceptions.SoortBestaatAlException;
import be.vdab.repositories.LeverancierRepository;
import be.vdab.repositories.PlantRepository;
import be.vdab.repositories.SoortRepository;

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
        /*System.out.print("id: ");
        var scanner = new Scanner(System.in);
        var id = scanner.nextLong();
        var repository = new LeverancierRepository();
        try {
            repository.findById(id)
                    .ifPresentOrElse(System.out::println, () -> System.out.println("Niet gevonden"));
        }
        catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*System.out.print("Woord: ");
        var scanner = new Scanner(System.in);
        var woord = scanner.nextLine();
        var repository = new PlantRepository();
        try {
            repository.findNamenByWoord(woord).forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        /*var repository = new PlantRepository();
        try {
            repository.verhoogPrijzenBovenEnOnder100€();
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }*/
        System.out.print("Naam: ");
        var scanner = new Scanner(System.in);
        var naam = scanner.nextLine();
        var repository = new SoortRepository();
        try {
            repository.create(naam);
            System.out.println("Soort toegevoegd.");
        } catch (SoortBestaatAlException ex) {
            System.out.println("Soort bestaat al.");
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}