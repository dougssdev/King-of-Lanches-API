package br.com.douglas.kol.controller;

import br.com.douglas.kol.dto.pedido.DadosCancelamento;
import br.com.douglas.kol.dto.pedido.DadosEnvio;
import br.com.douglas.kol.dto.pedido.DetalhamentoPedido;
import br.com.douglas.kol.model.pedido.Pedido;
import br.com.douglas.kol.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@CrossOrigin("*")
@RequestMapping("pedidos")
public class PedidoController {
    @Autowired
    private PedidoService service;

    @GetMapping("/home")
    public String formulario(Model model){
        model.addAttribute("pedido", new Pedido());
        return "home";
    }

    @PostMapping("/enviaPedido")
    public ResponseEntity<DetalhamentoPedido> enviaPedido(@RequestBody DadosEnvio dadosEnvio) throws RuntimeException, URISyntaxException {
        DetalhamentoPedido novoPedido = service.criarNovoPedido(dadosEnvio.idsBebida(), dadosEnvio.idsHamburguer(),
                dadosEnvio.idsPizza());

        if (novoPedido != null) {
            URI location = new URI("/novoPedido/" + novoPedido.id_pedido());
            return ResponseEntity.created(location).body(novoPedido);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(novoPedido);
    }

    @PostMapping("/cancelaPedido")
    public ResponseEntity<String> cancelaPedido(@RequestBody DadosCancelamento dados) {
        service.cancelaPedido(dados.id());
        return ResponseEntity.ok("Pedido de número: " + dados.id() + " foi cancelado com sucesso.");
    }

}
