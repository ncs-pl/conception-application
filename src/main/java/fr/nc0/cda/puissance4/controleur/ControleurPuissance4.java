/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.puissance4.controleur;

import fr.nc0.cda.puissance4.modele.Joueur;
import fr.nc0.cda.puissance4.modele.Puissance4;
import fr.nc0.cda.puissance4.vue.Ihm;
import java.util.ArrayList;

public class ControleurPuissance4 {
  private final Ihm ihm;
  private final ArrayList<Joueur> lesJoueurs;
  private Puissance4 nim;

  public ControleurPuissance4(Ihm ihm) {
    this.ihm = ihm;

    while (true) {
      try {
        nim = new Puissance4();
        break;
      } catch (IllegalArgumentException e) {
        ihm.message(e.getMessage());
      }
    }

    lesJoueurs = new ArrayList<Joueur>(2);
    lesJoueurs.add(new Joueur(ihm.selectNomJoueur(1)));
    lesJoueurs.add(new Joueur(ihm.selectNomJoueur(2)));
  }

  public void jouer() {

    nim.demarrerPartie();
    Joueur currentPlayer = lesJoueurs.get(0);

    while (nim.getEtatPartie() == Puissance4.EtatPartie.EnCours) {
      ihm.afficherEtatPartie(nim.getTas());
      while (true) {
        int[] choix = ihm.selectAlumette(currentPlayer.getNom());
        try {
          nim.supprAllumettes(choix);
          break;
        } catch (IllegalArgumentException e) {
          ihm.message(e.getMessage());
        }
      }
      nim.checkEtatPartie();
      if (nim.getEtatPartie() == Puissance4.tatPartie.EnCours) {
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

  private Joueur nextPlayer(Joueur currentPlayer) {
    if (currentPlayer == lesJoueurs.get(0)) {
      return lesJoueurs.get(1);
    } else {
      return lesJoueurs.get(0);
    }
  }
}
