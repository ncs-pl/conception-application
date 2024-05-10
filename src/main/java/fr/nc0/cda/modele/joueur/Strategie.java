/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

/**
 * Une stratégie est un algorithme qui détermine la façon de jouer un tour par joueur (humain et
 * AI).
 */
public interface Strategie {
  void jouer();
}
