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
    public ResponseEntity<String> enviaPedido(@RequestBody DadosEnvio dadosEnvio) {
        DetalhamentoPedido novoPedido = service.criarNovoPedido(dadosEnvio.idsBebida(), dadosEnvio.idsHamburguer(),
                dadosEnvio.idsPizza());

        if (novoPedido != null) {
            ResponseEntity<String> ok = ResponseEntity.ok().body("Pedido enviado com sucesso." + "\n"
                    + "Número do pedido: " + novoPedido.id_pedido() + "\n"
                    + novoPedido.bebida() + "\n"
                    + novoPedido.hamburguer() + "\n"
                    + novoPedido.pizza() + "\n"
                    + "Total: " + novoPedido.precoTotal() + "\n");
            return ok;
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar pedido.");
        }

    }

    @PostMapping("/cancelaPedido")
    public ResponseEntity<String> cancelaPedido(@RequestBody DadosCancelamento dados) {
        service.cancelaPedido(dados.id());
        return ResponseEntity.ok("Pedido de número: " + dados.id() + " foi cancelado com sucesso.");
    }

}
