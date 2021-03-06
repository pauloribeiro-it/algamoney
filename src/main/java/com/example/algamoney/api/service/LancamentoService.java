package com.example.algamoney.api.service;

import com.example.algamoney.api.Mailer;
import com.example.algamoney.api.dto.LancamentoEstatisticaPessoa;
import com.example.algamoney.api.model.Lancamento;
import com.example.algamoney.api.model.Pessoa;
import com.example.algamoney.api.model.Usuario;
import com.example.algamoney.api.repository.LancamentoRepository;
import com.example.algamoney.api.repository.PessoaRepository;
import com.example.algamoney.api.repository.UsuarioRepository;
import com.example.algamoney.api.service.exception.PessoaInexistenteOuInativaException;
import com.example.algamoney.api.storage.S3;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class LancamentoService {
	
	@Autowired
	private LancamentoRepository repository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private Mailer mailer;

	@Autowired
	private S3 s3;

	private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
	private static final Logger logger = LoggerFactory.getLogger(LancamentoService.class);

	@Scheduled(cron = "0 0 6 * * *")
//	@Scheduled(fixedDelay = 1000 * 60 * 30)
	public void avisarSobreLancamentosVencidos(){
		if(logger.isDebugEnabled()){
			logger.debug("Preparando envio de e-mails de aviso de lançamentos vencidos.");
		}
		List<Lancamento> vencidos = repository.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.of(2021, 5, 1));

		if(vencidos.isEmpty()){
			logger.info("Sem lançamentos vencidos para aviso.");
			return;
		}

		logger.info("Existem {} lançamentos vencidos", vencidos.size());

		List<Usuario> usuarios = usuarioRepository.findByPermissoesDescricao(DESTINATARIOS);
		if(usuarios.isEmpty()){
			logger.warn("Existem lançamentos vencidos, mas o sistema não encontrou destinatários.");
		}
		mailer.avisarSobreLancamentosVencidos(vencidos, usuarios);

		logger.info("Envio de e-mail de aviso concluído.");
	}

	public Lancamento salvar(Lancamento lancamento) {
		validarPessoa(lancamento);
		if(StringUtils.hasText(lancamento.getAnexo())){
			s3.salvar(lancamento.getAnexo());
		}
		return repository.save(lancamento);
	}

	public Lancamento atualizar(Long codigo, Lancamento lancamento) {
		Lancamento lancamentoSalvo = buscarLancamentoExistente(codigo);
		if (!lancamento.getPessoa().equals(lancamentoSalvo.getPessoa())) {
			validarPessoa(lancamento);
		}
		if(StringUtils.isEmpty(lancamento.getAnexo()) && StringUtils.hasText(lancamentoSalvo.getAnexo())){
			s3.remover(lancamentoSalvo.getAnexo());
		}else if(StringUtils.hasText(lancamento.getAnexo()) && !lancamento.getAnexo().equals(lancamentoSalvo.getAnexo())){
			s3.substituir(lancamentoSalvo.getAnexo(), lancamento.getAnexo());
		}

		BeanUtils.copyProperties(lancamento, lancamentoSalvo, "codigo");

		return repository.save(lancamentoSalvo);
	}

	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws Exception{
		List<LancamentoEstatisticaPessoa> dados = repository.porPessoa(inicio, fim);

		final Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt","BR"));

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamentos-por-pessoa.jasper");

		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, new JRBeanCollectionDataSource(dados));

		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	private void validarPessoa(Lancamento lancamento) {
		Pessoa pessoa = null;
		if (lancamento.getPessoa().getCodigo() != null) {
			pessoa = pessoaRepository.getOne(lancamento.getPessoa().getCodigo());
		}

		if (pessoa == null || pessoa.isInativo()) {
			throw new PessoaInexistenteOuInativaException();
		}
	}

	private Lancamento buscarLancamentoExistente(Long codigo) {
		return repository.findById(codigo).orElseThrow(IllegalArgumentException::new);
	}
}
