package be.vdab.repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            for (var result = statement.executeQuery(); result.next();) {
                namen.add(result.getString("naam"));
            }
            return namen;
        }
    }
}
