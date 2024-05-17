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
import java.util.Random;

/** Stratégie similaire à simple avec une optimisation de calcul */
public class StrategiePuissance4AiOptimisee implements Strategie {
  private final Random rand = new Random();

  @Override
  public Choix jouer(Ihm ihm, Plateau plateau, Joueur joueur) {
    // Rappel, l'AI est la cellule jaune/le joueur 2.
    PlateauPuissance4 p4 = (PlateauPuissance4) plateau;

    // File de priorité selon les 7 cas décrits dans le sujet.
    List<List<Integer>> priorites = new ArrayList<>();
    for (int i = 0; i < 7; ++i) {
      priorites.add(new ArrayList<>());
    }

    int p4Longueur = p4.getLongueur();
    int p4Hauteur = p4.getHauteur();

    // Enregistrement des coups valides sur la position.
    for (int colonne = 1; colonne <= p4Longueur; ++colonne) {
      if (p4.verifierColonnePleine(colonne)) {
        continue;
      }

      PlateauPuissance4 p4Rouge = p4.dupliquer();
      p4Rouge.insererCellule(colonne, CellulePuissance4.ROUGE);

      PlateauPuissance4 p4Jaune = p4.dupliquer();
      p4Jaune.insererCellule(colonne, CellulePuissance4.JAUNE);

      for (int ligne = 1; ligne <= p4Hauteur; ++ligne) {
        if (p4Jaune.getCellule(colonne, ligne) == CellulePuissance4.VIDE) {
          continue;
        }

        int[] clusterJaune = calculerClusterMax(p4Jaune, CellulePuissance4.JAUNE, colonne, ligne);
        int jaune = clusterJaune[0];
        int direction = clusterJaune[1];

        if (jaune >= 4) {
          priorites.get(6).add(colonne);
        } else if (jaune == 3) {
          PlateauPuissance4 test = p4.dupliquer();

          if (verifierPossibiliteAlignement(test, colonne, ligne, direction)) {
            priorites.get(4).add(colonne);
          }
        } else if (jaune == 2) {
          PlateauPuissance4 test = p4.dupliquer();

          if (verifierPossibiliteAlignement(test, colonne, ligne, direction)) {
            priorites.get(2).add(colonne);
          }
        } else {
          priorites.get(0).add(colonne);
        }

        int[] clusterRouge = calculerClusterMax(p4Rouge, CellulePuissance4.ROUGE, colonne, ligne);
        int rouge = clusterRouge[0];

        if (rouge >= 4) {
          priorites.get(5).add(colonne);
        } else if (rouge == 3) {
          priorites.get(3).add(colonne);
        } else if (rouge == 2) {
          priorites.get(1).add(colonne);
        }

        break;
      }
    }

    // On doit choisir une colonne au hasard parmi la première liste de
    // priorité non vide.
    for (int i = priorites.size() - 1; i >= 0; --i) {
      if (priorites.get(i).isEmpty()) {
        continue;
      }

      int random = rand.nextInt(priorites.get(i).size());
      int colonneRandom = priorites.get(i).get(random);
      return new ChoixPuissance4(CoupPuissance4.INSERTION, null, colonneRandom);
    }

    // Par défaut, on joue dans la première colonne.
    return new ChoixPuissance4(CoupPuissance4.INSERTION, null, 1);
  }

  /**
   * Calcule la valeur du plus grand cluster de la cellule courante.
   *
   * @param plateau le plateau
   * @param couleur la couleur recherchée
   * @param colonne la colonne de la cellule courante
   * @param ligne la ligne de la cellule courante
   * @return [clusterMax, direction], avec direction étant 1 pour horizontale, 2 pour verticale, 3
   *     pour la diag1, et 4 pour la diag2. Si direction est négatif, alors il faut aller vers le
   *     bas.
   */
  @SuppressWarnings("UnnecessaryLocalVariable")
  private int[] calculerClusterMax(
      PlateauPuissance4 plateau, CellulePuissance4 couleur, int colonne, int ligne) {
    int max = 0;
    int direction = 0;

    int horizontalSuperieur = calculerCluster(plateau, couleur, colonne + 1, ligne, 1, 0);
    int horizontalInferieur = calculerCluster(plateau, couleur, colonne - 1, ligne, -1, 0);
    int horizontal = 1 + horizontalSuperieur + horizontalInferieur;
    max = horizontal;
    direction = horizontalSuperieur > horizontalInferieur ? 1 : -1;

    int verticalSuperieur = calculerCluster(plateau, couleur, colonne, ligne + 1, 0, 1);
    int verticalInferieur = calculerCluster(plateau, couleur, colonne, ligne - 1, 0, -1);
    int vertical = 1 + verticalSuperieur + verticalInferieur;

    if (vertical > max) {
      max = vertical;
      direction = verticalSuperieur > verticalInferieur ? 2 : -2;
    }

    int diagonale1Superieur = calculerCluster(plateau, couleur, colonne + 1, ligne + 1, 1, 1);
    int diagonale1Inferieur = calculerCluster(plateau, couleur, colonne - 1, ligne - 1, -1, -1);
    int diagonale1 = 1 + diagonale1Superieur + diagonale1Inferieur;

    if (diagonale1 > max) {
      max = diagonale1;
      direction = diagonale1Superieur > diagonale1Inferieur ? 3 : -3;
    }

    int diagonale2Superieur = calculerCluster(plateau, couleur, colonne + 1, ligne - 1, 1, -1);
    int diagonale2Inferieur = calculerCluster(plateau, couleur, colonne - 1, ligne + 1, -1, 1);
    int diagonale2 = 1 + diagonale2Superieur + diagonale2Inferieur;

    if (diagonale2 > max) {
      max = diagonale2;
      direction = diagonale2Superieur > diagonale2Inferieur ? 4 : -4;
    }

    int[] output = new int[2];
    output[0] = max;
    output[1] = direction;
    return output;
  }

