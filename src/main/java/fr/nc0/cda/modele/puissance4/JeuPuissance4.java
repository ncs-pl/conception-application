/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

import fr.nc0.cda.modele.jeu.*;
import java.util.ArrayList;
import java.util.List;

/** Représente une partie de Puissance 4. */
public class JeuPuissance4 extends Jeu<PlateauPuissance4, ChoixPuissance4> {
  /** Créer une partie de Puissance 4 et la commence */
  public JeuPuissance4(int longueur, int hauteur) {
    super(new PlateauPuissance4(longueur, hauteur));
  }

  // TODO(nc0): using Stack in PlateauPuissance4 will removes this method.
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
    for (int ligne = this.plateau.getHauteur(); ligne > 0; --ligne) {
      if (this.plateau.get(colonne, ligne) != CellulePuissance4.VIDE) continue;

      this.plateau.set(colonne, ligne, cellule);
      return ligne;
    }

    return -1;
  }

  // TODO(nc0): rename the method to have a verb first.
  /**
   * Vérifie si une condition de victoire est remplie pour la cellule donnée
   *
   * @param colonne la colonne de la cellule insérée, entre 1 et la longueur de la grille
   * @param ligne la ligne de la cellule insérée, entre 1 et la hauteur de la grille
   * @return true si une condition de victoire est remplie, false sinon
   */
  private boolean celluleVictorieuse(int colonne, int ligne) {
    CellulePuissance4 cellule = this.plateau.get(colonne, ligne);
    if (cellule == CellulePuissance4.VIDE) return false;

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

    CellulePuissance4 aD = plateau.get(colonne - 3, ligne);
    CellulePuissance4 bD = plateau.get(colonne - 2, ligne);
    CellulePuissance4 cD = plateau.get(colonne - 1, ligne);
    CellulePuissance4 eD = plateau.get(colonne + 1, ligne);
    CellulePuissance4 fD = plateau.get(colonne + 2, ligne);
    CellulePuissance4 gD = plateau.get(colonne + 3, ligne);
    if (cellule == eD && cellule == fD && cellule == gD) return true;
    if (cellule == cD && cellule == eD && cellule == fD) return true;
    if (cellule == bD && cellule == cD && cellule == eD) return true;
    if (cellule == aD && cellule == bD && cellule == cD) return true;

    CellulePuissance4 dA = plateau.get(colonne, ligne - 3);
    CellulePuissance4 dB = plateau.get(colonne, ligne - 2);
    CellulePuissance4 dC = plateau.get(colonne, ligne - 1);
    CellulePuissance4 dE = plateau.get(colonne, ligne + 1);
    CellulePuissance4 dF = plateau.get(colonne, ligne + 2);
    CellulePuissance4 dG = plateau.get(colonne, ligne + 3);
    if (cellule == dE && cellule == dF && cellule == dG) return true;
    if (cellule == dC && cellule == dE && cellule == dF) return true;
    if (cellule == dB && cellule == dC && cellule == dE) return true;
    if (cellule == dA && cellule == dB && cellule == dC) return true;

    CellulePuissance4 aA = plateau.get(colonne - 3, ligne - 3);
    CellulePuissance4 bB = plateau.get(colonne - 2, ligne - 2);
    CellulePuissance4 cC = plateau.get(colonne - 1, ligne - 1);
    CellulePuissance4 eE = plateau.get(colonne + 1, ligne);
    CellulePuissance4 fF = plateau.get(colonne + 2, ligne + 2);
    CellulePuissance4 gG = plateau.get(colonne + 3, ligne + 3);
    if (cellule == eE && cellule == fF && cellule == gG) return true;
    if (cellule == cC && cellule == eE && cellule == fF) return true;
    if (cellule == bB && cellule == cC && cellule == eE) return true;
    if (cellule == aA && cellule == bB && cellule == cC) return true;

    CellulePuissance4 aG = plateau.get(colonne - 3, ligne + 3);
    CellulePuissance4 bF = plateau.get(colonne - 2, ligne + 2);
    CellulePuissance4 cE = plateau.get(colonne - 1, ligne + 1);
    CellulePuissance4 eC = plateau.get(colonne + 1, ligne);
    CellulePuissance4 fB = plateau.get(colonne + 2, ligne - 2);
    CellulePuissance4 gA = plateau.get(colonne + 3, ligne - 3);
    if (cellule == cE && cellule == bF && cellule == aG) return true;
    if (cellule == eC && cellule == cE && cellule == bF) return true;
    if (cellule == fB && cellule == eC && cellule == cE) return true;
    return cellule == gA && cellule == fB && cellule == eC;
  }

  // TODO(nc0): simplify both arity functions to remove polymorphism.

  /**
   * Vérifie l'état de la partie à partir de la grille à partir de la cellule insérée.
   *
   * @param colonne la colonne de la cellule insérée, entre 1 et la longueur de la grille
   * @param ligne la ligne de la cellule insérée, entre 1 et la hauteur de la grille
   */
  private void actualiserEtatPartie(int colonne, int ligne) {
    CellulePuissance4 cellule = plateau.get(colonne, ligne);

    if (celluleVictorieuse(colonne, ligne)) {
      etatPartie =
          cellule == CellulePuissance4.ROUGE
              ? EtatPartie.VICTOIRE_JOUEUR_1
              : EtatPartie.VICTOIRE_JOUEUR_2;
      return;
    }

    for (int i = 1; i <= plateau.getLongueur(); ++i) {
      if (plateau.get(i, 1) == CellulePuissance4.VIDE) {
        return;
      }
    }

    etatPartie = EtatPartie.MATCH_NUL;
  }

  /** Actualise l'état de la partie en itérant sur la grille. */
  private void actualiserEtatPartie() {
    int victoiresRouges = 0;
    int victoiresJaunes = 0;

    iteration:
    for (int i = 1; i <= this.plateau.getLongueur(); i++) {
      for (int j = 1; j <= this.plateau.getHauteur(); j++) {
        CellulePuissance4 cellule = this.plateau.get(i, j);

        if (cellule == CellulePuissance4.VIDE
            || (cellule == CellulePuissance4.ROUGE && victoiresRouges > 0)
            || (cellule == CellulePuissance4.JAUNE && victoiresJaunes > 0)) {
          continue;
        }

        actualiserEtatPartie(i, j);

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

    if (victoiresRouges > 0 && victoiresJaunes > 0) {
      etatPartie = EtatPartie.MATCH_NUL;
    } else if (victoiresRouges > 0) {
      etatPartie = EtatPartie.VICTOIRE_JOUEUR_1;
    } else if (victoiresJaunes > 0) {
      etatPartie = EtatPartie.VICTOIRE_JOUEUR_2;
    }
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
          // TODO(nc0): merge steps to reduce complexity.
          // One idea would be to start the rotation from columns in a certains
          // order, and putting the non-empty cells in a stack, that we can then
          // fill with empty cells.

          // La rotation fonctionne en déterminant la nouvelle position d'une
          // cellule puis de la faire descendre au plus bas possible de sa
          // colonne, telle l'application de la gravité.
          // Pour l'instant, nous faisons les deux étapes en séparé.
          // TODO(nc0): simplify rotation from O(n^2) to something better.

          RotationPuissance4 rotation = choix.getRotation();
          int longueur = this.plateau.getLongueur();
          int hauteur = this.plateau.getHauteur();
          PlateauPuissance4 plateau2 = new PlateauPuissance4(hauteur, longueur); /* m*n -> n*m */

          // note: l'API de Plateau est 1-indexed
          for (int colonne = 1; colonne <= longueur; ++colonne) {
            for (int ligne = 1; ligne <= hauteur; ++ligne) {
              CellulePuissance4 cellule = plateau2.get(colonne, ligne);
              int colonne2;
              int ligne2;

              if (rotation == RotationPuissance4.HORAIRE) {
                colonne2 = ligne;
                ligne2 = hauteur + 1 - colonne;
              } else {
                colonne2 = longueur + 1 - ligne;
                ligne2 = colonne;
              }

              plateau2.set(colonne2, ligne2, cellule);
            }
          }

          // Pour faire tomber vers le bas, il suffit de supprimer toutes les
          // cellules vides puis de les insérer à la fin.
          // TODO(nc0): use Stacks within PlateauPuissance4 implementation

          plateau = new PlateauPuissance4(hauteur, longueur);

          List<CellulePuissance4> file = new ArrayList<>();
          for (int colonne = 1; colonne <= longueur; ++colonne) {
            int tailleFile = 0;

            for (int ligne = 1; ligne <= hauteur; ++ligne) {
              CellulePuissance4 cellule = plateau2.get(colonne, ligne);

              if (cellule != CellulePuissance4.VIDE) {
                file.add(cellule);
                ++tailleFile;
              }
            }

            if (tailleFile < longueur) {
              for (int k = 0; k < tailleFile; ++k) {
                file.add(CellulePuissance4.VIDE);
              }
            }

            for (CellulePuissance4 cellule : file) {
              insererCellule(colonne, cellule);
            }

            file.clear();
          }

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

          if (plateau.get(colonne, 1) != CellulePuissance4.VIDE) {
            throw new CoupInvalideException("la colonne " + colonne + " est pleine");
          }

          CellulePuissance4 cellule =
              joueur == Joueurs.JOUEUR_1 ? CellulePuissance4.ROUGE : CellulePuissance4.JAUNE;
          int ligne = insererCellule(colonne, cellule);

          actualiserEtatPartie(colonne, ligne);
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
