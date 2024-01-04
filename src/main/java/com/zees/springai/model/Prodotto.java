package com.zees.springai.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "prodotto")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_product")
    private Long id_prodotto;

    @Column(name = "product_name")
    private String nome_prodotto;
    @Column(name = "description")
    private String descrizione;
    @Column(name = "bulk_price")
    private Double prezzo_ingrosso;
    @Column(name = "retail_price")
    private Double prezzo_dettaglio;
    @Column(name = "total_selled")
    private Integer quantita_venduta;
    @Column(name = "remaining_amount")
    private Integer quantita_rimanente;
}
