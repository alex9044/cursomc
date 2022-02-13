package com.alexmoscato.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.alexmoscato.cursomc.domain.Cliente;
import com.alexmoscato.cursomc.domain.enums.TipoCliente;
import com.alexmoscato.cursomc.dtos.ClienteNewDTO;
import com.alexmoscato.cursomc.repositories.ClienteRepository;
import com.alexmoscato.cursomc.resources.exceptions.FieldName;
import com.alexmoscato.cursomc.services.validation.utils.BR;


public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldName> list = new ArrayList<>();

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldName("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldName("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repository.findByEmail(objDto.getEmail());
		
		if (aux != null) {
			list.add(new FieldName("email","Email já existente!"));
		}

		for (FieldName e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}

