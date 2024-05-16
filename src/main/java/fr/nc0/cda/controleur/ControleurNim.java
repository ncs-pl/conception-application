/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.controleur;

import fr.nc0.cda.modele.jeu.CoupInvalideException;
import fr.nc0.cda.modele.jeu.EtatPartie;
import fr.nc0.cda.modele.jeu.EtatPartieException;
import fr.nc0.cda.modele.joueur.Joueur;
import fr.nc0.cda.modele.joueur.StrategieGagnanteNim;
import fr.nc0.cda.modele.nim.ChoixNim;
import fr.nc0.cda.modele.nim.JeuNim;
import fr.nc0.cda.vue.Ihm;

/** Contrôleur du jeu de Nim. */
public class ControleurNim extends ControleurTemplate {
  /** Le nombre de tas pour les parties. */
  private final int nombreTas;

  /** Une partie du jeu de Nim */
  private JeuNim nim;

  public ControleurNim(Ihm ihm, Joueur joueur1, Joueur joueur2) {
    super(ihm, joueur1, joueur2);

    while (true) {
      int tas = ihm.demanderInt("Saisissez le nombre de tas pour les parties.");
      if (tas > 0) {
        nombreTas = tas;
        break;
      }

      ihm.afficherErreur("Le nombre de tas ne peut pas être négatif ou nul");
    }
  }

  @Override
  String creerAffichagePlateau() {
    return nim.getPlateau().toString();
  }

  @Override
  EtatPartie getEtatPartie() {
    return nim.getEtatPartie();
  }

  @Override
  void initialiserPartie() {
    if (joueur2.getNom().equalsIgnoreCase("ai")) {
      while (joueur2.getStrategie() == null) {
        String strategie =
            ihm.demanderString("Choisissez la strategie de l'IA (Gagnante/Aleatoire) : ")
                .toLowerCase();
        switch (strategie) {
          case "gagnante", "g":
            {
              joueur2.setStrategie(new StrategieGagnanteNim());
              break;
            }
          default:
            ihm.afficherErreur("La strategie n'existe pas.");
        }
      }
    }

    while (true) {
      int contrainte =
          ihm.demanderInt(
              "Saisissez le nombre maximal d'allumettes à retirer par "
                  + "coup, ou 0 pour ne pas mettre de contrainte");

      if (contrainte >= 0) {
        nim = new JeuNim(nombreTas, contrainte);
        break;
      }

      ihm.afficherErreur("La contrainte ne peut pas être négative");
    }
  }

  @Override
  void jouerCoup() throws CoupInvalideException, EtatPartieException {
    Joueur joueur = getJoueur(joueurCourant);
    ChoixNim choix = (ChoixNim) joueur.getStrategie().jouer(ihm, nim.getPlateau(), joueur);
    nim.jouer(joueurCourant, choix);
  }
}
