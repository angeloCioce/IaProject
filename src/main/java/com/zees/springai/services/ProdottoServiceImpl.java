package com.zees.springai.services;

import com.zees.springai.model.Prodotto;
import com.zees.springai.repository.ProdottoRepository;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdottoServiceImpl implements ProdottoService{

    @Autowired
    AiClient aiClient;
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Value("${spring.ai.openai.apikey}")
    private String apiKey;
    @Override
    public List<Prodotto> findAll() {
        return prodottoRepository.findAll();
    }

    @Override
    public Prodotto save(Prodotto prodotto) {
        prodottoRepository.save(prodotto);
        return prodotto;
    }

    @Override
    public Optional<Prodotto> findById(Long id) {
        return prodottoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        prodottoRepository.deleteById(id);
    }

    public String getAiInformation(String topic, String question)
    {
        PromptTemplate promptTemplate = new PromptTemplate("""
                I want to ask you kindly a question. Given the following data {topic} retrieved by my sql 
                database, I want you to give me a response based on this {question} after u read all the
                data.
                """);
        promptTemplate.add("topic", topic = prodottoRepository.findAll().toString());
        promptTemplate.add("question", question);

        return this.aiClient.generate(promptTemplate.create()).getGeneration().getText();
    }
}
