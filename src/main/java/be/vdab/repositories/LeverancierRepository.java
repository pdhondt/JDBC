package be.vdab.repositories;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeverancierRepository extends AbstractRepository {
    public List<String> findAllNamen() throws SQLException {
        var sql = """
                select naam
                from leveranciers
                """;
        var namen = new ArrayList<String>();
        try (var connection = super.getConnection();
             var statement = connection.prepareStatement(sql)) {
            var result = statement.executeQuery();
            while (result.next()) {
                namen.add(result.getString("naam"));
            }
            return namen;
        }
    }
}
