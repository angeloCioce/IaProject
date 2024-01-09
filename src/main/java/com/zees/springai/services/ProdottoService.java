package com.zees.springai.services;

import com.zees.springai.model.Prodotto;
import org.springframework.core.io.InputStreamResource;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

public interface ProdottoService {
    List<Prodotto> findAll();

    Prodotto save(Prodotto prodotto);

    Optional<Prodotto> findById(Long id);

    void delete(Long id);

    public String getAiInformation(String topic, String question);

    public String getGraphicFromData(String topic, String request);
}
