package com.algamoney.api.repository.lancamento;

import com.algamoney.api.dto.LancamentoEstatisticaCategoria;
import com.algamoney.api.dto.LancamentoEstatisticaDia;
import com.algamoney.api.repository.projection.ResumoLancamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algamoney.api.model.Lancamento;
import com.algamoney.api.repository.filter.LancamentoFilter;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepositoryQuery {
	List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);
	Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
