/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

import fr.nc0.cda.modele.jeu.*;

/** Représente une partie de Puissance 4. */
public class JeuPuissance4 extends Jeu<PlateauPuissance4, ChoixPuissance4> {
  /** Créer une partie de Puissance 4 et la commence */
  public JeuPuissance4(int longueur, int hauteur) {
    super(new PlateauPuissance4(longueur, hauteur));
  }

  /**
   * À partir de la cellule gagnante, déclare le match comme terminé sur une victoire.
   *
   * @param celluleGagnante la cellule gagnante
   */
  private void declarerVictoire(CellulePuissance4 celluleGagnante) {
    etatPartie =
        celluleGagnante == CellulePuissance4.ROUGE
            ? EtatPartie.VICTOIRE_JOUEUR_1
            : EtatPartie.VICTOIRE_JOUEUR_2;
  }

  /**
   * Vérifie l'état de la partie à partir de la grille à partir de la cellule insérée.
   *
   * @param colonne la colonne de la cellule insérée, entre 1 et la longueur de la grille
   * @param ligne la ligne de la cellule insérée, entre 1 et la hauteur de la grille
   */
  private void actualiserEtatPartieViaCellule(int colonne, int ligne) {
    CellulePuissance4 cellule = plateau.getCellule(colonne, ligne);
    if (cellule == CellulePuissance4.VIDE) return;

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
    verifierVictoireLigne(cellule, aD, bD, cD, eD, fD, gD);

    CellulePuissance4 dA = plateau.getCellule(colonne, ligne - 3);
    CellulePuissance4 dB = plateau.getCellule(colonne, ligne - 2);
    CellulePuissance4 dC = plateau.getCellule(colonne, ligne - 1);
    CellulePuissance4 dE = plateau.getCellule(colonne, ligne + 1);
    CellulePuissance4 dF = plateau.getCellule(colonne, ligne + 2);
    CellulePuissance4 dG = plateau.getCellule(colonne, ligne + 3);
    verifierVictoireLigne(cellule, dA, dB, dC, dE, dF, dG);

    CellulePuissance4 aA = plateau.getCellule(colonne - 3, ligne - 3);
    CellulePuissance4 bB = plateau.getCellule(colonne - 2, ligne - 2);
    CellulePuissance4 cC = plateau.getCellule(colonne - 1, ligne - 1);
    CellulePuissance4 eE = plateau.getCellule(colonne + 1, ligne + 1);
    CellulePuissance4 fF = plateau.getCellule(colonne + 2, ligne + 2);
    CellulePuissance4 gG = plateau.getCellule(colonne + 3, ligne + 3);
    verifierVictoireLigne(cellule, aA, bB, cC, eE, fF, gG);

    CellulePuissance4 aG = plateau.getCellule(colonne - 3, ligne + 3);
    CellulePuissance4 bF = plateau.getCellule(colonne - 2, ligne + 2);
    CellulePuissance4 cE = plateau.getCellule(colonne - 1, ligne + 1);
    CellulePuissance4 eC = plateau.getCellule(colonne + 1, ligne - 1);
    CellulePuissance4 fB = plateau.getCellule(colonne + 2, ligne - 2);
    CellulePuissance4 gA = plateau.getCellule(colonne + 3, ligne - 3);
    verifierVictoireLigne(cellule, gA, fB, eC, cE, bF, aG);

    // Match Nul

    if (plateau.estPlein()) {
      etatPartie = EtatPartie.MATCH_NUL;
    }
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
  private void verifierVictoireLigne(
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
      declarerVictoire(cellule);
    }
  }

  /** Actualise l'état de la partie en itérant sur la grille. */
  private void actualiserEtatPartie() {
    int victoiresRouges = 0;
    int victoiresJaunes = 0;

    iteration:
    for (int colonne = 1; colonne <= this.plateau.getLongueur(); ++colonne) {
      for (int ligne = 1; ligne <= this.plateau.getHauteur(); ++ligne) {
        CellulePuissance4 cellule = this.plateau.getCellule(colonne, ligne);
        if (cellule == CellulePuissance4.VIDE
            || (cellule == CellulePuissance4.ROUGE && victoiresRouges == 1)
            || (cellule == CellulePuissance4.JAUNE && victoiresJaunes == 1)) {
          continue;
        }

        actualiserEtatPartieViaCellule(colonne, ligne);

        if (etatPartie == EtatPartie.VICTOIRE_JOUEUR_1) {
          victoiresRouges = 1;
        } else if (etatPartie == EtatPartie.VICTOIRE_JOUEUR_2) {
          victoiresJaunes = 1;
        }

        // Early exit
        if (victoiresRouges > 0 && victoiresJaunes > 0) {
          break iteration;
        }
      }
    }

    if (victoiresRouges == 1 && victoiresJaunes == 1) {
      etatPartie = EtatPartie.MATCH_NUL;
    } else if (victoiresRouges > victoiresJaunes) {
      etatPartie = EtatPartie.VICTOIRE_JOUEUR_1;
    } else if (victoiresJaunes > victoiresRouges) {
      etatPartie = EtatPartie.VICTOIRE_JOUEUR_2;
    }
    // else -> continuer partie
  }

  @Override
  public void jouer(Joueurs joueur, ChoixPuissance4 choix)
      throws CoupInvalideException, EtatPartieException {
    if (etatPartie != EtatPartie.EN_COURS) {
      throw new EtatPartieException("la partie est terminée");
    }

    switch (choix.getCoup()) {
      case ROTATION:
        {
          RotationPuissance4 rotation = choix.getRotation();
          int longueur = plateau.getLongueur();
          int hauteur = plateau.getHauteur();
          PlateauPuissance4 nouveauPlateau = new PlateauPuissance4(hauteur, longueur);

          for (int ligne = 1; ligne <= hauteur; ++ligne) {
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

          plateau = nouveauPlateau;
          actualiserEtatPartie();
          break;
        }
      case INSERTION:
        {
          int colonne = choix.getColonne();
          if (colonne < 1) {
            throw new CoupInvalideException("la colonne est inférieure à 1");
          }

          int longueur = plateau.getLongueur();
          if (colonne > longueur) {
            throw new CoupInvalideException(
                "la colonne dépasse la longueur de la grille (" + longueur + ")");
          }

          if (plateau.verifierColonnePleine(colonne)) {
            throw new CoupInvalideException("la colonne " + colonne + " est pleine");
          }

          CellulePuissance4 cellule =
              joueur == Joueurs.JOUEUR_1 ? CellulePuissance4.ROUGE : CellulePuissance4.JAUNE;
          int ligne = plateau.insererCellule(colonne, cellule);

          actualiserEtatPartieViaCellule(colonne, ligne);
          break;
        }
      default:
        {
          /* unreachable */
          throw new CoupInvalideException("la coup demandé est invalide");
        }
    }
  }
}
