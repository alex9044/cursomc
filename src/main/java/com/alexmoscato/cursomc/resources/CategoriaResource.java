package com.alexmoscato.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.alexmoscato.cursomc.domain.Categoria;
import com.alexmoscato.cursomc.dtos.CategoriaDTO;
import com.alexmoscato.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // Uri de dominio da coleção
public class CategoriaResource {

	@Autowired
	private CategoriaService service;// Instanciação da classe de serviços.

	// Criando uri para consultar uma categoria
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria objeto = service.findId(id);
		return ResponseEntity.ok().body(objeto);
	}

	// Criando uri para consultar todas as categorias
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();
		// Convertendo lista para lista
		List<CategoriaDTO> listDto = list.stream().map(objeto -> new CategoriaDTO(objeto)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	// Criando uri para consultar uma page de categorias
		@RequestMapping(value="/page", method = RequestMethod.GET)
		public ResponseEntity<Page<CategoriaDTO>> findPage(
				@RequestParam(value = "page", defaultValue = "0") Integer page, 
				@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
				@RequestParam(value = "ordeyBy", defaultValue = "nome") String orderBy, 
				@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
			Page<Categoria> list = service.findPage(page,linesPerPage,orderBy,direction);
			// Convertendo lista para lista
			Page<CategoriaDTO> listDto = list.map(objeto -> new CategoriaDTO(objeto));
			return ResponseEntity.ok().body(listDto);
		}

	// Crindo uri para inserir uma nova categoria
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objetoDTO) {
		Categoria objeto = service.fromDTO(objetoDTO);
		objeto = service.insert(objeto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(objeto.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	// Crindo uri para atualizar uma categoria
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objetoDTO, @PathVariable Integer id) {
		Categoria objeto = service.fromDTO(objetoDTO);
		objeto.setId(id);
		objeto = service.update(objeto);
		return ResponseEntity.noContent().build();
	}

	// Criando uri para eliminar uma categoria
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	

}