  /**
   * Détermine la valeur d'une moitié de cluster en partant d'une cellule et navigant récursivement
   * ses voisins.
   *
   * @param plateau la plateau sur lequel naviguer
   * @param couleur la couleur cherchée
   * @param colonne la colonne de la cellule en calcul
   * @param ligne la ligne de la cellule en calcul
   * @param decalageColonne la différence entre la colonne du voisin et notre colonne
   * @param decalageLigne la différence entre la colonne du voisin et notre ligne
   * @return la valeur du cluster récursivement.
   */
  private int calculerCluster(
      PlateauPuissance4 plateau,
      CellulePuissance4 couleur,
      int colonne,
      int ligne,
      int decalageColonne,
      int decalageLigne) {
    if (colonne < 1
        || colonne > plateau.getLongueur()
        || ligne < 1
        || ligne > plateau.getHauteur()) {
      return 0;
    }

    CellulePuissance4 cellule = plateau.getCellule(colonne, ligne);
    if (cellule != couleur) {
      return 0;
    }

    return 1
        + calculerCluster(
            plateau,
            couleur,
            colonne + decalageColonne,
            ligne + decalageLigne,
            decalageColonne,
            decalageLigne);
  }

  /**
   * Vérifie qu'un alignement est possible en inférieur ou supérieur de la direction donnée.
   *
   * @param plateau le plateau
   * @param colonne la colonne de la cellule courante
   * @param ligne la ligne de la cellule courante
   * @param direction la direction, comme le retour de calculerClusterMax
   * @return true si alignement possible
   */
  private boolean verifierPossibiliteAlignement(
      PlateauPuissance4 plateau, int colonne, int ligne, int direction) {
    CellulePuissance4 cellule = plateau.getCellule(colonne, ligne);

    return switch (direction) {
      case -1 -> /* horizontal inférieur */ {
        CellulePuissance4 quatrieme = plateau.getCellule(colonne - 3, ligne);
        yield quatrieme == cellule || quatrieme == CellulePuissance4.VIDE;
      }
      case 1 -> /* horizontal supérieur */ {
        CellulePuissance4 quatrieme = plateau.getCellule(colonne + 3, ligne);
        yield quatrieme == cellule || quatrieme == CellulePuissance4.VIDE;
      }
      case -2 -> /* vertical inférieur */ {
        CellulePuissance4 quatrieme = plateau.getCellule(colonne, ligne - 3);
        yield quatrieme == cellule || quatrieme == CellulePuissance4.VIDE;
      }
      case 2 -> /* vertical supérieur */ {
        CellulePuissance4 quatrieme = plateau.getCellule(colonne, ligne + 3);
        yield quatrieme == cellule || quatrieme == CellulePuissance4.VIDE;
      }
      case -3 -> /* diagonale 1 inférieure */ {
        CellulePuissance4 quatrieme = plateau.getCellule(colonne - 3, ligne - 3);
        yield quatrieme == cellule || quatrieme == CellulePuissance4.VIDE;
      }
      case 3 -> /* diagonale 1 supérieure */ {
        CellulePuissance4 quatrieme = plateau.getCellule(colonne + 3, ligne + 3);
        yield quatrieme == cellule || quatrieme == CellulePuissance4.VIDE;
      }
      case -4 -> /* diagonale 2 inférieure */ {
        CellulePuissance4 quatrieme = plateau.getCellule(colonne + 3, ligne - 3);
        yield quatrieme == cellule || quatrieme == CellulePuissance4.VIDE;
      }
      case 4 -> /* diagonale 2 supérieure */ {
        CellulePuissance4 quatrieme = plateau.getCellule(colonne - 3, ligne + 3);
        yield quatrieme == cellule || quatrieme == CellulePuissance4.VIDE;
      }
      default -> /* unreachable */ false;
    };
  }
}
