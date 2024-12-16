package com.corner.ai.service;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DatabaseMetadataService {

    private final DataSource dataSource;

    public DatabaseMetadataService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Map<String, TableMetadata> getDatabaseMetadataWithData() throws SQLException {
        Map<String, TableMetadata> metadataMap = new HashMap<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            // Recupera tutte le tabelle
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                TableMetadata tableMetadata = new TableMetadata(tableName);

                // Recupera le colonne della tabella
                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                while (columns.next()) {
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    tableMetadata.addColumn(columnName, columnType);
                }

                // Recupera le chiavi primarie
                ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
                while (primaryKeys.next()) {
                    String primaryKey = primaryKeys.getString("COLUMN_NAME");
                    tableMetadata.addPrimaryKey(primaryKey);
                }

                // Recupera i dati della tabella
                tableMetadata.setData(getTableData(connection, tableName));

                metadataMap.put(tableName, tableMetadata);
            }
        }

        return metadataMap;
    }

    private List<Map<String, Object>> getTableData(Connection connection, String tableName) throws SQLException {
        List<Map<String, Object>> rows = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = resultSetMetaData.getColumnName(i);
                    Object value = resultSet.getObject(i);
                    row.put(columnName, value);
                }
                rows.add(row);
            }
        }

        return rows;
    }

    public static class TableMetadata {
        private final String tableName;
        private final List<String> primaryKeys = new ArrayList<>();
        private final List<ColumnMetadata> columns = new ArrayList<>();
        private List<Map<String, Object>> data = new ArrayList<>();

        public TableMetadata(String tableName) {
            this.tableName = tableName;
        }

        public void addPrimaryKey(String primaryKey) {
            primaryKeys.add(primaryKey);
        }

        public void addColumn(String name, String type) {
            columns.add(new ColumnMetadata(name, type));
        }

        public String getTableName() {
            return tableName;
        }

        public List<String> getPrimaryKeys() {
            return primaryKeys;
        }

        public List<ColumnMetadata> getColumns() {
            return columns;
        }

        public List<Map<String, Object>> getData() {
            return data;
        }

        public void setData(List<Map<String, Object>> data) {
            this.data = data;
        }
    }

    public static class ColumnMetadata {
        private final String name;
        private final String type;

        public ColumnMetadata(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }
}