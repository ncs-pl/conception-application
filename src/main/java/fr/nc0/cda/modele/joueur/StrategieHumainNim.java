/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.jeu.Choix;
import fr.nc0.cda.modele.jeu.Plateau;
import fr.nc0.cda.modele.nim.ChoixNim;
import fr.nc0.cda.vue.Ihm;

/** Permet à un humain de jouer au jeu de Nim */
public class StrategieHumainNim implements Strategie {
  @Override
  public Choix jouer(Ihm ihm, Plateau _plateau, Joueur joueur) {
    int[] input =
        ihm.demanderDeuxInt(
            joueur.getNom()
                + " a vous de jouer ! Rentrez le numéro du tas suivi du"
                + " nombre d'allumettes que vous souhaitez enlever.");
    int tas = input[0];
    int allumettes = input[1];
    return new ChoixNim(tas, allumettes);
  }
}
