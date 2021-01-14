package com.example.algamoney.api.service;

import java.util.Objects;

import com.example.algamoney.api.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.algamoney.api.model.Pessoa;

@Service
public class PessoaService {
	@Autowired
	private PessoaRepository repository;

	public Pessoa salvar(Pessoa pessoa){
		pessoa.getContatos().forEach(c -> c.setPessoa(pessoa));
		return repository.save(pessoa);
	}

	public Pessoa atualizar(Long codigo, Pessoa pessoa ) {
		Pessoa pessoaBanco = buscaPessoaPeloCodigo(codigo);

		pessoaBanco.getContatos().clear();
		pessoaBanco.getContatos().addAll(pessoa.getContatos());
		pessoaBanco.getContatos().forEach(c -> c.setPessoa(pessoaBanco));

		BeanUtils.copyProperties(pessoa, pessoaBanco, "codigo","contatos");
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
