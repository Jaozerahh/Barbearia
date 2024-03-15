package br.com.fiap.barbearia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.barbearia.model.Barbearia;


public interface BarbeariaRepository extends JpaRepository<Barbearia, Long> {


}