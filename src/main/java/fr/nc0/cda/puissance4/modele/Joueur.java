/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.puissance4.modele;

/**
 * Représente un joueur dans le jeu Puissance 4.
 *
 * <p>Cette classe permet de créer des joueurs avec un nom et de suivre le nombre de parties gagnées
 * par chaque joueur.
 */
public class Joueur {
  /** Le nom du joueur */
  private final String nom;

  /** Le nombre de parties gagnées par le joueur */
  private int nbrPartieGagnee;

  /**
   * Constructeur de la classe Joueur.
   *
   * @param nom Le nom du joueur.
   */
  public Joueur(String nom) {
    this.nom = nom;
    nbrPartieGagnee = 0;
  }

  /**
   * Obtient le nom du joueur.
   *
   * @return Le nom du joueur.
   */
  public String getNom() {
    return nom;
  }

  /**
   * Obtient le nombre de parties gagnées par le joueur.
   *
   * @return Le nombre de parties gagnées par le joueur.
   */
  public int getNbrPartieGagnee() {
    return nbrPartieGagnee;
  }

  /** Incrémente le nombre de parties gagnées par le joueur. */
  public void ajouterPartieGagnee() {
    nbrPartieGagnee++;
  }
}
