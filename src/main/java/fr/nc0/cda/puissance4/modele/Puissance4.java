/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.puissance4.modele;

import java.util.ArrayList;
import java.util.Collections;
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

  /** Le joueur courant */
  private CellulePuissance4 joueurCourant = CellulePuissance4.ROUGE;

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
   * Insère un jeton dans la colonne donnée.
   *
   * @param colonne La colonne dans laquelle insérer le jeton
   * @param cellule La cellule à insérer
   * @return les coordonnées de la cellule insérée
   */
  private List<Integer> insererCellule(int colonne, CellulePuissance4 cellule) {
    // On parcourt la colonne de bas en haut
    for (int i = this.hauteur - 1; i >= 0; i--) {
      if (getCellule(colonne, i) == CellulePuissance4.VIDE) {
        // Si la cellule actuelle est vide, alors on sait (par gravité)
        // que les cellules du dessus sont aussi vides, et donc on peut
        // insérer le jeton à cette position et sortir de la boucle.
        setCellule(colonne, i, cellule);
        return new ArrayList<>(List.of(colonne, i));
      }
    }
    return Collections.emptyList();
  }

  /**
   * Vérifie que la colonne donnée existe
   *
   * @param colonne La colonne dans laquelle jouer
   * @return true si la colonne existe, false sinon
   */
  private boolean verifierNumeroColonne(int colonne) {
    return colonne >= 0 && colonne < this.longueur;
  }

  /**
   * Retourne la cellule à la position donnée
   *
   * @param colonne la colonne
   * @param ligne la ligne
   * @return la cellule à la position donnée
   */
  private CellulePuissance4 getCellule(int colonne, int ligne) {
    return grille.get(ligne).get(colonne);
  }

  /**
   * Modifie la cellule à la position donnée
   *
   * @param colonne la colonne
   * @param ligne la ligne
   * @param cellule la cellule à insérer
   */
  private void setCellule(int colonne, int ligne, CellulePuissance4 cellule) {
    grille.get(ligne).set(colonne, cellule);
  }

  /**
   * Vérifie si la colonne donnée est pleine
   *
   * @param colonne La colonne à vérifier
   * @return true si la colonne est pleine, false sinon
   */
  private boolean colonneEstPleine(int colonne) {
    return getCellule(colonne, 0) != CellulePuissance4.VIDE;
  }

  private void changerJoueurCourant() {
    joueurCourant =
        joueurCourant == CellulePuissance4.ROUGE
            ? CellulePuissance4.JAUNE
            : CellulePuissance4.ROUGE;
  }

  /**
   * Vérifie si les cellules données sont égales (même couleur)
   *
   * @param c1 la première cellule
   * @param c2 la deuxième cellule
   * @param c3 la troisième cellule
   * @param c4 la quatrième cellule
   * @return true si les cellules sont égales, false sinon
   */
  private boolean cellulesEgales(
      CellulePuissance4 c1, CellulePuissance4 c2, CellulePuissance4 c3, CellulePuissance4 c4) {
    return c1 == c2 && c2 == c3 && c3 == c4;
  }

  /**
   * Utilitaire pour obtenir une cellule à partir de ses coordonnées, ou null si les coordonnées
   * sont invalides.
   *
   * <p>Cette fonction est pratique dans la vérification des victoires, préférez la méthode
   * getCellule() pour obtenir une cellule à partir de ses coordonnées en dehors de la vérification,
   * car elle sera plus rapide.
   *
   * @param colonne la colonne
   * @param ligne la ligne
   * @return la cellule à la position donnée, ou null si les coordonnées sont invalides
   */
  private CellulePuissance4 getCelluleOuNull(int colonne, int ligne) {
    if (colonne < 0 || colonne >= this.longueur || ligne < 0 || ligne >= this.hauteur) {
      return null;
    }
    return getCellule(colonne, ligne);
  }

  /**
   * Déclare la victoire d'un joueur
   *
   * @param cellule la cellule gagnante
   */
  private void declarerVictoire(CellulePuissance4 cellule) {
    etat =
        cellule == CellulePuissance4.ROUGE
            ? EtatPartiePuissance4.VICTOIRE_ROUGE
            : EtatPartiePuissance4.VICTOIRE_JAUNE;
  }

  /** Déclare la partie nulle */
  private void declarerNul() {
    etat = EtatPartiePuissance4.MATCH_NUL;
  }

  /**
   * Vérifie si une liste de cellules est valide pour une victoire
   *
   * @param coordonnees les coordonnées des cellules
   * @return true si la liste est valide, false sinon
   */
  private boolean verifierListeCellules(List<List<Integer>> coordonnees) {
    if (coordonnees.size() != 4) return false;

    CellulePuissance4 c = getCelluleOuNull(coordonnees.get(0).get(0), coordonnees.get(0).get(1));
    if (cellulesEgales(
        c,
        getCelluleOuNull(coordonnees.get(1).get(0), coordonnees.get(1).get(1)),
        getCelluleOuNull(coordonnees.get(2).get(0), coordonnees.get(2).get(1)),
        getCelluleOuNull(coordonnees.get(3).get(0), coordonnees.get(3).get(1)))) {
      declarerVictoire(c);
      return true;
    }

    return false;
  }

  /**
   * Vérifie si une condition de victoire est remplie
   *
   * @param colonne la colonne de la cellule insérée
   * @param ligne la ligne de la cellule insérée
   * @return true si une condition de victoire est remplie, false sinon
   */
  private boolean verifierVictoire(int colonne, int ligne) {
    // Lignes horizontales

    // oooo
    // ^
    if (verifierListeCellules(
        List.of(
            List.of(colonne, ligne),
            List.of(colonne, ligne + 1),
            List.of(colonne, ligne + 2),
            List.of(colonne, ligne + 3)))) return true;

    // oooo
    //  ^
    if (verifierListeCellules(
        List.of(
            List.of(colonne, ligne - 1),
            List.of(colonne, ligne),
            List.of(colonne, ligne + 1),
            List.of(colonne, ligne + 2)))) return true;

    // oooo
    //   ^
    if (verifierListeCellules(
        List.of(
            List.of(colonne, ligne - 2),
            List.of(colonne, ligne - 1),
            List.of(colonne, ligne),
            List.of(colonne, ligne + 1)))) return true;

    // oooo
    //    ^
    if (verifierListeCellules(
        List.of(
            List.of(colonne, ligne - 3),
            List.of(colonne, ligne - 2),
            List.of(colonne, ligne - 1),
            List.of(colonne, ligne)))) return true;

    // Colonnes verticales

    // o <
    // o
    // o
    // o
    if (verifierListeCellules(
        List.of(
            List.of(colonne, ligne),
            List.of(colonne + 1, ligne),
            List.of(colonne + 2, ligne),
            List.of(colonne + 3, ligne)))) return true;

    // o
    // o <
    // o
    // o
    if (verifierListeCellules(
        List.of(
            List.of(colonne - 1, ligne),
            List.of(colonne, ligne),
            List.of(colonne + 1, ligne),
            List.of(colonne + 2, ligne)))) return true;

    // o
    // o
    // o <
    // o
    if (verifierListeCellules(
        List.of(
            List.of(colonne - 2, ligne),
            List.of(colonne - 1, ligne),
            List.of(colonne, ligne),
            List.of(colonne + 1, ligne)))) return true;

    // o
    // o
    // o
    // o <
    if (verifierListeCellules(
        List.of(
            List.of(colonne - 3, ligne),
            List.of(colonne - 2, ligne),
            List.of(colonne - 1, ligne),
            List.of(colonne, ligne)))) return true;

    // Diagonales

    // o    <
    //  o
    //   o
    //    o
    if (verifierListeCellules(
        List.of(
            List.of(colonne, ligne),
            List.of(colonne + 1, ligne + 1),
            List.of(colonne + 2, ligne + 2),
            List.of(colonne + 3, ligne + 3)))) return true;

    // o
    //  o   <
    //   o
    //    o
    if (verifierListeCellules(
        List.of(
            List.of(colonne - 1, ligne - 1),
            List.of(colonne, ligne),
            List.of(colonne + 1, ligne + 1),
            List.of(colonne + 2, ligne + 2)))) return true;

    // o
    //  o
    //   o  <
    //    o
    if (verifierListeCellules(
        List.of(
            List.of(colonne - 2, ligne - 2),
            List.of(colonne - 1, ligne - 1),
            List.of(colonne, ligne),
            List.of(colonne + 1, ligne + 1)))) return true;

    // o
    //  o
    //   o
    //    o <
    if (verifierListeCellules(
        List.of(
            List.of(colonne - 3, ligne - 3),
            List.of(colonne - 2, ligne - 2),
            List.of(colonne - 1, ligne - 1),
            List.of(colonne, ligne)))) return true;

    //    o <
    //   o
    //  o
    // o
    if (verifierListeCellules(
        List.of(
            List.of(colonne, ligne),
            List.of(colonne - 1, ligne + 1),
            List.of(colonne - 2, ligne + 2),
            List.of(colonne - 3, ligne + 3)))) return true;

    //    o
    //   o  <
    //  o
    // o
    if (verifierListeCellules(
        List.of(
            List.of(colonne + 1, ligne - 1),
            List.of(colonne, ligne),
            List.of(colonne - 1, ligne + 1),
            List.of(colonne - 2, ligne + 2)))) return true;

    //    o
    //   o
    //  o   <
    // o
    if (verifierListeCellules(
        List.of(
            List.of(colonne + 2, ligne - 2),
            List.of(colonne + 1, ligne - 1),
            List.of(colonne, ligne),
            List.of(colonne - 1, ligne + 1)))) return true;

    //    o
    //   o
    //  o
    // o    <
    return verifierListeCellules(
        List.of(
            List.of(colonne + 3, ligne - 3),
            List.of(colonne + 2, ligne - 2),
            List.of(colonne + 1, ligne - 1),
            List.of(colonne, ligne)));
  }

  /**
   * Vérifie si la grille est pleine en vérifiant si chaque colonne est pleine.
   *
   * @return true si la grille est pleine, false sinon
   */
  private boolean grilleEstPleine() {
    for (int i = 0; i < this.longueur; i++) {
      if (getCellule(i, 0) == CellulePuissance4.VIDE) return false;
    }
    return true;
  }

  /**
   * Vérifie l'état de la partie à partir de la grille.
   *
   * @param colonne la colonne de la cellule insérée
   * @param ligne la ligne de la cellule insérée
   */
  private void verifierEtatPartie(int colonne, int ligne) {
    CellulePuissance4 cellule = getCellule(colonne, ligne);
    if (cellule == CellulePuissance4.VIDE) return;

    if (verifierVictoire(colonne, ligne)) declarerVictoire(cellule);
    else if (grilleEstPleine()) declarerNul();
  }

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

  public CellulePuissance4 getJoueurCourant() {
    return joueurCourant;
  }

  /**
   * Joue un coup dans la colonne donnée
   *
   * @param colonne la colonne dans laquelle jouer, entre 1 et 7 (inclus)
   * @throws IllegalArgumentException si la colonne n'existe pas où est pleine
   * @throws IllegalStateException si la partie est terminée
   */
  public void jouer(int colonne) {
    if (etat != EtatPartiePuissance4.EN_COURS)
      throw new IllegalStateException("La partie est terminée");

    colonne--; // On décrémente la colonne pour la rendre 0-indexée
    if (!verifierNumeroColonne(colonne)) {
      throw new IllegalArgumentException("La colonne doit être comprise entre 1 et 7");
    }
    if (colonneEstPleine(colonne)) throw new IllegalArgumentException("La colonne est pleine");

    List<Integer> pos = insererCellule(colonne, joueurCourant);
    if (pos.isEmpty()) return;
    int x = pos.get(0);
    int y = pos.get(1);

    verifierEtatPartie(x, y);
    changerJoueurCourant();
  }
}
