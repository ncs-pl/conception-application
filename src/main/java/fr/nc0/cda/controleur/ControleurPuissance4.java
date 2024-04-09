/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.controleur;

import fr.nc0.cda.modele.EtatPartie;
import fr.nc0.cda.modele.Joueur;
import fr.nc0.cda.modele.puissance4.*;
import fr.nc0.cda.vue.Ihm;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur du jeu Puissance 4.
 *
 * <p>Cette classe gère le déroulement d'une partie de Puissance 4 en utilisant le modèle Puissance4
 * et l'interface utilisateur Ihm.
 */
public class ControleurPuissance4 {
  /** La longueur d'une grille de Puissance 4 */
  private static final int LONGUEUR = 7;

  /** La hauteur d'une grille de Puissance 4 */
  private static final int HAUTEUR = 7;

  /** Rotations disponibles par défaut */
  private static final int ROTATIONS_DISPONIBLES_DEFAUT = 4;

  /** L'interface utilisateur */
  private final Ihm ihm;

  /** La liste des joueurs */
  private final ArrayList<Joueur> lesJoueurs;

  /** Les rotations restantes pour chaque joueur */
  private List<Integer> rotationsRestantes;

  /**
   * Constructeur de la classe ControleurPuissance4.
   *
   * @param ihm L'interface utilisateur pour les interactions avec le jeu.
   */
  public ControleurPuissance4(Ihm ihm) {
    this.ihm = ihm;

    lesJoueurs = new ArrayList<>(2);
    lesJoueurs.add(new Joueur(demanderNomJoueur(1)));
    lesJoueurs.add(new Joueur(demanderNomJoueur(2)));
  }

  /**
   * Demande le nom d'un joueur
   *
   * @param numJoueur le numéro du joueur demandé
   * @return le nom entré
   */
  private String demanderNomJoueur(int numJoueur) {
    return ihm.demanderString("Saisissez le nom du joueur " + numJoueur);
  }

  /**
   * Retourne le nombre de rotations restantes pour un joueur.
   *
   * @param joueur le joueur pour lequel on veut le nombre de rotations restantes
   * @return le nombre de rotations restantes pour le joueur
   */
  private int rotationsRestantesJoueur(Joueur joueur) {
    return joueur == lesJoueurs.get(0) ? rotationsRestantes.get(0) : rotationsRestantes.get(1);
  }

  /**
   * Diminue le nombre de rotations restantes pour un joueur.
   *
   * @param joueur le joueur pour lequel on veut diminuer le nombre de rotations restantes
   */
  private void diminuerRotationsJoueur(Joueur joueur) {
    if (joueur == lesJoueurs.get(0)) rotationsRestantes.set(0, rotationsRestantes.get(0) - 1);
    else rotationsRestantes.set(1, rotationsRestantes.get(1) - 1);
  }

  /**
   * Demande une colonne valide à un joueur.
   *
   * @param joueur le joueur qui doit choisir
   * @param p4 la partie en cours
   * @return la colonne choisie par le joueur
   */
  private int demanderColonne(Puissance4 p4, Joueur joueur) {
    while (true) {
      int colonne =
          ihm.demanderInt(joueur.getNom() + ", choisissez une colonne (1-" + LONGUEUR + ")");

      if (p4.colonneInvalide(colonne)) {
        ihm.afficherErreur("Veuillez choisir une colonne valide.");
        continue;
      }

      return colonne;
    }
  }

  /**
   * Demande une rotation à un joueur.
   *
   * @param joueur le joueur qui doit choisir
   * @return la rotation choisie par le joueur
   */
  private RotationPuissance4 demanderRotation(Joueur joueur) {
    while (true) {
      String choix =
          ihm.demanderString(
                  joueur.getNom()
                      + ", dans quel sens voulez-vous tourner la grille ? (droite/gauche)")
              .toLowerCase();

      switch (choix) {
        case "droite", "d", "horaire":
          return RotationPuissance4.HORAIRE;
        case "gauche", "g", "anti-horaire":
          return RotationPuissance4.ANTI_HORAIRE;
        default:
          ihm.afficherErreur("Veuillez choisir \"droite\" et \"gauche\".");
      }
    }
  }

