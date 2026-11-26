package com.dest1.steam.controllers;

import com.dest1.steam.entities.Jogo;
import com.dest1.steam.repositories.JogoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jogos")
public class JogoController {

    @Autowired
    JogoRepository repository;

    @GetMapping
    public List<Jogo> mostrar() {
        return repository.findAll();
    }

    @PostMapping
    public Jogo criar(@RequestBody Jogo jogo) {
        return repository.save(jogo);
    }
}