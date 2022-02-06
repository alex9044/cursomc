package com.alexmoscato.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.alexmoscato.cursomc.domain.Cliente;
import com.alexmoscato.cursomc.dtos.ClienteDTO;
import com.alexmoscato.cursomc.repositories.ClienteRepository;
import com.alexmoscato.cursomc.services.exceptions.DataIntegrityException;
import com.alexmoscato.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;

	// Método para buscar um cliente por Id.
	public Cliente findId(Integer id) {
		Optional<Cliente> objeto = repository.findById(id);
		return objeto.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	// Método para atualizar um clientes
	public Cliente update(Cliente obj) {
		Cliente newObj = findId(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);
	}

	// Método para deletar um clientes.
	public void delete(Integer id) {
		findId(id);
		try {
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir um cliente que possui entidades associadas");
		}
	}

	// Método para listar todas as clientes.
	public List<Cliente> findAll() {
		return repository.findAll();
	}

	// Método para listar uma page de clientes.
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}

	// Método para criar um objeto categoria apartir de um objeto categoria DTO.
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	// Método para atualizar um objeto do banco de dados apartir de um objeto passado por parametro
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

}
