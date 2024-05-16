/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.jeu.Choix;
import fr.nc0.cda.modele.jeu.Plateau;
import fr.nc0.cda.modele.puissance4.*;
import fr.nc0.cda.vue.Ihm;
import java.util.ArrayList;
import java.util.List;

public class StrategieAiPuissance4 implements Strategie {
  @Override
  public Choix jouer(Ihm ihm, Plateau plateau, Joueur joueur) {
    PlateauPuissance4 p4 = (PlateauPuissance4) plateau;

    if (actualiserEtatPartie(
        plateauRotation(RotationPuissance4.HORAIRE, p4), CellulePuissance4.JAUNE)) {
      System.out.println("Horaire");
      return new ChoixPuissance4(CoupPuissance4.ROTATION, RotationPuissance4.HORAIRE, 0);
    } else if (actualiserEtatPartie(
        plateauRotation(RotationPuissance4.ANTI_HORAIRE, p4), CellulePuissance4.JAUNE)) {
      System.out.println("Anti-Horaire");
      return new ChoixPuissance4(CoupPuissance4.ROTATION, RotationPuissance4.ANTI_HORAIRE, 0);
    }

    List<List<Integer>> listCoup = new ArrayList<>();
    for (int i = 0; i < 7; ++i) {
      listCoup.add(new ArrayList<>());
    }

    for (int colonne = 1; colonne <= p4.getLongueur(); ++colonne) {
      if (!p4.verifierColonnePleine(colonne)) {
        PlateauPuissance4 p4Jaune = makeCopiePlateau((PlateauPuissance4) plateau);
        PlateauPuissance4 p4Rouge = makeCopiePlateau((PlateauPuissance4) plateau);
        p4Jaune.insererCellule(colonne, CellulePuissance4.JAUNE);
        p4Rouge.insererCellule(colonne, CellulePuissance4.ROUGE);
        for (int ligne = 1; ligne <= p4.getHauteur(); ++ligne) {
          if (p4Jaune.getCellule(colonne, ligne) != CellulePuissance4.VIDE) {
            int maxJaune = comptageLigne(p4Jaune, CellulePuissance4.JAUNE, colonne, ligne);
            int maxRouge = comptageLigne(p4Rouge, CellulePuissance4.ROUGE, colonne, ligne);

            if (maxJaune >= 4) {
              listCoup.get(6).add(colonne);
            } else if (maxJaune == 3) {
              listCoup.get(4).add(colonne);
            } else if (maxJaune == 2) {
              listCoup.get(2).add(colonne);
            } else {
              listCoup.get(0).add(colonne);
            }

            if (maxRouge >= 4) {
              listCoup.get(5).add(colonne);
            } else if (maxRouge == 3) {
              listCoup.get(3).add(colonne);
            } else if (maxRouge == 2) {
              listCoup.get(1).add(colonne);
            }
            break;
          }
        }
      }
    }

    for (int i = listCoup.size() - 1; i >= 0; --i) {
      for (int j = listCoup.get(i).size() - 1; j >= 0; --j) {
        if (!actualiserEtatPartie(
                plateauRotation(RotationPuissance4.HORAIRE, p4), CellulePuissance4.ROUGE)
            || !actualiserEtatPartie(
                plateauRotation(RotationPuissance4.ANTI_HORAIRE, p4), CellulePuissance4.ROUGE)) {
          System.out.println("Insertion");
          return new ChoixPuissance4(CoupPuissance4.INSERTION, null, listCoup.get(i).get(j));
        }
      }
    }

    System.out.println("Erreur");
    return new ChoixPuissance4(CoupPuissance4.INSERTION, null, 1);
  }

