package br.com.rocha.apiboleto.controller;

import br.com.rocha.apiboleto.dto.BoletoRequestDTO;
import br.com.rocha.apiboleto.dto.BoletoDTO;
import br.com.rocha.apiboleto.service.BoletoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/boleto")
@AllArgsConstructor
public class BoletoController {

    private final BoletoService boletoService;

    @PostMapping
    public ResponseEntity<BoletoDTO> salvar(@Valid @RequestBody BoletoRequestDTO boletoDTO) {
        var boleto = boletoService.salvar(boletoDTO.getCodigoBarras());
        return new ResponseEntity<>(boleto, HttpStatus.CREATED);
    }

    @GetMapping("/{codigoBarras}")
    public ResponseEntity<BoletoDTO> buscarPorCodigoBarras(@PathVariable("codigoBarras") String codigoBarras) {
        var boletoDTO = boletoService.buscarBoletoPorCodigoBarras(codigoBarras);
        return ResponseEntity.ok(boletoDTO);
    }
}
