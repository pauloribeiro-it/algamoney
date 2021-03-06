package com.example.algamoney.api.repository.lancamento;

import com.example.algamoney.api.dto.LancamentoEstatisticaCategoria;
import com.example.algamoney.api.dto.LancamentoEstatisticaDia;
import com.example.algamoney.api.dto.LancamentoEstatisticaPessoa;
import com.example.algamoney.api.repository.projection.ResumoLancamento;
import com.example.algamoney.api.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.algamoney.api.model.Lancamento;

import java.time.LocalDate;
import java.util.List;

public interface LancamentoRepositoryQuery {
	List<LancamentoEstatisticaPessoa> porPessoa(LocalDate inicio, LocalDate fim);
	List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);
	Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
	Page<ResumoLancamento> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
