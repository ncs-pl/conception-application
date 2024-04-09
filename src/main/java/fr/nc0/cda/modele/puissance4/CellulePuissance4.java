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
    return switch (this) {
      case VIDE -> "⚪️";
      case JAUNE -> "🟡";
      case ROUGE -> "🔴";
    };
  }
}
