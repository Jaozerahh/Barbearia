package br.com.fiap.barbearia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.barbearia.model.Barbearia;
import br.com.fiap.barbearia.repository.BarbeariaRepository;

@RestController
@RequestMapping("barbearia")
public class BarbeariaController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    BarbeariaRepository barbeariaRepository;

    @GetMapping
    public List<Barbearia> index() {
        return barbeariaRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Barbearia create(@RequestBody Barbearia barbearia) {
        log.info("cadastrando barbearia: {}", barbearia);
        barbeariaRepository.save(barbearia);
        return barbearia;
    }

    @GetMapping("{id}")
    public ResponseEntity<Barbearia> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return barbeariaRepository
                    .findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("apagando barbearia {}", id);

        verificarSeExisteBarbearia(id);

        barbeariaRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }


    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Barbearia barbearia){
        log.info("atualizando barbearia id {} para {}", id, barbearia);
        
        verificarSeExisteBarbearia(id);

        barbearia.setId(id);
        barbeariaRepository.save(barbearia);
        return ResponseEntity.ok(barbearia);
    }

    
    private void verificarSeExisteBarbearia(Long id) {
        barbeariaRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Barbearia não encontrada" )
            );
    }


}
