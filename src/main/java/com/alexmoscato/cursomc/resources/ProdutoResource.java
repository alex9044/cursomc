package com.alexmoscato.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.alexmoscato.cursomc.domain.Produto;
import com.alexmoscato.cursomc.dtos.ProdutoDTO;
import com.alexmoscato.cursomc.resources.utils.URL;
import com.alexmoscato.cursomc.services.ProdutoService;

@RestController
@RequestMapping(value = "/produtos") // Uri de dominio da coleção
public class ProdutoResource {

	@Autowired
	private ProdutoService service; // Instanciação da classe de serviços.

	// Método para buscar um produto por Id.
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto objeto = service.findId(id);
		return ResponseEntity.ok().body(objeto);
	}

	// Criando uri para consultar uma page de produtos
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "ordeyBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecode = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecode, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(objeto -> new ProdutoDTO(objeto));
		return ResponseEntity.ok().body(listDto);
	}

}
