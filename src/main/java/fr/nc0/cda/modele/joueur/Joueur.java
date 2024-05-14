/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.joueur.strategie.Strategie;

/** Représente une partie d'un jeu. */
public class Joueur {
  /** Le nom du joueur. */
  private final String nom;

  /** Le nombre de parties gagnées par le joueur. */
  private int victoires = 0;

  /** La stratégie de jeu utilisée */
  private Strategie strategie;

  /** Créer un joueur avec un nom donné. */
  public Joueur(String nom) {
    this.nom = nom;
  }

  /** Récupère le nom du joueur. */
  public String getNom() {
    return nom;
  }

  /** Récupère le nombre de parties gagnées par le joueur. */
  public int getVictoires() {
    return victoires;
  }

  /** Ajoute une partie gagnée au joueur. */
  public void incrementerVictoires() {
    ++victoires;
  }

  /** Modifie la stratégie du joueur */
  public void setStrategie(Strategie strategie) {
    this.strategie = strategie;
  }

  public Strategie getStrategie() {return strategie;}

  // TODO(nc0): meilleure signature
  /*public void jouer( args ) {
   strategie.jouer();
  }*/

  @Override
  public String toString() {
    return nom;
  }
}
