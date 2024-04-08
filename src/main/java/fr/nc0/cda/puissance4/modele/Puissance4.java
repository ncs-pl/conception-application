/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.puissance4.modele;

import java.util.ArrayList;
import java.util.List;

/** Représente une partie de Puissance 4. */
public class Puissance4 {
  /** La longueur de la grille */
  private final int longueur;

  /** La hauteur de la grille */
  private final int hauteur;

  /** La grille de jeu. */
  private final List<List<CellulePuissance4>> grille;

  /** L'état de la partie */
  private EtatPartiePuissance4 etat = EtatPartiePuissance4.EN_COURS;

  /** Créer une partie de Puissance 4 et la commence */
  public Puissance4(int longueur, int hauteur) {
    if (longueur < 1) throw new IllegalArgumentException("La longueur doît être supérieure à 0");
    this.longueur = longueur;

    if (hauteur < 1) throw new IllegalArgumentException("La hauteur doît être supérieure à 0");
    this.hauteur = hauteur;

    grille = new ArrayList<>();
    for (int i = 0; i < this.longueur; i++) {
      grille.add(new ArrayList<>());
      for (int j = 0; j < this.hauteur; j++) grille.get(i).add(CellulePuissance4.VIDE);
    }
  }

  /**
   * Utilitaire pour obtenir une cellule à partir de ses coordonnées, ou null si les coordonnées
   * sont invalides.
   *
   * @param colonne la colonne, entre 1 et la longueur définie
   * @param ligne la ligne, entre 1 et la hauteur définie
   * @return la cellule à la position donnée, ou null si les coordonnées sont invalides
   */
  private CellulePuissance4 getCellule(int colonne, int ligne) {
    // On décrémente les indices pour correspondre aux indices de la ArrayList
    return (colonneValide(colonne) || ligneValide(ligne))
        ? this.grille.get(ligne - 1).get(colonne - 1)
        : null;
  }

  /**
   * Modifie la cellule à la position donnée
   *
   * @param colonne la colonne, entre 1 et la longueur définie
   * @param ligne la ligne, entre 1 et la hauteur définie
   * @param cellule la cellule à insérer
   */
  private void setCellule(int colonne, int ligne, CellulePuissance4 cellule) {
    // On décrémente les indices pour correspondre aux indices de la ArrayList
    grille.get(ligne - 1).set(colonne - 1, cellule);
  }

  /**
   * Vérifie si la colonne donnée est pleine
   *
   * @param colonne La colonne à vérifier
   * @return true si la colonne est pleine, false sinon
   */
  private boolean colonnePleine(int colonne) {
    return getCellule(colonne, 1) != CellulePuissance4.VIDE;
  }

  /**
   * Vérifie que la ligne donnée soit valide, c'est-à-dire comprise entre 1 et la hauteur de la
   * grille.
   *
   * @param ligne le numéro de ligne à vérifier, entre 1 et la hauteur de la grille
   * @return true si la ligne est valide, false sinon
   */
  private boolean ligneValide(int ligne) {
    return ligne > 0 && ligne <= hauteur;
  }

  /**
   * Vérifie si les cellules données sont égales (même couleur)
   *
   * @param cellules la liste des cellules à vérifier
   * @return true si les cellules sont égales, false sinon
   */
  private boolean cellulesEgales(CellulePuissance4... cellules) {
    for (int i = 1; i < cellules.length; i++) {
      if (cellules[i] != cellules[0]) return false;
    }

    return true;
  }

  /**
   * Insère un jeton dans la colonne donnée, en le plaçant au plus bas de la colonne.
   *
   * @param colonne La colonne dans laquelle insérer le jeton, entre 1 et la longueur de la grille
   * @param cellule La cellule à insérer
   * @return la ligne à laquelle le jeton a été inséré, ou -1 si la colonne est pleine
   */
  private int insererCellule(int colonne, CellulePuissance4 cellule) {
    // On parcourt la colonne de bas en haut. Par la gravité, nous savons que si
    // une cellule est vide, alors celles du dessus le sont aussi.
    for (int i = this.hauteur; i > 0; --i)
      if (getCellule(colonne, i) == CellulePuissance4.VIDE) {
        setCellule(colonne, i, cellule);
        return i;
      }

    return -1;
  }

