/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

import fr.nc0.cda.modele.jeu.Choix;

public class ChoixPuissance4 extends Choix {
  /** Le coup du joueur */
  private final CoupPuissance4 coup;

  /** Le sens de rotation du joueur, si le coup est une rotation */
  private final RotationPuissance4 rotation;

  /** La colonne dans laquelle jouer, si le coup est une insertion */
  private final int colonne;

  ChoixPuissance4(CoupPuissance4 coup, RotationPuissance4 rotation, int colonne) {
    super();
    this.coup = coup;
    this.rotation = rotation;
    this.colonne = colonne;
  }

  /**
   * Le coup que le joueur veut effectuer.
   *
   * @return le coup
   */
  public CoupPuissance4 getCoup() {
    return coup;
  }

  /**
   * Le sens de rotation
   *
   * @return le sens de rotation
   */
  public RotationPuissance4 getRotation() {
    return rotation;
  }

  /**
   * La colonne dans laquelle il faut insérer.
   *
   * @return la colonne
   */
  public int getColonne() {
    return colonne;
  }
}
