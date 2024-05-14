/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

/** Enumération des différentes cellules possibles dans une grille de Puissance 4. */
public enum CellulePuissance4 {
  /** Cellule vide. */
  VIDE,
  /** Cellule jaune. */
  JAUNE,
  /** Cellule rouge. */
  ROUGE;

  @Override
  public String toString() {
    // \u001B[0m  reset
    // \u001B[33m yellow
    // \u001B[31m red

    return switch (this) {
      case VIDE -> "⬤";
      case JAUNE -> "\u001B[33m⬤\u001B[0m";
      case ROUGE -> "\u001B[31m⬤\u001B[0m";
    };
  }
}
