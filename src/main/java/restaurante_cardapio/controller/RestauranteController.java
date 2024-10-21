package restaurante_cardapio.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurante_cardapio.model.Restaurante;
import restaurante_cardapio.repository.RestauranteRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    private final RestauranteRepository restauranteRepository;

    public RestauranteController(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public List<Restaurante> listarRestaurantes() {
        return restauranteRepository.findAll();
    }

    @GetMapping(value = "/{id}", produces = {"application/json", "application/xml"})
    public ResponseEntity<Restaurante> consultarRestauranteComCardapios(@PathVariable Long id) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(id);
        return restaurante.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> adicionarRestaurante(@RequestBody Restaurante restaurante) {
        restauranteRepository.save(restaurante);
        return new ResponseEntity<>("Restaurante criado com sucesso!", HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> atualizarRestaurante(@PathVariable("id") Long id, @RequestBody Restaurante restauranteAtualizado) {
        Optional<Restaurante> restauranteOptional = restauranteRepository.findById(id);
        if (restauranteOptional.isPresent()) {
            Restaurante restauranteExistente = restauranteOptional.get();
            // Atualiza os campos do restaurante existente
            restauranteExistente.setNome(restauranteAtualizado.getNome());
            restauranteExistente.setEndereco(restauranteAtualizado.getEndereco());

            // Atualiza os cardápios se fornecidos
            if (restauranteAtualizado.getCardapios() != null) {
                restauranteExistente.setCardapios(restauranteAtualizado.getCardapios());
            }

            restauranteRepository.save(restauranteExistente);
            return new ResponseEntity<>("Restaurante atualizado com sucesso!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Restaurante não encontrado", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarRestaurante(@PathVariable Long id) {
        if (restauranteRepository.findById(id).isPresent()) {
            restauranteRepository.deleteById(id);
            return new ResponseEntity<>("Restaurante removido com sucesso!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Restaurante não encontrado", HttpStatus.NOT_FOUND);
    }
}
