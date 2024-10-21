package restaurante_cardapio.repository;

import restaurante_cardapio.model.Cardapio;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CardapioRepository {

    private final List<Cardapio> cardapios = new ArrayList<>();

    // Retorna todos os cardápios
    public List<Cardapio> findAll() {
        return new ArrayList<>(cardapios); // Retorna uma cópia para evitar modificações diretas
    }

    // Salva um cardápio (adiciona ou atualiza)
    public void save(Cardapio cardapio) {
        // Remove o cardápio com o mesmo ID, caso já exista
        cardapios.removeIf(c -> c.getId().equals(cardapio.getId()));
        cardapios.add(cardapio);
    }

    // Encontra um cardápio pelo ID
    public Optional<Cardapio> findById(Long id) {
        return cardapios.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    // Remove um cardápio pelo ID
    public void deleteById(Long id) {
        cardapios.removeIf(c -> c.getId().equals(id));
    }
}
