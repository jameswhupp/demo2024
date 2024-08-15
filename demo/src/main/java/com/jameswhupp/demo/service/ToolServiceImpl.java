package com.jameswhupp.demo.service;

import java.util.Optional;

import com.jameswhupp.demo.data.ToolRepository;
import com.jameswhupp.demo.model.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolServiceImpl implements ToolService {
    @Autowired
    ToolRepository toolRepository;

    @Override
    public Tool getToolByToolCode(String toolCode) {
        Optional<Tool> opt = toolRepository.findByToolCode(toolCode);
        return opt.orElse(null);
    }
}