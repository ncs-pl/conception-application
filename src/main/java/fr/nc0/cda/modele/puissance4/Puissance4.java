/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

import fr.nc0.cda.modele.EtatPartie;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/** Représente une partie de Puissance 4. */
public class Puissance4 {
  /** La grille de jeu. */
  private GrillePuissance4 grille;

  /** L'état de la partie */
  private EtatPartie etat = EtatPartie.EN_COURS;

  /** Créer une partie de Puissance 4 et la commence */
  public Puissance4(int longueur, int hauteur) {
    if (longueur < 1) throw new IllegalArgumentException("La longueur doît être supérieure à 0");
    if (hauteur < 1) throw new IllegalArgumentException("La hauteur doît être supérieure à 0");

    this.grille = new GrillePuissance4(longueur, hauteur);
  }

  /**
   * Vérifie si la colonne donnée est pleine
   *
   * @param colonne La colonne à vérifier
   * @return true si la colonne est pleine, false sinon
   */
  private boolean colonnePleine(int colonne) {
    return this.grille.get(colonne, 1) != CellulePuissance4.VIDE;
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
    for (int ligne = this.grille.getHauteur(); ligne > 0; --ligne)
      if (this.grille.get(colonne, ligne) == CellulePuissance4.VIDE) {
        this.grille.set(colonne, ligne, cellule);
        return ligne;
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
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne),
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne + 1),
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne + 2),
        /* ⭕️🔴🔴🔴 */ this.grille.get(colonne, ligne + 3))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne - 1),
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne),
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne + 1),
        /* 🔴⭕️🔴🔴 */ this.grille.get(colonne, ligne + 2))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne - 2),
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne - 1),
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne),
        /* 🔴🔴⭕️🔴 */ this.grille.get(colonne, ligne + 1))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne - 3),
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne - 2),
        /* ⚪⚪⚪⚪ */ this.grille.get(colonne, ligne - 1),
        /* 🔴🔴🔴⭕️ */ this.grille.get(colonne, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⭕️ */ this.grille.get(colonne, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 1, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 2, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 3, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne - 1, ligne),
        /* ⚪⚪⚪⭕️ */ this.grille.get(colonne, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 1, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 2, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne - 2, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne - 1, ligne),
        /* ⚪⚪⚪⭕️ */ this.grille.get(colonne, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 1, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne - 3, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne - 2, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne - 1, ligne),
        /* ⚪⚪⚪⭕️ */ this.grille.get(colonne, ligne))) return true;

    if (cellulesEgales(
        /* ⭕️⚪⚪⚪ */ this.grille.get(colonne, ligne),
        /* ⚪🔴⚪⚪ */ this.grille.get(colonne + 1, ligne + 1),
        /* ⚪⚪🔴⚪ */ this.grille.get(colonne + 2, ligne + 2),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 3, ligne + 3))) return true;

    if (cellulesEgales(
        /* 🔴⚪⚪⚪ */ this.grille.get(colonne - 1, ligne - 1),
        /* ⚪⭕️⚪⚪ */ this.grille.get(colonne, ligne),
        /* ⚪⚪🔴⚪ */ this.grille.get(colonne + 1, ligne + 1),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 2, ligne + 2))) return true;

    if (cellulesEgales(
        /* 🔴⚪⚪⚪ */ this.grille.get(colonne - 2, ligne - 2),
        /* ⚪🔴⚪⚪ */ this.grille.get(colonne - 1, ligne - 1),
        /* ⚪⚪⭕️⚪ */ this.grille.get(colonne, ligne),
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 1, ligne + 1))) return true;

    if (cellulesEgales(
        /* 🔴⚪⚪⚪ */ this.grille.get(colonne - 3, ligne - 3),
        /* ⚪🔴⚪⚪ */ this.grille.get(colonne - 2, ligne - 2),
        /* ⚪⚪🔴⚪ */ this.grille.get(colonne - 1, ligne - 1),
        /* ⚪⚪⚪⭕️ */ this.grille.get(colonne, ligne))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪⭕️ */ this.grille.get(colonne, ligne),
        /* ⚪⚪🔴⚪ */ this.grille.get(colonne - 1, ligne + 1),
        /* ⚪🔴⚪⚪ */ this.grille.get(colonne - 2, ligne + 2),
        /* 🔴⚪⚪⚪ */ this.grille.get(colonne - 3, ligne + 3))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 1, ligne - 1),
        /* ⚪⚪⭕️⚪ */ this.grille.get(colonne, ligne),
        /* ⚪🔴⚪⚪ */ this.grille.get(colonne - 1, ligne + 1),
        /* 🔴⚪⚪⚪ */ this.grille.get(colonne - 2, ligne + 2))) return true;

    if (cellulesEgales(
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 2, ligne - 2),
        /* ⚪⚪🔴⚪ */ this.grille.get(colonne + 1, ligne - 1),
        /* ⚪⭕️⚪⚪ */ this.grille.get(colonne, ligne),
        /* 🔴⚪⚪⚪ */ this.grille.get(colonne - 1, ligne + 1))) return true;

    return cellulesEgales(
        /* ⚪⚪⚪🔴 */ this.grille.get(colonne + 3, ligne - 3),
        /* ⚪⚪🔴⚪ */ this.grille.get(colonne + 2, ligne - 2),
        /* ⚪🔴⚪⚪ */ this.grille.get(colonne + 1, ligne - 1),
        /* ⭕️⚪⚪⚪ */ this.grille.get(colonne, ligne));
  }

  /**
   * Vérifie si la grille est pleine en vérifiant si chaque colonne est pleine.
   *
   * @return true si la grille est pleine, false sinon
   */
  private boolean grillePleine() {
    boolean result = true;
    for (int i = 1; i <= this.grille.getLongueur(); ++i) {
      if (!colonnePleine(i)) {
        result = false;
        break;
      }
    }
    return result;
  }

  /**
   * Vérifie l'état de la partie à partir de la grille à partir de la cellule insérée.
   *
   * @param colonne la colonne de la cellule insérée, entre 1 et la longueur de la grille
   * @param ligne la ligne de la cellule insérée, entre 1 et la hauteur de la grille
   */
  private void actualiserEtatPartie(int colonne, int ligne) {
    CellulePuissance4 cellule = this.grille.get(colonne, ligne);
    if (cellule == CellulePuissance4.VIDE) return;

    if (celluleVictorieuse(colonne, ligne))
      etat =
          cellule == CellulePuissance4.ROUGE
              ? EtatPartie.VICTOIRE_JOUEUR_1
              : EtatPartie.VICTOIRE_JOUEUR_2;
    else if (grillePleine()) etat = EtatPartie.MATCH_NUL;
    else etat = EtatPartie.EN_COURS;
  }

  /** Actualise l'état de la partie en itérant sur la grille. */
  private void actualiserEtatPartie() {
    for (int i = 1; i <= this.grille.getLongueur(); i++)
      for (int j = 1; j <= this.grille.getHauteur(); j++) actualiserEtatPartie(i, j);
  }

  /**
   * Rotationne la grille de jeu de 90° dans le sens horaire ou anti-horaire, selon la rotation
   * donnée.
   *
   * <p>La rotation est effectuée en appliquant une matrice de rotation sur la grille. Aucune
   * gravité n'est appliquée. Il peut aussi y avoir des pertes de données si la grille n'est pas
   * carrée, ou au contraire des données pouvant être dupliquées.
   *
   * @param rotation la rotation à effectuer
   */
  private void rotationnerGrille(RotationPuissance4 rotation) {
    int nouvelleLongueur = this.grille.getHauteur();
    int nouvelleHauteur = this.grille.getLongueur();
    GrillePuissance4 grilleRotationnee =
        new GrillePuissance4(nouvelleLongueur, nouvelleHauteur); // On inverse les dimensions

    // Note : commencer à 1 au lieu de 0 pour suivre la formule mathématique
    // qui indexe à 1.
    for (int i = 1; i <= this.grille.getLongueur(); ++i) {
      for (int j = 1; j <= this.grille.getHauteur(); ++j) {
        // Rotation de 90° dans le sens horaire d'une matrice 3x2 vers une
        // matrice 2x3 :
        //
        //                  ┌────────────┐     Matrice M        Matrice M'
        //               ┌──┘┌──────────┐└┐    d'origine :      d'arrivée :
        //           ┌───┼───┘┌────────┐└┐│
        //       ┌───┼───┼────┘ ┌──┬──┐└┐││      * A -> 1,1       * A' -> 1,2
        //     ┌─┴─┬─┴─┬─┴─┐  ┌►│D'│A'│◄┘││      * B -> 1,2       * B' -> 2,2
        //     │ A │ B │ C │ ┌┘ ├──┼──┤ ┌┘│      * C -> 1,3       * C' -> 3,2
        //     ├───┼───┼───┤┌┘┌►│E'│B'│◄┘┌┘      * D -> 2,1       * D' -> 1,1
        //     │ D │ E │ F ││┌┘ ├──┼──┤ ┌┘       * E -> 2,2       * E' -> 2,1
        //     └─┬─┴─┬─┴─┬─┘││┌►│F'│C'│◄┘        * F -> 2,3       * F' -> 3,1
        //       └┐  └┐  └──┼┼┘ └──┴──┘
        //        └┐  └─────┼┘                 ∀ɣ∈M, ∀ɣ'∈M, ɣ'₁ = ɣ₂,
        //         └────────┘                               ɣ'₂ = n - ɣ₁
        if (Objects.requireNonNull(rotation) == RotationPuissance4.HORAIRE)
          grilleRotationnee.set(j, this.grille.getHauteur() - i, grille.get(i, j));

        // Rotation de 90° dans le sens horaire inverse d'une matrice 3x2 vers
        // une matrice 2x3 :
        //
        //                  ┌────────────┐     Matrice M        Matrice M'
        //               ┌──┘┌──────────┐└┐    d'origine :      d'arrivée :
        //           ┌───┼───┘┌────────┐└┐│
        //       ┌───┼───┼────┘ ┌──┬──┐└┐││      * A -> 1,1       * A' -> 3,1
        //     ┌─┴─┬─┴─┬─┴─┐  ┌►│C'│F'│◄┘││      * B -> 1,2       * B' -> 2,1
        //     │ A │ B │ C │ ┌┘ ├──┼──┤ ┌┘│      * C -> 1,3       * C' -> 1,1
        //     ├───┼───┼───┤┌┘┌►│B'│E'│◄┘┌┘      * D -> 2,1       * D' -> 3,2
        //     │ D │ E │ F ││┌┘ ├──┼──┤ ┌┘       * E -> 2,2       * E' -> 2,2
        //     └─┬─┴─┬─┴─┬─┘││┌►│A'│D'│◄┘        * F -> 2,3       * F' -> 1,2
        //       └┐  └┐  └──┼┼┘ └──┴──┘
        //        └┐  └─────┼┘                 ∀ɣ∈M, ∀ɣ'∈M, ɣ'₁ = n - ɣ₂,
        //         └────────┘                               ɣ'₂ = ɣ₁
        //
        else if (rotation == RotationPuissance4.ANTI_HORAIRE)
          grilleRotationnee.set(this.grille.getLongueur() - j, i, grille.get(i, j));
      }
    }

    grille = grilleRotationnee;
  }

  /** Applique la gravité sur la grille de jeu, en déplaçant les cellules vides vers le bas. */
  private void appliquerGravite() {
    // Pour simuler la gravité, il nous suffit pour chaque colonne, d'insérer
    // les cellules non vides dans une file, puis d'y ajouter
    // hauteur - len(file) cases vides.  On défile la file dans l'ordre pour
    // insérer dans la colonne.

    // Faire une copie de la grille actuelle nous permet de réinitialiser la
    // grille existante et de profiter des fonctions pré-définies pour insérer
    // un jeton.
    GrillePuissance4 copieGrille = grille;
    grille = new GrillePuissance4(this.grille.getLongueur(), this.grille.getHauteur());

    List<CellulePuissance4> file = new ArrayList<>();
    for (int colonne = 1; colonne <= this.grille.getHauteur(); ++colonne) {
      int tailleFile = 0; // Évite d'appeler file.size() qui requière une boucle

      // On enfile uniquement les jetons colorés dans une file.
      for (int ligne = 1; ligne <= this.grille.getLongueur(); ++ligne) {
        // WARN: inversé?
        CellulePuissance4 cellule = copieGrille.get(colonne, ligne);

        if (cellule != null && cellule != CellulePuissance4.VIDE) {
          file.add(cellule);
          ++tailleFile;
        }
      }

      // On comble l'espace restant de vide
      if (tailleFile < this.grille.getHauteur())
        for (int k = 0; k < tailleFile; ++k) file.add(CellulePuissance4.VIDE);

      // Reste qu'à insérer nos jetons dans la colonne.
      for (CellulePuissance4 cellule : file) insererCellule(colonne, cellule);
      file.clear(); // Permet de réutiliser la file pour la prochaine colonne
    }
  }

  // ===========================================================================
  // Public API
  // ===========================================================================

  /**
   * Retourne la grille de jeu
   *
   * @return La grille de jeu
   */
  public GrillePuissance4 getGrille() {
    return grille;
  }

  /**
   * Retourne l'état de la partie
   *
   * @return L'état de la partie
   */
  public EtatPartie getEtat() {
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
  public boolean colonneInvalide(int colonne) {
    return colonne <= 0 || colonne > this.grille.getLongueur();
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
    if (etat != EtatPartie.EN_COURS) throw new IllegalStateException("La partie est terminée");

    if (colonneInvalide(colonne))
      throw new IllegalArgumentException("La colonne doit être comprise entre 1 et 7");

    if (colonnePleine(colonne)) throw new IllegalArgumentException("La colonne est pleine");

    int ligne = insererCellule(colonne, jeton);
    actualiserEtatPartie(colonne, ligne);
  }

  /**
   * Rotationne la grille de jeu de 90° dans le sens horaire ou anti-horaire.
   *
   * @param rotation la rotation à effectuer
   * @throws IllegalStateException si la partie est terminée
   */
  public void rotationner(RotationPuissance4 rotation) {
    if (etat != EtatPartie.EN_COURS) throw new IllegalStateException("La partie est terminée");

    rotationnerGrille(rotation);
    appliquerGravite();
    actualiserEtatPartie();
  }
}