  /**
   * Demande au joueur s'il veut jouer ou effectuer une rotation.
   *
   * @param joueur le joueur qui doit choisir
   * @return le choix du joueur
   */
  public ChoixJouerPuissance4 demanderChoixJouer(Joueur joueur) {
    while (true) {
      String choix =
          ihm.demanderString(joueur.getNom() + ", que voulez-vous faire ? (jouer/rotation)")
              .toLowerCase();

      switch (choix) {
        case "jouer", "j":
          return ChoixJouerPuissance4.JOUER;
        case "rotation", "r":
          return ChoixJouerPuissance4.ROTATION;
        default:
          ihm.afficherErreur("Veuillez choisir entre \"jouer\" et \"rotation\".");
      }
    }
  }

  /**
   * Demande si les joueurs veulent rejouer.
   *
   * @return true si les joueurs veulent rejouer, false sinon.
   */
  private boolean demanderRejouer() {
    return ihm.demanderBoolean("Voulez-vous rejouer ?");
  }

  /**
   * Demande si les joueurs veulent activer la rotation de la grille.
   *
   * @return true si les joueurs veulent activer la rotation, false sinon.
   */
  private boolean demanderActivationRotations() {
    return ihm.demanderBoolean("Voulez-vous activer la possibilité de rotation de la grille ?");
  }

  /**
   * Lance une partie de Puissance 4.
   *
   * <p>Cette méthode gère le déroulement d'une partie de Puissance 4 en mode interactif avec les
   * joueurs. Elle affiche la grille de jeu à chaque tour, permet aux joueurs de choisir une colonne
   * où placer leur jeton, vérifie si la partie est terminée, affiche le gagnant ou un match nul, et
   * propose éventuellement de rejouer.
   */
  public void jouer() {
    Puissance4 p4 = new Puissance4(LONGUEUR, HAUTEUR);
    boolean rotation = demanderActivationRotations();
    rotationsRestantes = List.of(ROTATIONS_DISPONIBLES_DEFAUT, ROTATIONS_DISPONIBLES_DEFAUT);

    Joueur joueurCourant = lesJoueurs.get(0);

    // Game loop

    while (p4.getEtat() == EtatPartie.EN_COURS) {
      ihm.afficherMessage(p4.getGrille().toString());

      // Demander au joueur s'il veut jouer ou effectuer une rotation
      // seulement si la rotation est activée et que le joueur a encore des rotations
      if (rotation
          && rotationsRestantesJoueur(joueurCourant) > 0
          && demanderChoixJouer(joueurCourant) == ChoixJouerPuissance4.ROTATION) {
        diminuerRotationsJoueur(joueurCourant);
        RotationPuissance4 sensRotation = demanderRotation(joueurCourant);
        p4.rotationner(sensRotation);
      } else {
        int colonne = demanderColonne(p4, joueurCourant);
        p4.jouer(jetonJoueur(joueurCourant), colonne);
      }

      joueurCourant = joueurSuivant(joueurCourant);
    }

    // Après la fin de la partie

    Joueur gagnant =
        p4.getEtat() == EtatPartie.VICTOIRE_JOUEUR_1 ? lesJoueurs.get(0) : lesJoueurs.get(1);
    Joueur perdant =
        p4.getEtat() == EtatPartie.VICTOIRE_JOUEUR_1 ? lesJoueurs.get(1) : lesJoueurs.get(0);

    if (p4.getEtat() == EtatPartie.MATCH_NUL) ihm.afficherMessage("Match nul !");
    else {
      gagnant.ajouterPartieGagnee();
      ihm.afficherMessage("Victoire de " + gagnant.getNom() + " !");
    }

    boolean rejouer = demanderRejouer();
    if (!rejouer) {
      ihm.afficherScores(gagnant, perdant);
      ihm.afficherVainqueur(gagnant, gagnant.getNbrPartieGagnee() == perdant.getNbrPartieGagnee());

      return;
    }

    ihm.afficherScores(gagnant, perdant);
    // Appel récursif à la fin pour profiter de la Tail-Call Optimization
    jouer();
  }

  /**
   * Retourne le jeton du joueur. Utile pour déterminer le jeton à insérer pour le joueur courant.
   *
   * @param joueur Le joueur pour lequel on veut le jeton.
   * @return Le jeton du joueur.
   */
  private CellulePuissance4 jetonJoueur(Joueur joueur) {
    return joueur == lesJoueurs.get(0) ? CellulePuissance4.ROUGE : CellulePuissance4.JAUNE;
  }

  /**
   * Détermine le joueur suivant dans l'ordre de jeu.
   *
   * @param joueurCourant Le joueur actuel.
   * @return Le joueur suivant.
   */
  private Joueur joueurSuivant(Joueur joueurCourant) {
    if (joueurCourant == lesJoueurs.get(0)) {
      return lesJoueurs.get(1);
    } else {
      return lesJoueurs.get(0);
    }
  }
}
