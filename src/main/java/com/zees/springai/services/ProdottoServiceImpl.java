package com.zees.springai.services;

import com.zees.springai.model.Prodotto;
import com.zees.springai.models.GeneratedImage;
import com.zees.springai.repository.ProdottoRepository;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
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

    @Override
    public String getGraphicFromData(String topic, String request) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                I want to ask you kindly a question. Given the following data {topic} retrieved by my sql 
                database, I want you to give me a Text-based art diagram about this {request}.
                """);
        promptTemplate.add("topic", topic = prodottoRepository.findAll().toString());
        promptTemplate.add("request", request);

        return this.aiClient.generate(promptTemplate.create()).getGeneration().getText();
    }
//metodo non ancora disponibile ma funzionante che ritorna immagine di un grafico partendo da dati di un db.
// Non disponibile siccome questa funzionalià di ritornare immagini partendo da serie di dati non è stata
// ancora implementata per spring boot
//
//    public InputStreamResource getGraphicFromData(String topic, String request ) throws URISyntaxException {
//       PromptTemplate promptTemplate = new PromptTemplate("""
//                I'm really needing a image generation of a graphic. Can u please create me one about {request}?
//                This are the {topic} I have to put in it. Make it with a resolution of 256x256,
//                But ensure it is presented in JSON. I desire one creation. Give me a JSON in format : prompt, n, size.
//                In prompt insert a short description of the image u generate
//                """);
//        promptTemplate.add("topic", topic = prodottoRepository.findAll().toString());
//        promptTemplate.add("request", request);
//        String imagePrompt = this.aiClient.generate(promptTemplate.create()).getGeneration().getText();
//
//        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//        requestBody.add("prompt", imagePrompt);
//        requestBody.add("n", "1");
//        requestBody.add("size", "256");
//
//
//
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + apiKey);
//        headers.add("Content-Type", "application/json");
//        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(requestBody, headers);
//
//
//        String imageUrl = restTemplate.exchange(openAIImageUrl, HttpMethod.POST, httpEntity, GeneratedImage.class)
//              .getBody().getData().get(0).getUrl();
//       byte[] imageBytes = restTemplate.getForObject(new URI(imageUrl), byte[].class);
//        assert imageBytes != null;
//        return new InputStreamResource(new java.io.ByteArrayInputStream(imageBytes));}
}

