/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.jeu.Choix;
import fr.nc0.cda.modele.jeu.Plateau;
import fr.nc0.cda.modele.nim.ChoixNim;
import fr.nc0.cda.modele.nim.PlateauNim;
import fr.nc0.cda.vue.Ihm;

public class StrategieAiGagnanteNim implements Strategie {
  @Override
  public Choix jouer(Ihm ihm, Plateau plateau, Joueur joueur) {
    PlateauNim nim = (PlateauNim) plateau;
    int taille = nim.getTaille();

    int resultatXor = 0;
    for (int i = 1; i <= taille; ++i) {
      resultatXor ^= nim.getAllumettesRestantes(i);
    }

    // Si aucune idée, on enlève une allumette là où possible
    if (resultatXor == 0) {
      for (int i = 1; i <= taille; ++i) {
        if (nim.getAllumettesRestantes(i) != 0) {
          return new ChoixNim(i, 1);
        }
      }
    }

    for (int i = 1; i <= taille; ++i) {
      int allumettesRestantes = nim.getAllumettesRestantes(i);
      int aRetirer = resultatXor ^ allumettesRestantes;

      if (aRetirer < allumettesRestantes) {
        return new ChoixNim(i, allumettesRestantes - aRetirer);
      }
    }

    /* unreachable */
    return new ChoixNim(1, 1);
  }
}
