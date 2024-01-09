# spring-ai-in-action

In this project the goal is integrate my database with open ai and use it to generate response.

1. U can give the program several question like:
     - Every type of question for example what is the tallest mountain? and give u a response with links, ecc. Example for links
       u search for something accademical like programming and openai returns you links, diagrams, part of code, ecc.
     - U can based on a genre and a year search for whatever u want like film, games, books, ecc.
     - U can genrate images based on a prompt but this function it's being tested as it doesn't work as it should.
  
2. U can give the program your db and the openai api analyze the data retrived and in base of that helps u do a lot of things
   like:
      -Do query for you and retrive u a response u can manipulate after.
      -Give u hint how to do things with the data
      -Added a method called graph that generate a simple text based art diagram from given data. U have to specify what data u have to use from the db u send for the final         calculation. Use this formula for the generation:

   es.:  http://localhost:8080/prodotto/graph?request=loss revenue using the data i give to u using the formula of bulk price * (remaining amount plus total selled) for loss          and for revenue retail price * total selled. I want the graph filled with all the data possible and i want a short explenation of what u do. The type of currency u           have to use is the euro.

# things-i-want-to-add-in-future-update

I want to create a method that take in a image, text, ecc. and give me out a graph, table, ecc. or for example if we talk about arts or something, capable of generate images from that or recognize the artist behind the image or describe it. Unfortunaly this method of generation isn't avaible right now on spring boot because right now the image generator can accept only a text description data and can't process like a table of a db i pass. When it become avaible i will implement it.
