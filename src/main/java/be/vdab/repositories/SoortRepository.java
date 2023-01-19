package be.vdab.repositories;

import be.vdab.exceptions.SoortBestaatAlException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SoortRepository extends AbstractRepository {
    public long create(String naam) throws SQLException {
        var sqlInsert = """
                    insert into soorten(naam)
                    values (?)
                    """;
        try (var connection = super.getConnection();
            var statementInsert = connection.prepareStatement(sqlInsert, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statementInsert.setString(1, naam);
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            try {
                statementInsert.executeUpdate();
                var result = statementInsert.getGeneratedKeys();
                result.next();
                var nieuweId = result.getLong(1);
                connection.commit();
                return nieuweId;
            } catch (SQLException ex) {
                var sqlSelect = """
                select id
                from soorten
                where naam = ?
                """;
                try (var statementSelect = connection.prepareStatement(sqlSelect)) {
                    statementSelect.setString(1, naam);
                    if (statementSelect.executeQuery().next()) {
                        connection.commit();
                        throw new SoortBestaatAlException();
                    }
                    connection.commit();
                    throw ex;
                }
            }
        }
    }
    public List<Long> create(List<String> namen) throws SQLException {
        var sql = """
                insert into soorten(naam)
                values (?)
                """;
        try (var connection = super.getConnection();
            var statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            connection.setAutoCommit(false);
            for (String naam : namen) {
                statement.setString(1, naam);
                statement.addBatch();
            }
            var ids = new ArrayList<Long>();
            statement.executeBatch();
            var result = statement.getGeneratedKeys();
            while (result.next()) {
                ids.add(result.getLong(1));
            }
            connection.commit();
            return ids;
        }
    }
}
