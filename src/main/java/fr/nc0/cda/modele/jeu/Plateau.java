/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.jeu;

/** Le plateau du jeu en cours. */
public interface Plateau {
  /**
   * Vérifie que le plateau soit vide
   *
   * @return true si le plateau est vide.
   */
  boolean estVide();

  String toString();
}