  private PlateauPuissance4 plateauRotation(
      RotationPuissance4 rotation, PlateauPuissance4 plateau) {
    int longueur = plateau.getLongueur();
    int hauteur = plateau.getHauteur();
    PlateauPuissance4 nouveauPlateau = new PlateauPuissance4(hauteur, longueur);

    for (int ligne = 1; ligne <= hauteur; ligne++) {
      if (rotation == RotationPuissance4.HORAIRE) {
        for (int colonne = longueur; colonne > 0; --colonne) {
          CellulePuissance4 cellule = plateau.getCellule(colonne, ligne);
          if (cellule == CellulePuissance4.VIDE) {
            continue;
          }

          nouveauPlateau.insererCellule(hauteur + 1 - ligne, cellule);
        }
      } else {
        for (int colonne = 1; colonne <= longueur; ++colonne) {
          CellulePuissance4 cellule = plateau.getCellule(colonne, ligne);
          if (cellule == CellulePuissance4.VIDE) {
            continue;
          }

          nouveauPlateau.insererCellule(ligne, cellule);
        }
      }
    }
    return nouveauPlateau;
  }

  private boolean etatPartieViaCellule(PlateauPuissance4 plateau, int colonne, int ligne) {
    CellulePuissance4 cellule = plateau.getCellule(colonne, ligne);
    if (cellule == CellulePuissance4.VIDE) return false;

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

    CellulePuissance4 aD = plateau.getCellule(colonne - 3, ligne);
    CellulePuissance4 bD = plateau.getCellule(colonne - 2, ligne);
    CellulePuissance4 cD = plateau.getCellule(colonne - 1, ligne);
    CellulePuissance4 eD = plateau.getCellule(colonne + 1, ligne);
    CellulePuissance4 fD = plateau.getCellule(colonne + 2, ligne);
    CellulePuissance4 gD = plateau.getCellule(colonne + 3, ligne);
    if (verifierVictoireLigne(cellule, aD, bD, cD, eD, fD, gD)) {
      return true;
    }

    CellulePuissance4 dA = plateau.getCellule(colonne, ligne - 3);
    CellulePuissance4 dB = plateau.getCellule(colonne, ligne - 2);
    CellulePuissance4 dC = plateau.getCellule(colonne, ligne - 1);
    CellulePuissance4 dE = plateau.getCellule(colonne, ligne + 1);
    CellulePuissance4 dF = plateau.getCellule(colonne, ligne + 2);
    CellulePuissance4 dG = plateau.getCellule(colonne, ligne + 3);
    if (verifierVictoireLigne(cellule, dA, dB, dC, dE, dF, dG)) {
      return true;
    }

    CellulePuissance4 aA = plateau.getCellule(colonne - 3, ligne - 3);
    CellulePuissance4 bB = plateau.getCellule(colonne - 2, ligne - 2);
    CellulePuissance4 cC = plateau.getCellule(colonne - 1, ligne - 1);
    CellulePuissance4 eE = plateau.getCellule(colonne + 1, ligne + 1);
    CellulePuissance4 fF = plateau.getCellule(colonne + 2, ligne + 2);
    CellulePuissance4 gG = plateau.getCellule(colonne + 3, ligne + 3);
    if (verifierVictoireLigne(cellule, aA, bB, cC, eE, fF, gG)) {
      return true;
    }

    CellulePuissance4 aG = plateau.getCellule(colonne - 3, ligne + 3);
    CellulePuissance4 bF = plateau.getCellule(colonne - 2, ligne + 2);
    CellulePuissance4 cE = plateau.getCellule(colonne - 1, ligne + 1);
    CellulePuissance4 eC = plateau.getCellule(colonne + 1, ligne - 1);
    CellulePuissance4 fB = plateau.getCellule(colonne + 2, ligne - 2);
    CellulePuissance4 gA = plateau.getCellule(colonne + 3, ligne - 3);
    if (verifierVictoireLigne(cellule, gA, fB, eC, cE, bF, aG)) {
      return true;
    }
    return false;
  }

