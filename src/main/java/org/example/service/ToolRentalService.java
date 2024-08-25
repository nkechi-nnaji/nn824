package org.example.service;

import org.example.model.Tool;
import org.example.model.ToolType;
import org.example.repository.ToolInMemoryRepository;

import java.util.Optional;

public class ToolRentalService {
    private ToolInMemoryRepository inMemoryRepository = ToolInMemoryRepository.geInstance();

    public boolean addToolType(ToolType toolType) {
        return Optional.ofNullable(toolType)
                .map(inMemoryRepository::addToolType)
                .orElseThrow(() -> new IllegalArgumentException("ToolType cannot be null"));
    }

    public boolean addTool(Tool tool) {
        return Optional.ofNullable(tool)
                .map(inMemoryRepository::addTool)
                .orElseThrow(() -> new IllegalArgumentException("Tool cannot be null"));
    }

    public Tool getTool(String code) {
        return Optional.ofNullable(code).map(inMemoryRepository::getTool)
                .orElseThrow(() -> new IllegalArgumentException("Tool code cannot be null"));
    }

    public ToolType getToolType(String toolType) {
        return Optional.ofNullable(toolType).map(inMemoryRepository::getToolType)
                .orElseThrow(() -> new IllegalArgumentException("ToolType cannot be null"));
    }
}


