package restaurante_cardapio.model;

public class Cardapio {
    private Long id;
    private String nomeDoPrato;
    private Double preco;
    private Long restauranteId;

    public Cardapio() {
    }

    public Cardapio(Long id, String nomeDoPrato, Double preco, Long restauranteId) {
        this.id = id;
        this.nomeDoPrato = nomeDoPrato;
        this.preco = preco;
        this.restauranteId = restauranteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDoPrato() {
        return nomeDoPrato;
    }

    public void setNomeDoPrato(String nomeDoPrato) {
        this.nomeDoPrato = nomeDoPrato;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Long getRestauranteId() {
        return restauranteId;
    }

    public void setRestauranteId(Long restauranteId) {
        this.restauranteId = restauranteId;
    }
}
