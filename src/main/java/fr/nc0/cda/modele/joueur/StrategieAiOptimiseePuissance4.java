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

public class StrategieAiOptimiseePuissance4 implements Strategie {
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

        int jaune = calculerClusterMax(p4Jaune, CellulePuissance4.JAUNE, colonne, ligne);
        if (jaune >= 4) {
          priorites.get(6).add(colonne);
        } else if (jaune == 3) {
          if (alignementPossibleAll(p4Jaune, colonne, ligne)) {
            priorites.get(4).add(colonne);
          }
        } else if (jaune == 2) {
          if (alignementPossibleAll(p4Jaune, colonne, ligne)) {
            priorites.get(2).add(colonne);
          }
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

    // TODO: Complete
    // Pour chaque action possible parmi nos priorités, on la teste.
    for (int i = priorites.size() - 1; i >= 0; --i) {

      int colonneTest = priorites.get(i).get(rand.nextInt(priorites.get(i).size()));
      PlateauPuissance4 test = p4.dupliquer();
      test.insererCellule(colonneTest, CellulePuissance4.JAUNE);

      // L'insertion est effectuée.
      return new ChoixPuissance4(CoupPuissance4.INSERTION, null, colonneTest);
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
   * Vérifie si avec ce coup, on peut potentiellement aligner 4 jetons.
   *
   * @param plateau le plateau
   * @param colonne la colonne
   * @param ligne la ligne
   * @return true si un alignement de 4 jetons est possible, sinon false.
   */
  private boolean alignementPossibleAll(PlateauPuissance4 plateau, int colonne, int ligne) {
    // Vérifie dans les directions horizontale, verticale, diagonales
    return alignementPossible(plateau, colonne, ligne, 1, 0)
        || alignementPossible(plateau, colonne, ligne, 0, 1)
        || alignementPossible(plateau, colonne, ligne, 1, 1)
        || alignementPossible(plateau, colonne, ligne, 1, -1);
  }

  /**
   * Vérifie si l'alignement dans une direction donnée peut potentiellement aligner 4 jetons.
   *
   * @param plateau le plateau
   * @param colonne la colonne
   * @param ligne la ligne
   * @param decalageColonne la différence de colonne pour la direction
   * @param decalageLigne la différence de ligne pour la direction
   * @return true si un alignement de 4 est possible dans cette direction, sinon false.
   */
  private boolean alignementPossible(
      PlateauPuissance4 plateau, int colonne, int ligne, int decalageColonne, int decalageLigne) {
    int compteur = 1;

    // Vérifier décalage positif
    int c = colonne + decalageColonne;
    int l = ligne + decalageLigne;
    while (c >= 1
            && c <= plateau.getLongueur()
            && l >= 1
            && l <= plateau.getHauteur()
            && plateau.getCellule(c, l) == CellulePuissance4.JAUNE
        || plateau.getCellule(c, l) == CellulePuissance4.VIDE) {
      compteur++;
      c += decalageColonne;
      l += decalageLigne;
    }

    // Vérifier décalage négatif
    c = colonne - decalageColonne;
    l = ligne - decalageLigne;
    while (c >= 1
            && c <= plateau.getLongueur()
            && l >= 1
            && l <= plateau.getHauteur()
            && plateau.getCellule(c, l) == CellulePuissance4.JAUNE
        || plateau.getCellule(c, l) == CellulePuissance4.VIDE) {
      compteur++;
      c -= decalageColonne;
      l -= decalageLigne;
    }

    return compteur >= 4;
  }
}
