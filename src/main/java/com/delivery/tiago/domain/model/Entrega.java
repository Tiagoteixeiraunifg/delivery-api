package com.delivery.tiago.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.delivery.tiago.domain.ValidationsGroup;
import com.delivery.tiago.domain.exception.NegocioException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Entrega {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Valid
	@ConvertGroup(from = Default.class, to = ValidationsGroup.clienteId.class)
	@NotNull
	@ManyToOne
	private Cliente cliente;
	
	@Valid
	@Embedded
	private Destinatario destinatario;
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL)
	private List<StatusPedido> statusPedido = new ArrayList<>();
	
	@NotNull
	private BigDecimal taxa;
	
	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private StatusEntrega status;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataPedido;
	
	@JsonProperty(access = Access.READ_ONLY)
	private OffsetDateTime dataFinalizacao;
	
	public StatusPedido adicionarStatusPedido(String descricao) {
		StatusPedido status = new StatusPedido();
		status.setDescricao(descricao);
		status.setDataRegistro(OffsetDateTime.now());
		status.setEntrega(this);
		this.getStatusPedido().add(status);
		return status;
	}
	
	public void finalizar() {
		
		if(naoPodeSerFinalizada()) {
			throw new NegocioException("Essa entrega não pode ser finalizada");
		}
		this.setStatus(StatusEntrega.FINALIZADA);
		this.setDataFinalizacao(OffsetDateTime.now());
		
	}
	
	public boolean  podeSerFinalizada() {
		return this.getStatus().equals(StatusEntrega.PENDENTE);
	}
	
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
	}
	
}
