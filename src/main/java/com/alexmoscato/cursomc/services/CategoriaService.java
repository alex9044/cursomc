package com.alexmoscato.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.alexmoscato.cursomc.domain.Categoria;
import com.alexmoscato.cursomc.dtos.CategoriaDTO;
import com.alexmoscato.cursomc.repositories.CategoriaRepository;
import com.alexmoscato.cursomc.services.exceptions.*;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repository;
	
	//Method para buscar uma categoria por Id.
	public Categoria findId(Integer id){
		Optional<Categoria> objeto = repository.findById(id); 
		return objeto.orElseThrow(() -> 
			new ObjectNotFoundException("Objeto não encontrado! ID:"+id+", tipo: " +Categoria.class.getName()));
	} 
	
	//Método para persistir uma nova Categoria
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repository.save(obj);
	}
	
	//Método para atualizar uma categoria
	public Categoria update(Categoria obj) {
		findId(obj.getId());
		return repository.save(obj);
	}
	
	//Método para deletar uma categoria.
	public void delete(Integer id) {
		findId(id);
		try {
			repository.deleteById(id); 
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos associados");
		}
	}
	
	//Método para listar todas as categorias.
	public List<Categoria> findAll(){
		return repository.findAll();
	}
}
