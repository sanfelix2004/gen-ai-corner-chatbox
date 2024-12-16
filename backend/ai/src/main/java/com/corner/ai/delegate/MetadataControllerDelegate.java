package com.corner.ai.delegate;

import com.corner.ai.service.DatabaseMetadataService;
import com.corner.ai.service.DatabaseMetadataService.TableMetadata;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class MetadataControllerDelegate {

    private final DatabaseMetadataService metadataService;

    public MetadataControllerDelegate(DatabaseMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    public Map<String, TableMetadata> fetchMetadata() throws SQLException {
        return metadataService.getDatabaseMetadataWithData();
    }
}