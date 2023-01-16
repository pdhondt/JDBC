package be.vdab;

import be.vdab.repositories.LeverancierRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        var repository = new LeverancierRepository();
        try {
            repository.findAllNamen().forEach(System.out::println);
        } catch (SQLException ex) {
            ex.printStackTrace(System.err);
        }
    }
}