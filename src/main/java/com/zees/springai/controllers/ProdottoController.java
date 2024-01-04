package com.zees.springai.controllers;

import com.zees.springai.model.Prodotto;
import com.zees.springai.services.ProdottoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prodotto")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @GetMapping("/prodottoList")
    public List<Prodotto> getProdotto()
    {
        return prodottoService.findAll();
    }

    @GetMapping("/getProdotto/{id_prodotto}")
    public Optional<Prodotto> findProdottoById(@PathVariable("id_prodotto") Long id)
    {
        return prodottoService.findById(id);
    }

    @PostMapping("/addProdotto")
    public void saveNewProdotto(Prodotto prodotto)
    {
        prodottoService.save(prodotto);
    }

    @DeleteMapping("/deleteProdotto/{id_prodotto}")
    public void deleteProdottoById(@PathVariable("id_prodotto") Long id)
    {
        prodottoService.delete(id);
    }

    @GetMapping("/response")
    public String getResponse(String topic, @RequestParam String question) {
        return prodottoService.getAiInformation(topic, question);
    }
}
