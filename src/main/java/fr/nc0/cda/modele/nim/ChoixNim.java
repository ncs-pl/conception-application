/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.nim;

import fr.nc0.cda.modele.jeu.Choix;

/** Les choix possibles pour jouer au jeu de Nim. */
public class ChoixNim implements Choix {
  /** Le tas dans lequel jouer */
  private final int tas;

  /** Le nombre d'allumettes à retirer */
  private final int allumettes;

  public ChoixNim(int tas, int allumettes) {
    this.tas = tas;
    this.allumettes = allumettes;
  }

  /** Le tas dans lequel jouer */
  public int getTas() {
    return tas;
  }

  /** Le nombre d'allumettes à retirer */
  public int getAllumettes() {
    return allumettes;
  }
}
