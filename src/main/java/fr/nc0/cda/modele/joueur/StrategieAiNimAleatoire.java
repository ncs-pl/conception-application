/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.Choix;
import fr.nc0.cda.modele.Plateau;
import fr.nc0.cda.modele.nim.ChoixNim;
import fr.nc0.cda.modele.nim.PlateauNim;
import fr.nc0.cda.vue.Ihm;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StrategieAiNimAleatoire implements Strategie {
  @Override
  public Choix jouer(Ihm ihm, Plateau plateau, Joueur joueur) {
    PlateauNim nim = (PlateauNim) plateau;
    Random rand = new Random();

    List<Integer> tasValide = new ArrayList<>();
    for (int i = 1; i <= nim.getTaille(); ++i) {
      if (nim.getAllumettesRestantes(i) > 0) {
        tasValide.add(i);
      }
    }

    int tas = tasValide.get(rand.nextInt(tasValide.size()));
    int allumettesRestantes = nim.getAllumettesRestantes(tas);
    int allumettes = rand.nextInt(Math.min(allumettesRestantes, nim.getContrainte())) + 1;
    return new ChoixNim(tas, allumettes);
  }
}
