/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele;

public class Joueur {

  private final String nom;
  private int nbrPartieGagnee;

  public Joueur(String nom) {
    this.nom = nom;
    nbrPartieGagnee = 0;
  }

  public String getNom() {
    return nom;
  }

  public int getNbrPartieGagnee() {
    return nbrPartieGagnee;
  }

  public void ajouterPartieGagnee() {
    nbrPartieGagnee++;
  }

  @Override
  public String toString() {
    return "Joueur{" + "nom='" + nom + '\'' + ", nbrPartieGagnee=" + nbrPartieGagnee + '}';
  }
}
