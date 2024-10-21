package restaurante_cardapio.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Restaurante {
    private Long id;
    private String nome;
    private String endereco;

    @Schema(description = "Lista de cardápios do restaurante", required = false)
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Cardapio> cardapios; // Lista de cardápios

    public Restaurante() {
        this.cardapios = new ArrayList<>(); // Inicializa a lista de cardápios
    }

    public Restaurante(Long id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cardapios = new ArrayList<>(); // Inicializa a lista de cardápios
    }

    public Restaurante(Long id, String nome, String endereco, List<Cardapio> cardapios) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cardapios = cardapios != null ? cardapios : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<Cardapio> getCardapios() {
        return cardapios;
    }

    public void setCardapios(List<Cardapio> cardapios) {
        this.cardapios = cardapios != null ? cardapios : new ArrayList<>();
    }
}
