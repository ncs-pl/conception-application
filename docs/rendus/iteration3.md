# Rapport de l'itération 3

## Cas d'utilisations

![UC](uc_iteration3.png)

### "Joueur jeu Nim"

**Périmètre :** Système de jeu

**Niveau :** But utilisateur

**Acteur principal :** Les deux joueurs

**Parties prenantes et intérêts :** Les joueurs

**Pré-conditions :** Aucune

**Post-conditions :** Les joueurs ont joué une partie de Nim

**Scénario nominal :**

1. Le système demande aux joueurs de saisir un nombre de tas supérieur
   ou égal à 1.
2. Les joueurs saisissent un nombre de tas.
3. Le système demande le nom du joueur 1.
4. Les joueurs saisissent le nom du joueur 1.
5. Le système demande le nom du joueur 2.
6. Les joueurs saisissent le nom du joueur 2.
7. Le système démarre une partie, le joueur 1 est défini joueur courant
   et commence.
8. Le système affiche l’état de la partie.
9. Le système invite le joueur courant à jouer un coup.
10. Le joueur courant saisit son coup.
11. Le système vérifie l’état de la partie.
12. Le système détermine le vainqueur de la partie et affiche son nom.
13. Le système propose aux joueurs de rejouer.
14. Les joueurs choisissent de ne pas rejouer.
15. Le système affiche le nombre de parties gagnées de chaque joueur.
16. Le système détermine le vainqueur et affiche le nom de celui-ci.

**Extensions :**

- *1.a.* La saisit du nombre de tas est invalide.
    1. Le système affiche l’erreur.
    2. Retour au point 1 du scénario nominal.
- *10.a.* Le coup saisit par le joueur courant est invalide.
    1. Le système affiche l’erreur.
    2. Retour au point 8 du scénario nominal.
- *11.a.* La partie en cours n’est pas terminée.
    1. Le système change le joueur courant.
    2. Retour au point 8 du scénario nominal.
- *14.a.* Les joueurs décident de rejouer une partie.
    1. Retour au point 7 du scénario nominal.
- *16.a.* Les deux joueurs ont le même nombre de victoires.
    1. Le système affiche ex-æquo.

### "Joueur jeu Puissance 4"

**Périmètre :** Système de jeu

**Niveau :** But utilisateur

**Acteur principal :** Les deux joueurs

**Parties prenantes et intérêts :** Les joueurs

**Pré-conditions :** Aucune

**Post-conditions :** Les joueurs ont joué une partie de Puissance 4

**Scénario nominal :**

1. Le système demande aux joueurs de saisir un nombre de tas supérieur
   ou égal à 1.
2. Les joueurs saisissent un nombre de tas.
3. Le système demande le nom du joueur 1.
4. Les joueurs saisissent le nom du joueur 1.
5. Le système demande le nom du joueur 2.
6. Les joueurs saisissent le nom du joueur 2.
7. Le système démarre une partie, le joueur 1 est défini joueur courant
   et commence.
8. Le système affiche l’état de la partie.
9. Le système invite le joueur courant à jouer un coup.
10. Le joueur courant saisit son coup.
11. Le système vérifie l’état de la partie.
12. Le système détermine le vainqueur de la partie et affiche son nom.
13. Le système propose aux joueurs de rejouer.
14. Les joueurs choisissent de ne pas rejouer.
15. Le système affiche le nombre de parties gagnées de chaque joueur.
16. Le système détermine le vainqueur et affiche le nom de celui-ci.

**Extensions :**

- *8.a.* Le coup saisit par le joueur courant est invalide.
    1. Le système affiche l’erreur.
    2. Retour au point 6 du scénario nominal.
- *9.a.* La partie en cours n’est pas terminée.
    1. Le système change le joueur courant.
    2. Retour au point 6 du scénario nominal.
- *10.a.* Le système ne détermine aucun gagnant pour la partie.
    1. Le système affiche que la partie est nulle.
- *12.a.* Les joueurs décident de rejouer une partie.
    1. Retour au point 5 du scénario nominal.
- *14.a.* Les deux joueurs ont le même nombre de victoires.
    1. Le système affiche ex-æquo.

## Modélisation Objet

![MO](mo_iteration3.png)

## Rapport

Pour cette itération, nous avons décidé d'architecturer à nouveau le
code de notre application pour permettre une meilleure modularité et
une meilleure extensibilité.

De ce fait, nous avons alors adopté le principe de séparation des
responsabilités ainsi que le principe de responsabilité unique pour
chaque classe.

Cela implique la création de nouvelles classes pour chaque élément
des jeux, notamment `Tas` et `ListeTas` pour le jeu de Nim, là où avant
les tas étaient une simple `ArrayList<Integer>`.

Ces modifications ont rendu possible la séparation des responsabilités,
ainsi désormais les nouvelles classes possèdent une méthode `toString()`
qui permet d'afficher les éléments de manière lisible.
Cette méthode est appelée par l'IHM pour afficher les éléments du
plateau des jeux.

En effet, la fusion a aussi permis de réduire la duplication de code
dans nos IHM, laissant l'IHM simplement maître des intéractions
utilisateurs de manière unique, aucune fonction spécifique aux jeux
(ces dernières devant résider dans les contrôleurs).

De plus, nous avons décidé de partir sur un paterne d'architecture de
type *Pâtron de méthode* pour unifier nos contrôleurs. Ainsi, ces
derniers doivent respecter un protocole commun de communication, défini
dans la classe abstraite `ControleurTemplate`.

Au niveau des jeux, la modification de leur logique pour rajouter les
nouvelles fonctionnalités demandées a été faite simplement et
rapidement, grâce à nos choix architecturaux fait précédemment.

Enfin, nous avons continué à utiliser l'intelligence artificielle pour
générer de la documentation des méthodes.