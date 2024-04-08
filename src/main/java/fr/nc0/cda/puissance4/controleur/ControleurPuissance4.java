/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.puissance4.controleur;

import fr.nc0.cda.puissance4.modele.CellulePuissance4;
import fr.nc0.cda.puissance4.modele.EtatPartiePuissance4;
import fr.nc0.cda.puissance4.modele.Joueur;
import fr.nc0.cda.puissance4.modele.Puissance4;
import fr.nc0.cda.puissance4.vue.Ihm;
import java.util.ArrayList;

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

  /** L'interface utilisateur */
  private final Ihm ihm;

  /** La liste des joueurs */
  private final ArrayList<Joueur> lesJoueurs;

  /**
   * Constructeur de la classe ControleurPuissance4.
   *
   * @param ihm L'interface utilisateur pour les interactions avec le jeu.
   */
  public ControleurPuissance4(Ihm ihm) {
    this.ihm = ihm;

    lesJoueurs = new ArrayList<>(2);
    lesJoueurs.add(new Joueur(ihm.selectNomJoueur(1)));
    lesJoueurs.add(new Joueur(ihm.selectNomJoueur(2)));
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
    Joueur joueurCourant = lesJoueurs.get(0);

    while (p4.getEtat() == EtatPartiePuissance4.EN_COURS) {
      ihm.afficherGrille(p4.getGrille());
      boolean numeroInvalide = true;

      while (numeroInvalide) {
        try {
          int colonne = ihm.choixColonne(joueurCourant.getNom());
          CellulePuissance4 jeton = jetonJoueur(joueurCourant);
          p4.jouer(jeton, colonne);

          numeroInvalide = false;
        } catch (IllegalArgumentException e) {
          ihm.message("Erreur : " + e.getMessage());
        }
      }

      if (p4.getEtat() == EtatPartiePuissance4.EN_COURS)
        joueurCourant = joueurSuivant(joueurCourant);
    }

    ihm.afficherGrille(p4.getGrille());

    if (p4.getEtat() != EtatPartiePuissance4.EN_COURS) {
      if (p4.getEtat() == EtatPartiePuissance4.MATCH_NUL) ihm.matchNul();
      else {
        joueurCourant.ajouterPartieGagnee();
        ihm.afficherGagnant(joueurCourant.getNom());
      }

      if (ihm.rejouer()) {
        jouer();
        return;
      }

      Joueur perdant = joueurSuivant(joueurCourant);
      boolean exaeco = joueurCourant.getNbrPartieGagnee() == perdant.getNbrPartieGagnee();
      ihm.afficherStats(
          joueurCourant.getNom(),
          perdant.getNom(),
          joueurCourant.getNbrPartieGagnee(),
          perdant.getNbrPartieGagnee(),
          exaeco);
    }
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
