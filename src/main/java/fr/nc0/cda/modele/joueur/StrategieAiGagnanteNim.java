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
    int resultatXor = 0;

    for (int i = 1; i <= nim.taille; ++i) {
      resultatXor ^= nim.getAllumettesRestantes(i);
    }

    int allumettes = 0;
    int tas = 0;

    if (resultatXor == 0) {
      allumettes = 1;
      for (int i = 1; i <= nim.taille; ++i) {
        if (nim.getAllumettesRestantes(i) != 0) {
          tas = i;
          break;
        }
      }
    } else {
      for (int i = 1; i <= nim.taille; ++i) {
        int nb = resultatXor ^ nim.getAllumettesRestantes(i);
        if (nb < nim.getAllumettesRestantes(i)) {
          allumettes = nim.getAllumettesRestantes(i) - nb;
          tas = i;
          break;
        }
      }
    }

    return new ChoixNim(tas, allumettes);
  }
}
