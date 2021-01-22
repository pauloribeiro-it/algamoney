package com.example.algamoney.api.resource;

import com.example.algamoney.api.model.Estado;
import com.example.algamoney.api.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private EstadoRepository repository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Estado> listar(){
        return repository.findAll();
    }
}
