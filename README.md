# B-TREE CHOISY-SHAN-DUFEU-HANNI 2022

**Projet Java** dans le cadre d'un projet de recherche SGBD MIAGE MASTER 1 2022.


## Récupération et installation du projet

`git clone https://github.com/sebastienchoisy/B-tree2022`

Une fois le projet récupéré, il faut installer les dépendances nécessaires pour créer le fichier de données et éventuellement faire tourner le test avec **Maven**.

    mvn install


## Notice d'utilisation

Une fois les dépendances installées, il faut run le fichier java **DataWriting** situé dans le dossier dataFactory, si l'on souhaite créé un nouveau fichier de données (après avoir supprimé celui déjà existant à la racine).
On peut ensuite lancer le fichier **Main** pour lancer l'interface.
Les deux boutons qui vont nous intéresser dans ce projet de recherche sont :

- Le bouton **Charger l'arbre depuis le fichier de données** qui va donc créer l'arbre en se basant sur les numéros de sécurité sociale des personnes présentes dans le fichier de données "data.txt"(en associant chaque valeur avec son numéro de ligne).
- Le bouton **Lancer les recherches** qui va lancer les recherches indexées (avec les numéros de lignes) et séquentielles (en parcourant les noeuds, les uns à la suite des autres) de 100 valeurs aléatoires piochées sur les valeurs précédemment ajoutées.

Une fois, les recherches effectués, on trouve les résultats/statistiques de ces recherches dans la console et également dans un fichier texte appelé **RapportDeRecherche.txt**.
Un test a également été créé lors de l'implantation du référencement du noeud suivant pour chaque noeud.

## Résultats

On remarque que le temps de recherche séquentielle dans le fichier est nettement supérieur au temps de recherche séquentiel dans l'arbre.
Le temps de recherche séquentiel dans l'arbre est, lui, supérieur au temps de recherche indexé dans l'arbre.
