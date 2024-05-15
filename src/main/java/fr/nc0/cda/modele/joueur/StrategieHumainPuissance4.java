/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.jeu.Choix;
import fr.nc0.cda.modele.jeu.Plateau;
import fr.nc0.cda.modele.puissance4.ChoixPuissance4;
import fr.nc0.cda.modele.puissance4.CoupPuissance4;
import fr.nc0.cda.modele.puissance4.RotationPuissance4;
import fr.nc0.cda.vue.Ihm;

/** Permet à un humain de jouer au jeu du Puissance 4 */
public class StrategieHumainPuissance4 implements Strategie {
  @Override
  public Choix jouer(Ihm ihm, Plateau plateau, Joueur joueur) {
    while (true) {
      Object input =
          ihm.demanderIntOuString(
              joueur.getNom()
                  + " a vous de jouer ! Entrez le numéro d'une colonne pour y"
                  + " insérer un jeton, ou \"droite\" ou \"gauche\" pour"
                  + " effectuer une rotation de la grille.");

      if (input instanceof Integer) {
        return new ChoixPuissance4(CoupPuissance4.INSERTION, null, (Integer) input);
      } else if (input instanceof String) {
        switch (((String) input).toLowerCase()) {
          case "droite", "d", "horaire":
            return new ChoixPuissance4(CoupPuissance4.ROTATION, RotationPuissance4.ANTI_HORAIRE, 0);
          case "gauche", "g", "anti-horaire":
            return new ChoixPuissance4(CoupPuissance4.ROTATION, RotationPuissance4.HORAIRE, 0);
          default:
            ihm.afficherErreur(
                "Veuillez choisir entre \"droite\" et \"gauche\", ou alors entrez un numéro de colonne.");
        }
      } else {
        ihm.afficherErreur("Entrée invalide."); /* unreachable */
      }
    }
  }
}
