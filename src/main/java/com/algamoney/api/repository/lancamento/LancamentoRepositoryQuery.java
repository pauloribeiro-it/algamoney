package com.algamoney.api.repository.lancamento;

import com.algamoney.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algamoney.api.model.Lancamento;
import com.algamoney.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {
	Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
