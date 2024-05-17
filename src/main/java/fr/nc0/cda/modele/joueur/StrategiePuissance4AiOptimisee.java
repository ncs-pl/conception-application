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

public class StrategiePuissance4AiOptimisee implements Strategie {
  private final StrategiePuissance4AiSimple simple = new StrategiePuissance4AiSimple();
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

        int jaune = simple.calculerClusterMax(p4Jaune, CellulePuissance4.JAUNE, colonne, ligne);
        boolean alignementPossible = alignementPossibleAll(p4Jaune, colonne, ligne);

        if (jaune >= 4) {
          priorites.get(6).add(colonne);
        } else if (alignementPossible && jaune == 3) {
          priorites.get(4).add(colonne);
        } else if (alignementPossible && jaune == 2) {
          priorites.get(2).add(colonne);
        } else {
          priorites.get(0).add(colonne);
        }

        int rouge = simple.calculerClusterMax(p4Rouge, CellulePuissance4.ROUGE, colonne, ligne);
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
