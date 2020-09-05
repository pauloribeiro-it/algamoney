package com.algamoney.api.service;

import java.util.Objects;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algamoney.api.model.Pessoa;
import com.algamoney.api.repository.PessoaRepository;

@Service
public class PessoaService {
	@Autowired
	private PessoaRepository repository;
	
	public Pessoa atualizar(Long codigo, Pessoa pessoa ) {
		Pessoa pessoaBanco = buscaPessoaPeloCodigo(codigo);
		BeanUtils.copyProperties(pessoa, pessoaBanco, "codigo");
		return repository.save(pessoaBanco);
	}

	public void atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
		Pessoa pessoaSalva = buscaPessoaPeloCodigo(codigo);
		pessoaSalva.setAtivo(ativo);
		repository.save(pessoaSalva);
	}
	
	public Pessoa buscaPessoaPeloCodigo(Long codigo) {
		Pessoa pessoaBanco = repository.findOne(codigo);
		if(Objects.isNull(pessoaBanco)) {
			throw new EmptyResultDataAccessException(1);
		}
		return pessoaBanco;
	}
}
