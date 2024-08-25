package org.example.repository;

import org.example.model.Tool;
import org.example.model.ToolType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ToolInMemoryRepository {
    private final Map<String, Tool> tools = new HashMap<>();
    private final Map<String, ToolType> toolTypes = new HashMap<>();

    private static final ToolInMemoryRepository inMemoryRepository = new ToolInMemoryRepository();

    public static ToolInMemoryRepository geInstance() {
        return inMemoryRepository;
    }


    public boolean addTool(final Tool tool) {
        if (!toolTypes.containsKey(tool.getType())) {
            throw new IllegalArgumentException("Tool type " + tool.getType() + " is not supported");
        }
        tools.put(tool.getCode(), tool);
        return true;
    }

    public boolean addToolType(final ToolType toolType) {
        toolTypes.put(toolType.getName(), toolType);
        return true;
    }

    public Tool getTool(String code){
        return Optional.ofNullable(tools.get(code))
                .orElseThrow(() -> new IllegalArgumentException("Tool not found"));
    }

    public ToolType getToolType(String code){
        return Optional.ofNullable(toolTypes.get(code))
                .orElseThrow(() -> new IllegalArgumentException("Tool not found"));
    }
}
