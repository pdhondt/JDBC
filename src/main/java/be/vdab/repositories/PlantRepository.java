package be.vdab.repositories;

import be.vdab.exceptions.PlantNietGevondenException;
import be.vdab.exceptions.PrijsTeLaagException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlantRepository extends AbstractRepository {
    public int verhoogPrijzenMet10Procent() throws SQLException {
        var sql = """
                update planten
                set prijs = prijs * 1.1
                """;
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            return statement.executeUpdate();
        }
    }

    public int verhoogPrijzenMet10ProcentByNaam(String naam) throws SQLException {
        var sql = """
                update planten
                set prijs = prijs * 1.1
                where naam = ?
                """;
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            statement.setString(1, naam);
            return statement.executeUpdate();
        }
    }

    public List<String> findNamenByWoord(String woord) throws SQLException {
        var namen = new ArrayList<String>();
        try (var connection = super.getConnection();
             var statement = connection.prepareCall("{call PlantNamenMetEenWoord(?)}")) {
            statement.setString(1, '%' + woord + '%');
            for (var result = statement.executeQuery(); result.next(); ) {
                namen.add(result.getString("naam"));
            }
            return namen;
        }
    }

    public void verhoogPrijzenBovenEnOnder100€() throws SQLException {
        var sqlVanaf100 = """
                update planten
                set prijs = prijs * 1.1
                where prijs >= 100
                """;
        var sqlTot100 = """
                update planten
                set prijs = prijs * 1.05
                where prijs < 100
                """;
        try (var connection = super.getConnection();
             var statementVanaf100 = connection.prepareStatement(sqlVanaf100);
             var statementTot100 = connection.prepareStatement(sqlTot100)) {
            connection.setAutoCommit(false);
            statementVanaf100.executeUpdate();
            statementTot100.executeUpdate();
            connection.commit();
        }
    }

    public void verlaagPrijs(long id, BigDecimal nieuwePrijs) throws SQLException {
        var sqlUpdate = """
                update planten
                set prijs = ?
                where id = ? and ? > prijs / 2
                """;
        try (var connection = super.getConnection();
             var statementUpdate = connection.prepareStatement(sqlUpdate)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            statementUpdate.setBigDecimal(1, nieuwePrijs);
            statementUpdate.setLong(2, id);
            statementUpdate.setBigDecimal(3, nieuwePrijs);
            var aantalAangepast = statementUpdate.executeUpdate();
            if (aantalAangepast == 1) {
                connection.commit();
                return;
            }
            var sqlSelect = """
                    select count(*) as aantal
                    from planten
                    where id = ?
                    """;
            try (var statementSelect = connection.prepareStatement(sqlSelect)) {
                statementSelect.setLong(1, id);
                var result = statementSelect.executeQuery();
                result.next();
                if (result.getLong("aantal") == 0) {
                    connection.rollback();
                    throw new PlantNietGevondenException();
                }
                connection.rollback();
                throw new PrijsTeLaagException();
            }
        }
    }
    public List<String> findNamenByIds(Set<Long> ids) throws SQLException {
        if (ids.isEmpty()) {
            return List.of();
        }
        var namen = new ArrayList<String>();
        var sql = """
                select naam
                from planten
                where id in (
                """
                + "?,".repeat(ids.size() - 1)
                + "?)";
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql)) {
            var index = 1;
            for (var id : ids) {
                statement.setLong(index++, id);
            }
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            var result = statement.executeQuery();
            while (result.next()) {
                namen.add(result.getString("naam"));
            }
            connection.commit();
            return namen;
        }
    }
}