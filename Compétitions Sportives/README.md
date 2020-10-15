# Sujet
Ce projet permet de faire une simulation de compétitions sportives. On possèdes 2 types de
compétitions différentes :
- les tournois à élimination directe
- les leagues ou les équipes jouent 2 fois contre chaque autre équipes

Lorsque tout les matchs d'une compétition ont été joués, on peut regarder le classement de celle-ci.

# Howto
Cloner le projet :
```bash
git init
git clone https://gitlab-etu.fil.univ-lille1.fr/liard/coo_competitions_sportives.git
cd coo_competitions_sportives
```

Génération de l'archive (jar) avec maven :
```bash
mvn install
# jar généré -> target/competSportive-1.jar
```

Execution de l'archive générée :
```bash
java -jar target/competSportive-1.jar
# Execute une league avec 8 équipes, puis execute un tournoi avec 8 participants.
```

Génération de la documentation :
```bash
mvn javadoc:javadoc
# la doc se trouve dans target/site/... (cliquer sur n'importe quel fichier html)
```

# Elements de code intéréssants

### Mocks dans les tests
Des mocks ont été utilisés dans 2 classes de tests :
- MockLeague      dans LeagueTest
- MockTournament  dans TournamentTest

Ces mocks sont utilisés pour override des méthodes des classes dont elles héritent respectivement afin d'annuler les effets de bords des dites méthodes tout en effectuant d'autres taches à la place utiles pour le test.

Par exemple dans TournamentTest.java, le mock utilisé permet d'override la méthode playMatch pour qu'à la place de faire ce qu'elle fait normalement, elle enregistre les attributs qui lui ont été passés dans une liste et renvoie toujours un vainqueur prédéterminé.

Grace à ça on peut ensuite vérifier dans les tests avec quels paramètres la méthodes playMatch a été appelée et combien de fois.

Les 2 mocks ne sont pas identiques car ils n'héritent pas de la même implémentation de la classe abstraite Competition.

### Classe CompetitionException.java
Une simple classe d'exception utilisée lorsqu'il est nécessaire de throw une exception nous même (pas une RuntimeException donc).

### Fonction playMatch dans la classe Competition.java
On a choisi de ne pas laisser cette fonction en abstract pour ne pas avoir besoin de redéfinir dans les classes qui héritent de Competition.

Pourquoi ? Pour cette première version du sujet, cette fonction peut être la même à la fois pour un Tournament qu'une League. Donc pas besoin de la laisser en abstract pour la redéfinir 2 fois à l'identique par la suite.

# Diagramme de classe du projet
![diagramme de classe](./diagrammeDeClasse.png)
