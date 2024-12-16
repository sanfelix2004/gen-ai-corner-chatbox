package com.corner.ai.controller;

import com.corner.ai.delegate.MetadataControllerDelegate;
import com.corner.ai.service.DatabaseMetadataService;
import com.corner.ai.service.DatabaseMetadataService.TableMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;
@RestController
@RequestMapping("/metadata")
public class MetadataController {

    private final MetadataControllerDelegate delegate;

    public MetadataController(MetadataControllerDelegate delegate) {
        this.delegate = delegate;
    }

    @GetMapping
    public ResponseEntity<Map<String, DatabaseMetadataService.TableMetadata>> getMetadata() {
        try {
            return ResponseEntity.ok(delegate.fetchMetadata());
        } catch (SQLException e) {
            return ResponseEntity.status(500).build();
        }
    }
}