  /**
   * Vérifie si une condition de victoire est remplie pour la cellule donnée
   *
   * @param colonne la colonne de la cellule insérée, entre 1 et la longueur de la grille
   * @param ligne la ligne de la cellule insérée, entre 1 et la hauteur de la grille
   * @return true si une condition de victoire est remplie, false sinon
   */
  private boolean celluleVictorieuse(int colonne, int ligne) {
    // ⚪ -> Une cellule, peu importe la couleur
    // 🔴 -> Cellule de la même couleur que la cellule actuelle
    // ⭕️ -> Cellule actuelle

    if (cellulesEgales(
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne),
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne + 1),
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne + 2),
        /* ⭕️🔴🔴🔴 */ getCellule(colonne, ligne + 3))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne - 1),
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne),
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne + 1),
        /* 🔴⭕️🔴🔴 */ getCellule(colonne, ligne + 2))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne - 2),
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne - 1),
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne),
        /* 🔴🔴⭕️🔴 */ getCellule(colonne, ligne + 1))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne - 3),
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne - 2),
        /* ⚪⚪⚪⚪ */ getCellule(colonne, ligne - 1),
        /* 🔴🔴🔴⭕️ */ getCellule(colonne, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⭕️ */ getCellule(colonne, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 1, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 2, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 3, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ getCellule(colonne - 1, ligne),
        /* ⚪⚪⚪⭕️ */ getCellule(colonne, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 1, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 2, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ getCellule(colonne - 2, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne - 1, ligne),
        /* ⚪⚪⚪⭕️ */ getCellule(colonne, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 1, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ getCellule(colonne - 3, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne - 2, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne - 1, ligne),
        /* ⚪⚪⚪⭕️ */ getCellule(colonne, ligne))) return true;

    if (cellulesEgales(
        /* ⭕️⚪⚪⚪ */ getCellule(colonne, ligne),
        /* ⚪🔴⚪⚪ */ getCellule(colonne + 1, ligne + 1),
        /* ⚪⚪🔴⚪ */ getCellule(colonne + 2, ligne + 2),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 3, ligne + 3))) return true;

    if (cellulesEgales(
        /* 🔴⚪⚪⚪ */ getCellule(colonne - 1, ligne - 1),
        /* ⚪⭕️⚪⚪ */ getCellule(colonne, ligne),
        /* ⚪⚪🔴⚪ */ getCellule(colonne + 1, ligne + 1),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 2, ligne + 2))) return true;

    if (cellulesEgales(
        /* 🔴⚪⚪⚪ */ getCellule(colonne - 2, ligne - 2),
        /* ⚪🔴⚪⚪ */ getCellule(colonne - 1, ligne - 1),
        /* ⚪⚪⭕️⚪ */ getCellule(colonne, ligne),
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 1, ligne + 1))) return true;

    if (cellulesEgales(
        /* 🔴⚪⚪⚪ */ getCellule(colonne - 3, ligne - 3),
        /* ⚪🔴⚪⚪ */ getCellule(colonne - 2, ligne - 2),
        /* ⚪⚪🔴⚪ */ getCellule(colonne - 1, ligne - 1),
        /* ⚪⚪⚪⭕️ */ getCellule(colonne, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⭕️ */ getCellule(colonne, ligne),
        /* ⚪⚪🔴⚪ */ getCellule(colonne - 1, ligne + 1),
        /* ⚪🔴⚪⚪ */ getCellule(colonne - 2, ligne + 2),
        /* 🔴⚪⚪⚪ */ getCellule(colonne - 3, ligne + 3))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 1, ligne - 1),
        /* ⚪⚪⭕️⚪ */ getCellule(colonne, ligne),
        /* ⚪🔴⚪⚪ */ getCellule(colonne - 1, ligne + 1),
        /* 🔴⚪⚪⚪ */ getCellule(colonne - 2, ligne + 2))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 2, ligne - 2),
        /* ⚪⚪🔴⚪ */ getCellule(colonne + 1, ligne - 1),
        /* ⚪⭕️⚪⚪ */ getCellule(colonne, ligne),
        /* 🔴⚪⚪⚪ */ getCellule(colonne - 1, ligne + 1))) return true;

    return cellulesEgales(
        /* ⚪⚪⚪🔴 */ getCellule(colonne + 3, ligne - 3),
        /* ⚪⚪🔴⚪ */ getCellule(colonne + 2, ligne - 2),
        /* ⚪🔴⚪⚪ */ getCellule(colonne + 1, ligne - 1),
        /* ⭕️⚪⚪⚪ */ getCellule(colonne, ligne));
  }

  /**
   * Vérifie si la grille est pleine en vérifiant si chaque colonne est pleine.
   *
   * @return true si la grille est pleine, false sinon
   */
  private boolean grillePleine() {
    for (int i = 1; i < this.longueur; ++i) if (colonnePleine(i)) return false;

    return true;
  }

  /**
   * Vérifie l'état de la partie à partir de la grille à partir de la cellule insérée.
   *
   * @param colonne la colonne de la cellule insérée, entre 1 et la longueur de la grille
   * @param ligne la ligne de la cellule insérée, entre 1 et la hauteur de la grille
   */
  private void actualiserEtatPartie(int colonne, int ligne) {
    CellulePuissance4 cellule = getCellule(colonne, ligne);
    if (cellule == CellulePuissance4.VIDE) return;

    if (celluleVictorieuse(colonne, ligne))
      etat =
          cellule == CellulePuissance4.ROUGE
              ? EtatPartiePuissance4.VICTOIRE_ROUGE
              : EtatPartiePuissance4.VICTOIRE_JAUNE;
    else if (grillePleine()) etat = EtatPartiePuissance4.MATCH_NUL;
    else etat = EtatPartiePuissance4.EN_COURS;
  }

  /** Actualise l'état de la partie en itérant sur la grille. */
  private void actualiserEtatPartie() {
    for (int i = 1; i <= longueur; i++)
      for (int j = 1; j <= hauteur; j++) actualiserEtatPartie(i, j);
  }

  // ===========================================================================
  // Public API
  // ===========================================================================

  /**
   * Retourne la grille de jeu
   *
   * @return La grille de jeu
   */
  public List<List<CellulePuissance4>> getGrille() {
    return grille;
  }

  /**
   * Retourne l'état de la partie
   *
   * @return L'état de la partie
   */
  public EtatPartiePuissance4 getEtat() {
    return etat;
  }

  /**
   * Vérifie que le numéro de colonne est valide.
   *
   * <p>Une colonne est considérée valide si elle est comprise entre 1 et la longueur de la grille.
   *
   * @param colonne le numéro de colonne
   * @return true si la colonne est valide
   */
  public boolean colonneValide(int colonne) {
    return colonne > 0 && colonne <= longueur;
  }

  /**
   * Joue un coup dans la colonne donnée
   *
   * @param jeton le jeton à insérer
   * @param colonne la colonne dans laquelle jouer, entre 1 et 7 (inclus)
   * @throws IllegalArgumentException si la colonne n'existe pas où est pleine
   * @throws IllegalStateException si la partie est terminée
   */
  public void jouer(CellulePuissance4 jeton, int colonne) {
    if (etat != EtatPartiePuissance4.EN_COURS)
      throw new IllegalStateException("La partie est terminée");

    if (!colonneValide(colonne))
      throw new IllegalArgumentException("La colonne doit être comprise entre 1 et 7");

    if (colonnePleine(colonne)) throw new IllegalArgumentException("La colonne est pleine");

    int ligne = insererCellule(colonne, jeton);
    actualiserEtatPartie(colonne, ligne);
  }
}
