package com.alexmoscato.cursomc.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends StandardException {
	private static final long serialVersionUID = 1L;

	private List<FieldName> erros = new ArrayList<>();
	
	public ValidationException(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);
	}

	public List<FieldName> getErros() {
		return erros;
	}

	public void addErros(String fieldName, String message) {
		erros.add(new FieldName(fieldName, message));
	}
	
}
