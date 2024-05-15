/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

import fr.nc0.cda.modele.jeu.Plateau;
import java.util.ArrayList;
import java.util.List;

/** Représente la grille de jeu du Puissance 4. */
public class PlateauPuissance4 implements Plateau {
  /** Les colonnes de la grille */
  private final List<List<CellulePuissance4>> grille;

  /** La longueur de la grille */
  private final int longueur;

  /** La hauteur de la grille */
  private final int hauteur;

  public PlateauPuissance4(int longueur, int hauteur) {
    this.longueur = longueur;
    this.hauteur = hauteur;

    grille = new ArrayList<>(longueur);
    for (int i = 0; i < longueur; ++i) {
      ArrayList<CellulePuissance4> cellules = new ArrayList<>(longueur);
      for (int j = 0; j < longueur; ++j) {
        cellules.add(CellulePuissance4.VIDE);
      }
      grille.add(cellules);
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
  public CellulePuissance4 get(int colonne, int ligne) {
    if (colonne < 1 || colonne > longueur) {
      throw new IllegalArgumentException("colonne invalide");
    }

    if (ligne < 1 || ligne > hauteur) {
      throw new IllegalArgumentException("ligne invalide");
    }

    return grille.get(ligne - 1).get(colonne - 1);
  }

  /**
   * Modifie la cellule à la position donnée.
   *
   * @param colonne la colonne de la cellule, entre 1 et la longueur de la grille.
   * @param ligne la ligne de la cellule, entre 1 et la hauteur de la grille.
   * @param cellule la nouvelle cellule.
   */
  public void set(int colonne, int ligne, CellulePuissance4 cellule) {
    if (colonne < 1 || colonne > longueur) {
      throw new IllegalArgumentException("colonne invalide");
    }

    if (ligne < 1 || ligne > hauteur) {
      throw new IllegalArgumentException("ligne invalide");
    }

    grille.get(ligne - 1).set(colonne - 1, cellule);
  }

  @Override
  public boolean estVide() {
    for (List<CellulePuissance4> cellules : grille) {
      for (CellulePuissance4 cellule : cellules) {
        if (cellule != CellulePuissance4.VIDE) {
          return false;
        }
      }
    }

    return true;
  }

  @SuppressWarnings("StringConcatenationInLoop")
  @Override
  public String toString() {
    String string = "";

    for (List<CellulePuissance4> ligne : grille) {
      string += "    ";
      for (CellulePuissance4 cellule : ligne) {
        string += " " + cellule.toString();
      }
      string += "\n";
    }

    string += "    ";
    for (int i = 1; i <= longueur; ++i) {
      string += " \033[1m" + i + "\033[0m";
    }
    string += "\n";

    return string;
  }
}
