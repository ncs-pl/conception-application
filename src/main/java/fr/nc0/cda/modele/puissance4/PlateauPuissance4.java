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
      List<CellulePuissance4> colonne = new ArrayList<>(hauteur);
      for (int j = 0; j < hauteur; ++j) {
        colonne.add(CellulePuissance4.VIDE);
      }

      grille.add(colonne);
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
   * Insère un jeton dans la colonne.
   *
   * @param colonne la colonne, entre 1 et la hauteur
   * @param cellule le jeton
   * @return la ligne dans laquelle la valeur a été insérée
   */
  public int insererCellule(int colonne, CellulePuissance4 cellule) {
    if (colonne < 1 || colonne > longueur) {
      throw new IllegalArgumentException("La colonne " + colonne + " est invalide");
    }

    if (verifierColonnePleine(colonne)) {
      throw new IllegalArgumentException("Le colonne " + colonne + " est invalide");
    }

    for (int ligne = longueur; ligne > 0; --ligne) {
      if (getCellule(colonne, ligne) == CellulePuissance4.VIDE) {
        setCellule(colonne, ligne, cellule);
        return ligne;
      }
    }

    /* unreachable */
    throw new IllegalArgumentException("La colonne " + colonne + " est invalide");
  }

  /**
   * Retourne la cellule à la position donnée.
   *
   * @param colonne la colonne de la cellule, entre 1 et la longueur de la grille.
   * @param ligne la ligne de la cellule, entre 1 et la hauteur de la grille.
   * @return la cellule à la position donnée, ou {@code null} si la position est invalide.
   */
  public CellulePuissance4 getCellule(int colonne, int ligne) {
    if (colonne < 1 || colonne > longueur || ligne < 1 || ligne > longueur) {
      return null;
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
  public void setCellule(int colonne, int ligne, CellulePuissance4 cellule) {
    if (colonne < 1 || colonne > longueur) {
      throw new IllegalArgumentException("La colonne " + colonne + " est invalide");
    }

    if (ligne < 1 || ligne > hauteur) {
      throw new IllegalArgumentException("La ligne " + ligne + " est invalide");
    }

    grille.get(ligne - 1).set(colonne - 1, cellule);
  }

  /**
   * Vérifie que la colonne demandée est pleine.
   *
   * @param colonne la colonne
   * @return true si la colonne est pleine
   */
  public boolean verifierColonnePleine(int colonne) {
    if (colonne < 1 || colonne > longueur) {
      throw new IllegalArgumentException("La colonne " + colonne + " est invalide");
    }

    return getCellule(colonne, 1) != CellulePuissance4.VIDE;
  }

  /**
   * Vérifie que le plateau soit plein.
   *
   * @return true si le plateau est plein
   */
  public boolean estPlein() {
    for (int i = 1; i <= hauteur; ++i) {
      if (!verifierColonnePleine(i)) {
        return false;
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
