/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.jeu.Choix;
import fr.nc0.cda.modele.jeu.Plateau;
import fr.nc0.cda.vue.Ihm;

/**
 * Une stratégie est un algorithme qui détermine la façon de jouer un tour par joueur (humain et
 * AI).
 */
public interface Strategie {
  /**
   * Laisse le joueur effectuer un tour
   *
   * @param ihm - l'IHM pour communiquer avec le joueur, si besoin
   * @param plateau - le plateau du jeu actuel
   */
  Choix jouer(Ihm ihm, Plateau plateau);
}
