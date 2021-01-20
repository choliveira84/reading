package com.reading.msnotificacao.application.impl;

import java.util.Optional;

import com.reading.msnotificacao.application.dto.NotificacaoBuscaDTO;
import com.reading.msnotificacao.application.dto.NotificacaoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoLivroPublicadoDTO;
import com.reading.msnotificacao.application.dto.NotificacaoResenhaDTO;
import com.reading.msnotificacao.domain.model.Notificacao;
import com.reading.msnotificacao.domain.repository.NotificacaoReposity;
import com.reading.msnotificacao.domain.service.NotificacaoService;
import com.reading.msnotificacao.infrastructure.exceptions.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class NotificacaoServiceImpl implements NotificacaoService {

    @Autowired
    private NotificacaoReposity repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Page<NotificacaoDTO> buscar(NotificacaoBuscaDTO request, Pageable pageable) {
        Specification<Notificacao> specification = Specification.where(null);

        // TODO: criar especificacao

        return repository.findAll(specification, pageable).map(this::mapearParaDTO);
    }

    @Transactional
    @Override
    public NotificacaoDTO criar(NotificacaoLivroPublicadoDTO request) {
        Notificacao notificacaoSalva = repository.save(mapearParaEntidade(request));

        return mapearParaDTO(notificacaoSalva);
    }

    @Transactional
    @Override
    public NotificacaoDTO criar(NotificacaoResenhaDTO request) {
        Notificacao notificacaoSalva = repository.save(mapearParaEntidade(request));

        return mapearParaDTO(notificacaoSalva);
    }

    @Transactional
    @Override
    public void deletar(Long id) {
        Optional<Notificacao> possivelNotificacao = repository.findById(id); // NOSONAR

        if (!possivelNotificacao.isPresent()) {
            throw new EntityNotFoundException(String.format("Notificação de id %s não encontrada", id));
        } else {
            repository.deleteById(id);
        }
    }

    @Override
    public NotificacaoDTO consultar(Long id) {
        Optional<Notificacao> possivelNotificacao = repository.findById(id); // NOSONAR

        if (!possivelNotificacao.isPresent()) {
            throw new EntityNotFoundException(String.format("Notificação de id %s não encontrada", id));
        } else {
            return mapearParaDTO(possivelNotificacao.get());
        }
    }

    public NotificacaoDTO mapearParaDTO(Notificacao notificacao) {
        return mapper.map(notificacao, NotificacaoDTO.class);
    }

    public Notificacao mapearParaEntidade(NotificacaoResenhaDTO request) {
        return new Notificacao(request.getTitulo(), request.getLivroId(), request.getId(), request.getUsuarioId(),
                request.getUsuarioNome());
    }

    public Notificacao mapearParaEntidade(NotificacaoLivroPublicadoDTO request) {
        return new Notificacao(request.getTitulo(), request.getId());
    }

}
