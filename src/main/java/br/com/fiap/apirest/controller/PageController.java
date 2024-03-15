package br.com.fiap.apirest.controller;

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

import br.com.fiap.apirest.model.Page;
import br.com.fiap.apirest.repository.PageRepository;

@RestController
@RequestMapping("page")
public class PageController {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    PageRepository pageRepository;

    @GetMapping
    public List<Page> index() {
        return pageRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Page create(@RequestBody Page page) {
        log.info("cadastrando página: {}", page);
        pageRepository.save(page);
        return page;
    }

    @GetMapping("{id}")
    public ResponseEntity<Page> get(@PathVariable Long id) {
        log.info("Buscar por id: {}", id);

        return pageRepository
                    .findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());

    }


    @DeleteMapping("{id}")
    public ResponseEntity<Object> destroy(@PathVariable Long id) {
        log.info("apagando pagina {}", id);

        verificarSeExistePage(id);

        pageRepository.deleteById(id);
        return ResponseEntity.noContent().build();

    }


    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Page page){
        log.info("atualizando pagina id {} para {}", id, page);
        
        verificarSeExistePage(id);

        page.setId(id);
        pageRepository.save(page);
        return ResponseEntity.ok(page);
    }

    
    private void verificarSeExistePage(Long id) {
        pageRepository
            .findById(id)
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Página não encontrada" )
            );
    }


}
