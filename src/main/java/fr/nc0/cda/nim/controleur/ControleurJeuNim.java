/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.nim.controleur;

import fr.nc0.cda.nim.modele.EtatPartieNim;
import fr.nc0.cda.nim.modele.Joueur;
import fr.nc0.cda.nim.modele.Nim;
import fr.nc0.cda.nim.vue.Ihm;
import java.util.ArrayList;

/** Contrôleur du jeu de Nim. */
public class ControleurJeuNim {
  /** Interface homme-machine. */
  private final Ihm ihm;

  /** Liste des joueurs de la partie. */
  private final ArrayList<Joueur> lesJoueurs;

  /** Partie en cours du jeu de Nim. */
  private Nim nim;

  /**
   * Créer un contrôleur de jeu de Nim.
   *
   * @param ihm l'interface homme-machine.
   */
  public ControleurJeuNim(Ihm ihm) {
    this.ihm = ihm;

    while (true) {
      try {
        nim = new Nim(ihm.selectNbrTas());
        break;
      } catch (IllegalArgumentException e) {
        ihm.message(e.getMessage());
      }
    }

    lesJoueurs = new ArrayList<>(2);
    lesJoueurs.add(new Joueur(ihm.selectNomJoueur(1)));
    lesJoueurs.add(new Joueur(ihm.selectNomJoueur(2)));
  }

  /** Jouer une partie du jeu de Nim. */
  public void jouer() {
    int contrainte = ihm.selectContrainte();
    nim.demarrerPartie();
    Joueur currentPlayer = lesJoueurs.get(0);

    while (nim.getEtatPartie() == EtatPartieNim.EN_COURS) {
      ihm.afficherEtatPartie(nim.getTas());
      while (true) {
        int[] choix = ihm.selectAlumette(currentPlayer.getNom());
        try {
          nim.supprAllumettes(choix, contrainte);
          break;
        } catch (IllegalArgumentException e) {
          ihm.message(e.getMessage());
        }
      }
      nim.checkEtatPartie();
      if (nim.getEtatPartie() == EtatPartieNim.EN_COURS) {
        currentPlayer = nextPlayer(currentPlayer);
      }
    }

    currentPlayer.ajouterPartieGagnee();
    Joueur gagnant = currentPlayer;
    Joueur perdant = nextPlayer(currentPlayer);
    if (ihm.finPartie(
        gagnant.getNom(),
        perdant.getNom(),
        gagnant.getNbrPartieGagnee(),
        perdant.getNbrPartieGagnee())) {
      jouer();
    } else {
      boolean exaequo = false;
      if (lesJoueurs.get(0).getNbrPartieGagnee() > lesJoueurs.get(1).getNbrPartieGagnee()) {
        gagnant = lesJoueurs.get(0);
        perdant = lesJoueurs.get(1);
      } else if (lesJoueurs.get(0).getNbrPartieGagnee() < lesJoueurs.get(1).getNbrPartieGagnee()) {
        gagnant = lesJoueurs.get(1);
        perdant = lesJoueurs.get(0);
      } else {
        exaequo = true;
      }
      ihm.afficheGagnant(
          gagnant.getNom(),
          perdant.getNom(),
          gagnant.getNbrPartieGagnee(),
          perdant.getNbrPartieGagnee(),
          exaequo);
    }
  }

  /**
   * Récupère le joueur suivant.
   *
   * @param currentPlayer le joueur actuel.
   * @return le joueur suivant.
   */
  private Joueur nextPlayer(Joueur currentPlayer) {
    if (currentPlayer == lesJoueurs.get(0)) {
      return lesJoueurs.get(1);
    } else {
      return lesJoueurs.get(0);
    }
  }
}
