# tinyTwitt
Repo pour le projet "TinyTwitt" 

Lien vers l'API explorer : https://apis-explorer.appspot.com/apis-explorer/?base=https://tinytwitt-227717.appspot.com/_ah/api#p/tinytwitt/v1/

# Mesures (écarts types et moyenne)

## Post de 1 twitt:
  - 100 followers : 40 (moy = 169ms)
  - 1000 followers : 45 (moy = 179ms)
  - 5000 followers : 42 (moy = 172ms )
  
 Remarque : La variance est faible car poster un twitt ne dépend pas du nombre de followers dans notre modèle, les temps varient peu.
 
## Extraire...
  - 10 twitts pour 
    - 100 followers : 108 (moy = 250ms)
    - 1000 followers : 37 (moy = 167ms)
    - 5000 followers : 97 (moy = 158ms)
  - 50 twitts pour
    - 100 followers : 85 (moy = 215ms)
    - 1000 followers : 47 (moy = 217ms)
    - 5000 followers : 1320 (moy = 439ms)  Remarque : une valeur a 7394ms, sans doute un gros coup de lag
  - 100 twitts pour
    - 100 followers : 126 (moy = 280ms)
    - 1000 followers : 39 (moy = 198ms)
    - 5000 followers : 54 (moy = 176ms)
    
Remarque : La latence personnelle entre en jeu sur certaines mesures...

## Extraire pour un hashtag:
  - 50 messages : 170 (moy = 676ms)
  - 1000 messages : 2803 (moy = 8987 ms)
  - 5000 messages : 3743 (moy = 37721 ms)
  
Remarque : Les messages n'étant pas dans le cache (à cause d'un problème lors des query), le temps pour récupérer des messages est probablement plus long que ce qu'il devrait être...
