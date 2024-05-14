/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.controleur;

import fr.nc0.cda.modele.jeu.CoupInvalideException;
import fr.nc0.cda.modele.jeu.EtatPartie;
import fr.nc0.cda.modele.jeu.EtatPartieException;
import fr.nc0.cda.modele.nim.ChoixNim;
import fr.nc0.cda.modele.nim.JeuNim;
import fr.nc0.cda.vue.Ihm;

/** Contrôleur du jeu de Nim. */
public class ControleurNim extends ControleurTemplate {
  /** Le nombre de tas pour les parties. */
  private final int nombreTas;

  /** Une partie du jeu de Nim */
  private JeuNim jeuNim;

  /**
   * Créer un contrôleur de jeu de Nim.
   *
   * @param ihm l'interface homme-machine.
   */
  public ControleurNim(Ihm ihm) {
    super(ihm);

    while (true) {
      int tas = ihm.demanderInt("Saisissez le nombre de tas pour la partie");
      if (tas > 0) {
        nombreTas = tas;
        break;
      }

      ihm.afficherErreur("Le nombre de tas ne peut pas être négatif ou nul");
    }
  }

  @Override
  String creerAffichagePlateau() {
    return jeuNim.getPlateauNim().toString();
  }

  @Override
  EtatPartie getEtatPartie() {
    return jeuNim.getEtatPartie();
  }

  @Override
  void initialiserPartie() {
    while (true) {
      int contrainte =
          ihm.demanderInt(
              "Saisissez le nombre maximal d'allumettes à retirer par "
                  + "coup, ou 0 pour ne pas mettre de contrainte");

      if (contrainte >= 0) {
        jeuNim = new JeuNim(nombreTas, contrainte);
        break;
      }

      ihm.afficherErreur("La contrainte ne peut pas être négative");
    }
  }

  @Override
  void jouerCoup() throws CoupInvalideException, EtatPartieException {
    while (true) {
      ChoixNim choix =
          (ChoixNim) getJoueur(joueurCourant).getStrategie().jouer(ihm, jeuNim.getPlateauNim());
      int tas = choix.getTas();
      int allumettes = choix.getAllumettes();
      int contrainte = jeuNim.getContrainte();

      if (tas < 1 || tas > jeuNim.getPlateauNim().taille) {
        ihm.afficherErreur("Le tas choisi n'existe pas.");
        continue;
      }

      if (jeuNim.getPlateauNim().getAllumettesRestantes(tas) < 1) {
        ihm.afficherErreur("Le tas choisi est vide.");
        continue;
      }

      if (allumettes < 1) {
        ihm.afficherErreur("Nombre d'allumettes invalide.");
        continue;
      }

      if (allumettes > jeuNim.getPlateauNim().getAllumettesRestantes(tas)) {
        ihm.afficherErreur("Nombre d'allumettes supérieur au nombre d'allumettes dans le tas.");
        continue;
      }

      if (contrainte != 0 && allumettes > contrainte) {
        ihm.afficherErreur("Nombre d'allumettes supérieur à la contrainte.");
        continue;
      }

      jeuNim.retirerAllumettes(joueurCourant, tas, allumettes);
      break;
    }
  }
}
