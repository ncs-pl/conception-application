/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele;

/** Représente une partie d'un jeu. */
public class Joueur {
  /** Le nom du joueur. */
  private final String nom;

  /** Le nombre de parties gagnées par le joueur. */
  private int nbrPartieGagnee = 0;

  /** Créer un joueur avec un nom donné. */
  public Joueur(String nom) {
    this.nom = nom;
  }

  /** Récupère le nom du joueur. */
  public String getNom() {
    return nom;
  }

  /** Récupère le nombre de parties gagnées par le joueur. */
  public int getNbrPartieGagnee() {
    return nbrPartieGagnee;
  }

  /** Ajoute une partie gagnée au joueur. */
  public void ajouterPartieGagnee() {
    nbrPartieGagnee++;
  }

  @Override
  public String toString() {
    return nom;
  }
}
