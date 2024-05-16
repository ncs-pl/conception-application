/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.joueur;

import fr.nc0.cda.modele.jeu.Choix;
import fr.nc0.cda.modele.jeu.EtatPartie;
import fr.nc0.cda.modele.jeu.Plateau;
import fr.nc0.cda.modele.puissance4.*;
import fr.nc0.cda.vue.Ihm;
import java.util.ArrayList;
import java.util.List;

public class StrategieAiSimplePuissance4 implements Strategie {
  @Override
  public Choix jouer(Ihm ihm, Plateau plateau, Joueur joueur) {
    // Rappel, l'AI est la cellule jaune/le joueur 2.

    PlateauPuissance4 p4 = (PlateauPuissance4) plateau;

    // Regarde si une rotation amène à notre victoire.
    PlateauPuissance4 p4RotationHoraire = p4.dupliquer();
    p4RotationHoraire.rotationner(RotationPuissance4.HORAIRE);
    if (p4RotationHoraire.verifierVictoire() == EtatPartie.VICTOIRE_JOUEUR_2) {
      return new ChoixPuissance4(CoupPuissance4.ROTATION, RotationPuissance4.HORAIRE, 0);
    }

    PlateauPuissance4 p4RotationAntiHoraire = p4.dupliquer();
    p4RotationAntiHoraire.rotationner(RotationPuissance4.ANTI_HORAIRE);
    if (p4RotationAntiHoraire.verifierVictoire() == EtatPartie.VICTOIRE_JOUEUR_2) {
      return new ChoixPuissance4(CoupPuissance4.ROTATION, RotationPuissance4.ANTI_HORAIRE, 0);
    }

    // Recherche des "clusters" parmi la grille et détermine une valeur à
    // ces clusters. Un cluster est une ligne ou diagonale de cellules
    // ayant la même couleur.

    // File de priorité selon les 7 cas décris dans le sujet.
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

        int jaune = calculerClusterMax(p4Jaune, CellulePuissance4.JAUNE, colonne, ligne);
        if (jaune >= 4) {
          priorites.get(6).add(colonne);
        } else if (jaune == 3) {
          priorites.get(4).add(colonne);
        } else if (jaune == 2) {
          priorites.get(2).add(colonne);
        } else {
          priorites.get(0).add(colonne);
        }

        int rouge = calculerClusterMax(p4Rouge, CellulePuissance4.ROUGE, colonne, ligne);
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

    // Pour chaque action possible parmi nos priorités, on la teste puis
    // on vérifie qu'elle ne permet pas à l'adversaire de gagner en faisant
    // une rotation.
    for (int i = priorites.size() - 1; i >= 0; --i) {
      for (int j = priorites.get(i).size() - 1; j >= 0; --j) {
        int colonneTest = priorites.get(i).get(j);
        PlateauPuissance4 test = p4.dupliquer();
        test.insererCellule(colonneTest, CellulePuissance4.JAUNE);

        PlateauPuissance4 testRotationHoraire = p4.dupliquer();
        testRotationHoraire.rotationner(RotationPuissance4.HORAIRE);
        if (testRotationHoraire.verifierVictoire() == EtatPartie.VICTOIRE_JOUEUR_1) {
          continue;
        }

        PlateauPuissance4 testRotationAntiHoraire = p4.dupliquer();
        testRotationAntiHoraire.rotationner(RotationPuissance4.ANTI_HORAIRE);
        if (testRotationAntiHoraire.verifierVictoire() == EtatPartie.VICTOIRE_JOUEUR_1) {
          continue;
        }

        // L'insertion ne permet pas à l'adversaire de gagner, alors on l'effectue.
        return new ChoixPuissance4(CoupPuissance4.INSERTION, null, colonneTest);
      }
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
   * @return le cluster max.
   */
  private int calculerClusterMax(
      PlateauPuissance4 plateau, CellulePuissance4 couleur, int colonne, int ligne) {
    int horizontalSuperieur = calculerCluster(plateau, couleur, colonne + 1, ligne, 1, 0);
    int horizontalInferieur = calculerCluster(plateau, couleur, colonne - 1, ligne, -1, 0);
    int horizontal = 1 + horizontalSuperieur + horizontalInferieur;

    int verticalSuperieur = calculerCluster(plateau, couleur, colonne, ligne + 1, 0, 1);
    int verticalInferieur = calculerCluster(plateau, couleur, colonne, ligne - 1, 0, -1);
    int vertical = 1 + verticalSuperieur + verticalInferieur;

    int diagonale1Superieur = calculerCluster(plateau, couleur, colonne + 1, ligne + 1, 1, 1);
    int diagonale1Inferieur = calculerCluster(plateau, couleur, colonne - 1, ligne - 1, -1, -1);
    int diagonale1 = 1 + diagonale1Superieur + diagonale1Inferieur;

    int diagonale2Superieur = calculerCluster(plateau, couleur, colonne + 1, ligne - 1, 1, -1);
    int diagonale2Inferieur = calculerCluster(plateau, couleur, colonne - 1, ligne + 1, -1, 1);
    int diagonale2 = 1 + diagonale2Superieur + diagonale2Inferieur;

    return Math.max(diagonale2, Math.max(diagonale1, Math.max(vertical, horizontal)));
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
   * @return la valeur du cluster
   */
  private int calculerCluster(
      PlateauPuissance4 plateau,
      CellulePuissance4 couleur,
      int colonne,
      int ligne,
      int decalageColonne,
      int decalageLigne) {
    if (colonne < 1
        || colonne < plateau.getLongueur()
        || ligne < 1
        || ligne < plateau.getHauteur()) {
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
}
