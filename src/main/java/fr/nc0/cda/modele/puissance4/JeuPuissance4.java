/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.modele.puissance4;

import fr.nc0.cda.modele.*;

/** Représente une partie de Puissance 4. */
public class JeuPuissance4 extends Jeu<PlateauPuissance4, ChoixPuissance4> {
  /** Créer une partie de Puissance 4 et la commence */
  public JeuPuissance4(int longueur, int hauteur) {
    super(new PlateauPuissance4(longueur, hauteur));
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
          plateau = plateau.rotationner(rotation);
          etatPartie = plateau.verifierVictoire();
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

          if (plateau.verifierCelluleVictorieuse(colonne, ligne)) {
            etatPartie =
                cellule == CellulePuissance4.ROUGE
                    ? EtatPartie.VICTOIRE_JOUEUR_1
                    : EtatPartie.VICTOIRE_JOUEUR_2;
          } else if (plateau.estPlein()) {
            etatPartie = EtatPartie.MATCH_NUL;
          }

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
