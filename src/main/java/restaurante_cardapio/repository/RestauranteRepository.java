package restaurante_cardapio.repository;

import restaurante_cardapio.model.Restaurante;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class RestauranteRepository {

    private final List<Restaurante> restaurantes = new ArrayList<>();

    // Retorna todos os restaurantes
    public List<Restaurante> findAll() {
        return new ArrayList<>(restaurantes);
    }

    // Salva um restaurante (adiciona ou atualiza)
    public void save(Restaurante restaurante) {
        // Se o restaurante já existir, atualiza-o
        restaurantes.removeIf(r -> r.getId().equals(restaurante.getId()));
        restaurantes.add(restaurante);
    }

    // Encontra restaurante por ID
    public Optional<Restaurante> findById(Long id) {
        return restaurantes.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst();
    }

    // Remove um restaurante pelo ID
    public void deleteById(Long id) {
        restaurantes.removeIf(r -> r.getId().equals(id));
    }
}
