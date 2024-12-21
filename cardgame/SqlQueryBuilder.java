package cardgame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class SqlQueryBuilder {
    private StringBuilder query;
    private List<Object> parameters;

    // Constructor
    public SqlQueryBuilder() {
        this.query = new StringBuilder();
        this.parameters = new ArrayList<>();
    }

    // Add INSERT INTO clause
    public SqlQueryBuilder insertInto(String table, String... columns) {
        query.append("INSERT INTO ").append(table).append(" (");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) query.append(", ");
        }
        query.append(") VALUES (");
        for (int i = 0; i < columns.length; i++) {
            query.append("?");
            if (i < columns.length - 1) query.append(", ");
        }
        query.append(") ");
        return this;
    }

    // Add UPDATE clause
    public SqlQueryBuilder update(String table) {
        query.append("UPDATE ").append(table).append(" SET ");
        return this;
    }

    // Add SET clause for UPDATE
    public SqlQueryBuilder set(String column) {
        if (query.toString().contains(" SET ") && !query.toString().endsWith(" SET ")) {
            query.append(", ");
        }
        query.append(column).append(" = ?");
        return this;
    }

    // Add WHERE clause
    public SqlQueryBuilder where(String condition) {
        query.append(" WHERE ").append(condition).append(" ");
        return this;
    }

    // Add AND condition
    public SqlQueryBuilder and(String condition) {
        query.append(" AND ").append(condition).append(" ");
        return this;
    }

    // Add SELECT clause
    public SqlQueryBuilder select(String... columns) {
        query.append("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) query.append(", ");
        }
        query.append(" ");
        return this;
    }

    // Add FROM clause
    public SqlQueryBuilder from(String table) {
        query.append("FROM ").append(table).append(" ");
        return this;
    }

    // Add a parameter
    public SqlQueryBuilder addParameter(Object parameter) {
        parameters.add(parameter);
        return this;
    }

    // Build the PreparedStatement
    public PreparedStatement build(Connection connection) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(query.toString());
        for (int i = 0; i < parameters.size(); i++) {
            preparedStatement.setObject(i + 1, parameters.get(i));
        }
        return preparedStatement;
    }

    // Debugging Helpers
    public String getQuery() {
        return query.toString();
    }

    public List<Object> getParameters() {
        return parameters;
    }
}
