package restaurante_cardapio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurante_cardapio.model.Cardapio;
import restaurante_cardapio.repository.CardapioRepository;

import java.util.List;

@RestController
@RequestMapping("/cardapios")
public class CardapioController {
    private final CardapioRepository cardapioRepository;

    public CardapioController(CardapioRepository cardapioRepository) {
        this.cardapioRepository = cardapioRepository;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<Cardapio> listarCardapios() {
        return cardapioRepository.findAll();
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> adicionarCardapio(@RequestBody Cardapio cardapio) {
        cardapioRepository.save(cardapio);
        return new ResponseEntity<>("Cardápio criado com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> atualizarCardapio(@PathVariable Long id, @RequestBody Cardapio cardapioAtualizado) {
        if (cardapioRepository.findById(id).isPresent()) {
            cardapioAtualizado.setId(id); // Garante que o ID seja mantido
            cardapioRepository.save(cardapioAtualizado);
            return new ResponseEntity<>("Cardápio atualizado com sucesso!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cardápio não encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarCardapio(@PathVariable Long id) {
        if (cardapioRepository.findById(id).isPresent()) {
            cardapioRepository.deleteById(id);
            return new ResponseEntity<>("Cardápio removido com sucesso!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cardápio não encontrado", HttpStatus.NOT_FOUND);
    }
}
