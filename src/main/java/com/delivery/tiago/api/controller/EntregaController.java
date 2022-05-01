package com.delivery.tiago.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.delivery.tiago.api.assembler.EntregaAssembler;
import com.delivery.tiago.api.model.output.dto.EntregaDTO;
import com.delivery.tiago.domain.model.Entrega;
import com.delivery.tiago.domain.repository.EntregaRepository;
import com.delivery.tiago.domain.service.entrega.EntregaService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/entregas")
public class EntregaController {

	private EntregaService entregaService;
	private EntregaRepository entregaRepository;
	private EntregaAssembler entregaAssembler;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Entrega solicitar(@Valid @RequestBody Entrega entrega) {
		
		return entregaService.solicitar(entrega);
		
	}
	
	
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaDTO> listarById(@PathVariable Long entregaId){
		
		return entregaRepository.findById(entregaId)
				.map(entrega ->	{
					EntregaDTO entregaDTO = entregaAssembler.toModel(entrega);
					return ResponseEntity.ok(entregaDTO);
				}).orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping
	public List<EntregaDTO> findAll() {		
		return entregaAssembler.toListEntrgaTDO(entregaRepository.findAll());
	}
	
	
	
	@GetMapping("/teste")
	public List<EntregaDTO> findAlll() {
		
		List<Entrega> listModel = entregaRepository.findAll();
		List<EntregaDTO> listDTO = new ArrayList<>();
		listModel.stream().forEach(entrega -> listDTO.add(entregaAssembler.toModel(entrega)));
		
		return listDTO;
	}
	
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		entregaService.finalizar(entregaId);
	}

	
}