  /**
   * Vérifie que les voisins en ligne ou diagonale de la cellule sont égaux.
   *
   * @param cellule la cellule courante
   * @param a voisin
   * @param b voisin
   * @param c voisin
   * @param e voisin
   * @param f voisin
   * @param g voisin
   */
  private boolean verifierVictoireLigne(
      CellulePuissance4 cellule,
      CellulePuissance4 a,
      CellulePuissance4 b,
      CellulePuissance4 c,
      CellulePuissance4 e,
      CellulePuissance4 f,
      CellulePuissance4 g) {
    if ((cellule == e && cellule == f && cellule == g)
        || (cellule == c && cellule == e && cellule == f)
        || (cellule == b && cellule == c && cellule == e)
        || (cellule == a && cellule == b && cellule == c)) {
      return true;
    }
    return false;
  }

  private boolean actualiserEtatPartie(PlateauPuissance4 plateau, CellulePuissance4 joueurCellule) {

    for (int colonne = 1; colonne <= plateau.getLongueur(); ++colonne) {
      for (int ligne = 1; ligne <= plateau.getHauteur(); ++ligne) {
        CellulePuissance4 cellule = plateau.getCellule(colonne, ligne);
        if (cellule == CellulePuissance4.VIDE) {
          continue;
        }

        if (cellule == joueurCellule) {
          if (etatPartieViaCellule(plateau, colonne, ligne)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  private int comptageLigne(
      PlateauPuissance4 plateau, CellulePuissance4 joueurCellule, int colonne, int ligne) {

    int[] liste = new int[4];

    // horizontal
    liste[0] =
        1
            + comptageCellule(plateau, joueurCellule, colonne + 1, ligne, new int[] {1, 0})
            + comptageCellule(plateau, joueurCellule, colonne - 1, ligne, new int[] {-1, 0});

    // vertical
    liste[1] =
        1
            + comptageCellule(plateau, joueurCellule, colonne, ligne + 1, new int[] {0, 1})
            + comptageCellule(plateau, joueurCellule, colonne, ligne - 1, new int[] {0, -1});

    // diag1
    liste[2] =
        1
            + comptageCellule(plateau, joueurCellule, colonne + 1, ligne + 1, new int[] {1, 1})
            + comptageCellule(plateau, joueurCellule, colonne - 1, ligne - 1, new int[] {-1, -1});

    // diag2
    liste[3] =
        1
            + comptageCellule(plateau, joueurCellule, colonne + 1, ligne - 1, new int[] {1, -1})
            + comptageCellule(plateau, joueurCellule, colonne - 1, ligne + 1, new int[] {-1, 1});

    int max = Integer.MIN_VALUE;

    for (int i : liste) {
      if (i > max) {
        max = i;
      }
    }
    System.out.println(
        "Couleur : "
            + joueurCellule.toString()
            + "Max : "
            + max
            + " | Colonne : "
            + colonne
            + " | Ligne : "
            + ligne);
    return max;
  }

  private int comptageCellule(
      PlateauPuissance4 plateau,
      CellulePuissance4 joueurCellule,
      int colonne,
      int ligne,
      int[] operation) {
    if (plateau.getCellule(colonne, ligne) == joueurCellule
        && colonne >= 1
        && colonne <= plateau.getLongueur()
        && ligne >= 1
        && ligne <= plateau.getHauteur()) {
      return 1
          + comptageCellule(
              plateau, joueurCellule, colonne + operation[0], ligne + operation[1], operation);
    } else {
      return 0;
    }
  }

  private PlateauPuissance4 makeCopiePlateau(PlateauPuissance4 plateau) {
    PlateauPuissance4 nouveauPlateau =
        new PlateauPuissance4(plateau.getLongueur(), plateau.getHauteur());

    for (int ligne = plateau.getHauteur(); ligne > 0; --ligne) {
      for (int colonne = 1; colonne <= plateau.getLongueur(); ++colonne) {
        if (plateau.getCellule(colonne, ligne) != CellulePuissance4.VIDE) {
          nouveauPlateau.insererCellule(colonne, plateau.getCellule(colonne, ligne));
        }
      }
    }

    return nouveauPlateau;
  }
}
