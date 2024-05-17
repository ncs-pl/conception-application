/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

import fr.nc0.cda.modele.jeu.EtatPartie;
import fr.nc0.cda.modele.jeu.Plateau;
import java.util.ArrayList;
import java.util.List;

/** Représente la grille de jeu du Puissance 4. */
public class PlateauPuissance4 implements Plateau {
  /** Les colonnes de la grille */
  private final List<List<CellulePuissance4>> grille;

  /** La longueur de la grille */
  private int longueur;

  /** La hauteur de la grille */
  private int hauteur;

  public PlateauPuissance4(int longueur, int hauteur) {
    this.longueur = longueur;
    this.hauteur = hauteur;
    this.grille = initialiserGrille(hauteur, longueur);
  }

  /**
   * Initialise une grille 2D de cellules vides
   *
   * @param longueur longueur de la grille
   * @param largeur largeur de la grille
   * @return la grille
   */
  private List<List<CellulePuissance4>> initialiserGrille(int longueur, int largeur) {
    List<List<CellulePuissance4>> grille = new ArrayList<>(longueur);

    for (int i = 0; i < longueur; ++i) {
      List<CellulePuissance4> colonne = new ArrayList<>(largeur);
      for (int j = 0; j < largeur; ++j) {
        colonne.add(CellulePuissance4.VIDE);
      }

      grille.add(colonne);
    }

    return grille;
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

  /**
   * Effectue une rotation de 90 degrés dans le sens voulu
   *
   * @param sens le sens de rotation de 90
   * @return le plateau avec une grille modifiée.
   */
  public PlateauPuissance4 rotationner(RotationPuissance4 sens) {
    int nouvelleLongueur = hauteur;
    int nouvelleHauteur = longueur;
    PlateauPuissance4 nouveauPlateau = new PlateauPuissance4(nouvelleLongueur, nouvelleHauteur);

    for (int ligne = 1; ligne <= hauteur; ++ligne) {
      if (sens == RotationPuissance4.HORAIRE) {
        for (int colonne = longueur; colonne > 0; --colonne) {
          CellulePuissance4 cellule = getCellule(colonne, ligne);
          if (cellule == CellulePuissance4.VIDE) {
            continue;
          }

          nouveauPlateau.insererCellule(hauteur + 1 - ligne, cellule);
        }
      } else {
        for (int colonne = 1; colonne <= longueur; ++colonne) {
          CellulePuissance4 cellule = getCellule(colonne, ligne);
          if (cellule == CellulePuissance4.VIDE) {
            continue;
          }

          nouveauPlateau.insererCellule(ligne, cellule);
        }
      }
    }

    return nouveauPlateau;
  }

  /**
   * Vérifie que la cellule passée donne la victoire.
   *
   * @param colonne la colonne de la cellule
   * @param ligne la ligne de la cellule
   * @return true si victoire
   */
  public boolean verifierCelluleVictorieuse(int colonne, int ligne) {
    if (colonne < 1 || colonne > longueur || ligne < 1 || ligne > longueur) {
      return false;
    }

    CellulePuissance4 cellule = getCellule(colonne, ligne);
    if (cellule == CellulePuissance4.VIDE) {
      return false;
    }

    // Victoire

    // Voisins à vérifier :
    //
    //       +-------------+       X = cellule courante
    //     A |o     o     o|       o = cellule voisine
    //     B |  o   o   o  |
    //     C |    o o o    |
    //     D |o o o X o o o|
    //     E |    o o o    |
    //     F |  o   o   o  |
    //     G |o     o     o|
    //       +-------------+
    //        a b c d e f g

    CellulePuissance4 aD = getCellule(colonne - 3, ligne);
    CellulePuissance4 bD = getCellule(colonne - 2, ligne);
    CellulePuissance4 cD = getCellule(colonne - 1, ligne);
    CellulePuissance4 eD = getCellule(colonne + 1, ligne);
    CellulePuissance4 fD = getCellule(colonne + 2, ligne);
    CellulePuissance4 gD = getCellule(colonne + 3, ligne);
    if (cellule == eD && cellule == fD && cellule == gD) return true;
    if (cellule == cD && cellule == eD && cellule == fD) return true;
    if (cellule == bD && cellule == cD && cellule == eD) return true;
    if (cellule == aD && cellule == bD && cellule == cD) return true;

    CellulePuissance4 dA = getCellule(colonne, ligne - 3);
    CellulePuissance4 dB = getCellule(colonne, ligne - 2);
    CellulePuissance4 dC = getCellule(colonne, ligne - 1);
    CellulePuissance4 dE = getCellule(colonne, ligne + 1);
    CellulePuissance4 dF = getCellule(colonne, ligne + 2);
    CellulePuissance4 dG = getCellule(colonne, ligne + 3);
    if (cellule == dE && cellule == dF && cellule == dG) return true;
    if (cellule == dC && cellule == dE && cellule == dF) return true;
    if (cellule == dB && cellule == dC && cellule == dE) return true;
    if (cellule == dA && cellule == dB && cellule == dC) return true;

    CellulePuissance4 aA = getCellule(colonne - 3, ligne - 3);
    CellulePuissance4 bB = getCellule(colonne - 2, ligne - 2);
    CellulePuissance4 cC = getCellule(colonne - 1, ligne - 1);
    CellulePuissance4 eE = getCellule(colonne + 1, ligne + 1);
    CellulePuissance4 fF = getCellule(colonne + 2, ligne + 2);
    CellulePuissance4 gG = getCellule(colonne + 3, ligne + 3);
    if (cellule == eE && cellule == fF && cellule == gG) return true;
    if (cellule == cC && cellule == eE && cellule == fF) return true;
    if (cellule == bB && cellule == cC && cellule == eE) return true;
    if (cellule == aA && cellule == bB && cellule == cC) return true;

    CellulePuissance4 aG = getCellule(colonne - 3, ligne + 3);
    CellulePuissance4 bF = getCellule(colonne - 2, ligne + 2);
    CellulePuissance4 cE = getCellule(colonne - 1, ligne + 1);
    CellulePuissance4 eC = getCellule(colonne + 1, ligne - 1);
    CellulePuissance4 fB = getCellule(colonne + 2, ligne - 2);
    CellulePuissance4 gA = getCellule(colonne + 3, ligne - 3);
    if (cellule == cE && cellule == bF && cellule == aG) return true;
    if (cellule == eC && cellule == cE && cellule == bF) return true;
    if (cellule == fB && cellule == eC && cellule == cE) return true;
    return cellule == gA && cellule == fB && cellule == eC;
  }

  /**
   * Vérifie si le plateau actuel contient une situation victorieuse.
   *
   * @return la situation de victoire
   */
  public EtatPartie verifierVictoire() {
    int victoiresRouges = 0;
    int victoiresJaunes = 0;

    iterations:
    for (int colonne = 1; colonne <= longueur; ++colonne) {
      for (int ligne = 1; ligne <= hauteur; ++ligne) {
        CellulePuissance4 cellule = getCellule(colonne, ligne);
        if (cellule == CellulePuissance4.VIDE
            || (cellule == CellulePuissance4.ROUGE && victoiresRouges == 1)
            || (cellule == CellulePuissance4.JAUNE && victoiresJaunes == 1)) {
          continue;
        }

        boolean victoire = verifierCelluleVictorieuse(colonne, ligne);
        if (victoire && cellule == CellulePuissance4.ROUGE) {
          victoiresRouges = 1;
        } else if (victoire && cellule == CellulePuissance4.JAUNE) {
          victoiresJaunes = 1;
        }

        // Early exit
        if (victoiresRouges == 1 && victoiresJaunes == 1) {
          break iterations;
        }
      }
    }

    if (victoiresRouges == 1 && victoiresJaunes == 1) {
      return EtatPartie.MATCH_NUL;
    } else if (victoiresRouges > victoiresJaunes) {
      return EtatPartie.VICTOIRE_JOUEUR_1;
    } else if (victoiresJaunes > victoiresRouges) {
      return EtatPartie.VICTOIRE_JOUEUR_2;
    } else {
      return EtatPartie.EN_COURS;
    }
  }

  /**
   * Créer une copie du plateau et de sa grille.
   *
   * @return la copie
   */
  public PlateauPuissance4 dupliquer() {
    PlateauPuissance4 copie = new PlateauPuissance4(longueur, hauteur);
    for (int ligne = 1; ligne <= hauteur; ++ligne) {
      for (int colonne = 1; colonne <= longueur; ++colonne) {
        CellulePuissance4 cellule = getCellule(colonne, ligne);
        if (cellule == CellulePuissance4.VIDE) {
          continue;
        }

        copie.setCellule(colonne, ligne, cellule);
      }
    }

    return copie;
  }
}
