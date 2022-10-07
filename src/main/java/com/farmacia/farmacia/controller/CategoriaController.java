package com.farmacia.farmacia.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.farmacia.farmacia.model.Categoria;
import com.farmacia.farmacia.repository.CategoriaRepository;

@RestController
@RequestMapping("/categoria")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaRepository CategoriaRepository;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(CategoriaRepository.findAll());
	}

	@GetMapping("{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id) {
		return CategoriaRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/Descricao/{Descricao}")
	public ResponseEntity<List<Categoria>> getByDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok(CategoriaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@GetMapping("/Tipo/{Tipo}")
	public ResponseEntity<List<Categoria>> getByTipo(@PathVariable String tipo) {
		return ResponseEntity.ok(CategoriaRepository.findAllByTipoContainingIgnoreCase(tipo));
	}
	@PostMapping
	public ResponseEntity<Categoria> post(@Valid @RequestBody Categoria Categoria) {
		return ResponseEntity.status(HttpStatus.CREATED).body(CategoriaRepository.save(Categoria));
	}

	@PutMapping
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria Categoria) {
		return CategoriaRepository.findById(Categoria.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(CategoriaRepository.save(Categoria)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Categoria> Categoria = CategoriaRepository.findById(id);

		if (Categoria.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		CategoriaRepository.deleteById(id);
	}

}
