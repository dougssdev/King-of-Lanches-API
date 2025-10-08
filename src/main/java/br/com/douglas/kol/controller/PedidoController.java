package br.com.douglas.kol.controller;

import br.com.douglas.kol.dto.pedido.DadosCancelamento;
import br.com.douglas.kol.dto.pedido.DadosEnvio;
import br.com.douglas.kol.dto.pedido.DetalhamentoPedido;
import br.com.douglas.kol.model.pedido.Pedido;
import br.com.douglas.kol.model.pedido.StatusDoPedido;
import br.com.douglas.kol.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @PostMapping("/enviaPedido")
    public ResponseEntity<DetalhamentoPedido> enviaPedido(@RequestBody DadosEnvio dadosEnvio) throws RuntimeException, URISyntaxException {
        DetalhamentoPedido novoPedido = service.criarNovoPedido(dadosEnvio.idsBebida(), dadosEnvio.idsHamburguer(),
                dadosEnvio.idsPizza());

        if (novoPedido != null) {
            URI location = new URI("/novoPedido/" + novoPedido.id_pedido());
            return ResponseEntity.created(location).body(novoPedido);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/cancelaPedido")
    public ResponseEntity<StatusDoPedido> cancelaPedido(@RequestBody DadosCancelamento dados) {
        Optional<Pedido> pedido = service.cancelaPedido(dados.id());

        return ResponseEntity.ok(pedido.get().getStatus());
    }

}
