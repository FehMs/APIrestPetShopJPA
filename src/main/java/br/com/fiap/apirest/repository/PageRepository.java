package br.com.fiap.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.apirest.model.Page;


public interface PageRepository extends JpaRepository<Page, Long> {


}