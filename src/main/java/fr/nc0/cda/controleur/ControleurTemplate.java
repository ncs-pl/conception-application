/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.controleur;

import fr.nc0.cda.modele.CoupInvalideException;
import fr.nc0.cda.modele.EtatPartie;
import fr.nc0.cda.modele.EtatPartieException;
import fr.nc0.cda.modele.joueur.Joueur;
import fr.nc0.cda.vue.Ihm;

/**
 * Template pour les contrôleurs de jeu, suivant le design pattern "Template Method", ou "Patron de
 * méthode modèle" en français.
 */
public abstract class ControleurTemplate {
  protected final Ihm ihm;

  protected ControleurTemplate(Ihm ihm) {
    this.ihm = ihm;
  }

  /**
   * Créer l'affichage en string du plateau.
   *
   * @return l'affichage du tableau sous la forme d'un String.
   */
  abstract String creerAffichagePlateau();

  /**
   * Retourne l'état de la partie en cours.
   *
   * @return l'état de la partie.
   */
  abstract EtatPartie getEtatPartie();

  /** Initialise une partie du jeu. */
  abstract void initialiserPartie();

  /** Change le joueur courant au prochain joueur qui doit jouer. */
  abstract void changerJoueurCourant();

  /**
   * Demande au joueur courant son choix pour jouer, et l'effectue. Doit throw les erreurs si
   * besoin.
   *
   * @throws CoupInvalideException erreur de coup
   * @throws EtatPartieException erreur de partie
   */
  abstract void jouerCoup() throws CoupInvalideException, EtatPartieException;

  /**
   * Retourne le joueur demandé.
   *
   * @param numeroJoueur le numéro du joueur, 1 ou 2.
   * @return le joueur correspondant.
   */
  abstract Joueur getJoueur(int numeroJoueur);

  /**
   * Demande si les joueurs veulent rejouer.
   *
   * @return true si les joueurs veulent rejouer, false sinon.
   */
  private boolean demanderRejouer() {
    return ihm.demanderBoolean("Souhaitez-vous rejouer ?");
  }

  /** Jouer une partie de jeu. */
  public void jouer() {
    initialiserPartie();

    while (this.getEtatPartie() == EtatPartie.EN_COURS) {
      ihm.afficherMessage(this.creerAffichagePlateau());

      try {
        jouerCoup();
      } catch (CoupInvalideException | EtatPartieException e) {
        ihm.afficherErreur(e.getMessage());
      }

      changerJoueurCourant();
    }

    ihm.afficherMessage(this.creerAffichagePlateau());
    EtatPartie etatPartie = this.getEtatPartie();

    Joueur gagnant;
    Joueur perdant;
    if (etatPartie == EtatPartie.VICTOIRE_JOUEUR_1) {
      gagnant = getJoueur(1);
      perdant = getJoueur(2);
    } else {
      gagnant = getJoueur(2);
      perdant = getJoueur(1);
    }

    if (etatPartie == EtatPartie.MATCH_NUL) {
      ihm.afficherMessage("Match nul!");
    } else {
      gagnant.incrementerVictoires();
      ihm.afficherMessage("Victoire de " + gagnant.getNom() + " !");
    }
    ihm.afficherScores(gagnant, perdant);

    if (!demanderRejouer()) {
      boolean exaequo = gagnant.getVictoires() == perdant.getVictoires();
      ihm.afficherVainqueur(gagnant, exaequo);
      return;
    }

    jouer(); // Appel récursif
  }
}
