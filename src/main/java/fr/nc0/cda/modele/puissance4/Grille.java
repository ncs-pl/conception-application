/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

import java.util.ArrayList;
import java.util.List;

/** Représente la grille de jeu du Puissance 4. */
public class Grille {
  private final List<List<Cellule>> grilleInterne;
  private final int longueur;
  private final int hauteur;

  public Grille(int longueur, int hauteur) {
    this.longueur = longueur;
    this.hauteur = hauteur;

    grilleInterne = new ArrayList<>(longueur);
    for (int i = 0; i < longueur; ++i) {
      List<Cellule> colonne = new ArrayList<>(hauteur);
      for (int j = 0; j < hauteur; ++j) {
        colonne.add(Cellule.VIDE);
      }
      grilleInterne.add(colonne);
    }
  }

  /**
   * Retourne la longueur de la grille.
   *
   * @return la longueur de la grille.
   */
  public int getLongueur() {
    return longueur;
  }

  /**
   * Retourne la hauteur de la grille.
   *
   * @return la hauteur de la grille.
   */
  public int getHauteur() {
    return hauteur;
  }

  /**
   * Retourne la cellule à la position donnée.
   *
   * @param colonne la colonne de la cellule, entre 1 et la longueur de la grille.
   * @param ligne la ligne de la cellule, entre 1 et la hauteur de la grille.
   * @return la cellule à la position donnée, ou {@code null} si la position est invalide.
   */
  public Cellule get(int colonne, int ligne) {
    if (colonne < 1 || colonne > longueur || ligne < 1 || ligne > hauteur) return null;
    return grilleInterne.get(colonne - 1).get(ligne - 1);
  }

  /**
   * Modifie la cellule à la position donnée.
   *
   * @param colonne la colonne de la cellule, entre 1 et la longueur de la grille.
   * @param ligne la ligne de la cellule, entre 1 et la hauteur de la grille.
   * @param cellule la nouvelle cellule.
   */
  public void set(int colonne, int ligne, Cellule cellule) {
    if (colonne < 1 || colonne > longueur || ligne < 1 || ligne > hauteur) return;
    grilleInterne.get(colonne - 1).set(ligne - 1, cellule);
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();

    for (List<Cellule> ligne : grilleInterne) {
      stringBuilder.append(" |");

      for (Cellule cellule : ligne)
        stringBuilder.append(" ").append(cellule.toString()).append(" | ");

      stringBuilder.append("\n");
    }

    stringBuilder.append("   1   2   3   4   5   6   7\n");

    return stringBuilder.toString();
  }
}
