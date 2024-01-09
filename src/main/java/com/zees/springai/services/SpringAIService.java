package com.zees.springai.services;

import com.zees.springai.models.GeneratedImage;
import org.springframework.ai.client.AiClient;
import org.springframework.ai.client.AiResponse;
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
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Service
public class SpringAIService {

    @Autowired
    AiClient aiClient;

    @Value("${spring.ai.openai.apikey}")
    private String apiKey;

    @Value("${spring.ai.openai.imageUrl}")
    private String openAIImageUrl;


    public String getRequest(String topic){
        PromptTemplate promptTemplate = new PromptTemplate("""
                I want to ask you kindly a question. I want all the details about it u can give me. Would you like me to give me a response at this question about {topic}? 
                Please if u can give me graph, link to pages releated and so on to lear more about it.
                """);
        promptTemplate.add("topic", topic);
        return this.aiClient.generate(promptTemplate.create()).getGeneration().getText();
    }

    public String getBestMedia(String type, String category, String year) {
        PromptTemplate promptTemplate = new PromptTemplate("""
                I want to research a {type}. How about you give me a {type} about {category} in {year} to get started?
                But pick the best best you can think of. I'm a critic, after all. Ratings are a good place to start.
                And who develop it? And who help it? Can you give me a short summary and also some link i can watch?
                But don't give me too much information. I want to be surprised.
                And please give me these details in the following JSON format: type, category, year, name, author, review, smallSummary.
                """);
        Map.of("category", category, "year", year, "type", type).forEach(promptTemplate::add);
        AiResponse generate = this.aiClient.generate(promptTemplate.create());
        return generate.getGeneration().getText();
    }


    public InputStreamResource getImage(@RequestParam(name = "topic") String topic) throws URISyntaxException {
        PromptTemplate promptTemplate = new PromptTemplate("""
                 I am really board from online memes. Can you create me a prompt about {topic}.
                 Elevate the given topic. Make it classy.
                 Make a resolution of 256x256, but ensure that it is presented in json.
                 I desire only one creation. Give me as JSON format: prompt, n, size.
                """);
        promptTemplate.add("topic", topic);
        String imagePrompt = this.aiClient.generate(promptTemplate.create()).getGeneration().getText();

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("prompt", imagePrompt);
        requestBody.add("n", "1");
        requestBody.add("size", "\"256\"");

        System.out.println(imagePrompt);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + apiKey);
            headers.add("Content-Type", "application/json");
            HttpEntity<String> httpEntity = new HttpEntity<>(imagePrompt, headers);

            System.out.println(restTemplate);
            System.out.println(headers);
            System.out.println(httpEntity);


            String imageUrl = restTemplate.exchange(openAIImageUrl, HttpMethod.POST, httpEntity, GeneratedImage.class)
                    .getBody().getData().get(0).getUrl();

            System.out.println(imageUrl);

            byte[] imageBytes = restTemplate.getForObject(new URI(imageUrl), byte[].class);
        return new InputStreamResource(new java.io.ByteArrayInputStream(imageBytes));
    }




}
