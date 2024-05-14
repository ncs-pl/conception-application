/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.jeu;

/** Représente les états possibles d'une partie à deux joueurs. */
public enum EtatPartie {
  /** La partie est en cours. */
  EN_COURS,
  /** Le joueur 1 a gagné. */
  VICTOIRE_JOUEUR_1,
  /** Le joueur 2 a gagné. */
  VICTOIRE_JOUEUR_2,
  /** Match nul. */
  MATCH_NUL
}
