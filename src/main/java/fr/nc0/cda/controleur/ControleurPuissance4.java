/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.controleur;

import fr.nc0.cda.modele.CoupInvalideException;
import fr.nc0.cda.modele.EtatPartie;
import fr.nc0.cda.modele.EtatPartieException;
import fr.nc0.cda.modele.joueur.Joueur;
import fr.nc0.cda.modele.puissance4.*;
import fr.nc0.cda.vue.Ihm;
import java.util.ArrayList;
import java.util.List;

/**
 * Contrôleur du jeu Puissance 4.
 *
 * <p>Cette classe gère le déroulement d'une partie de Puissance 4 en utilisant le modèle Puissance4
 * et l'interface utilisateur Ihm.
 */
public class ControleurPuissance4 extends ControleurTemplate {
  /** La longueur d'une grille de Puissance 4 */
  private static final int LONGUEUR = 7;

  /** La hauteur d'une grille de Puissance 4 */
  private static final int HAUTEUR = 7;

  /** Rotations disponibles par défaut */
  private static final int ROTATIONS_DISPONIBLES_DEFAUT = 4;

  /** Les rotations restantes pour chaque joueur */
  private List<Integer> rotationsRestantes;

  /** La partie en cours de Puissance 4 */
  private JeuPuissance4 jeuPuissance4;

  /** True si la partie peut se faire avec des rotations. */
  private boolean rotationsActivees = false;

  /**
   * Constructeur de la classe ControleurPuissance4.
   *
   * @param ihm L'interface utilisateur pour les interactions avec le jeu.
   */
  public ControleurPuissance4(Ihm ihm) {
    super(ihm);
  }

  @Override
  String creerAffichagePlateau() {
    return jeuPuissance4.getPlateauPuissance4().toString();
  }

  @Override
  EtatPartie getEtatPartie() {
    return jeuPuissance4.getEtatPartie();
  }

  @Override
  void initialiserPartie() {
    jeuPuissance4 = new JeuPuissance4(LONGUEUR, HAUTEUR);
    rotationsActivees =
        ihm.demanderBoolean("Voulez-vous activer la possibilité de rotation de la grille ?");
    rotationsRestantes =
        new ArrayList<>(List.of(ROTATIONS_DISPONIBLES_DEFAUT, ROTATIONS_DISPONIBLES_DEFAUT));
  }

  @Override
  void jouerCoup() throws CoupInvalideException, EtatPartieException {
    Joueur joueur = getJoueur(joueurCourant);

    while (true) {
      ChoixPuissance4 choix =
          (ChoixPuissance4) joueur.getStrategie().jouer(ihm, jeuPuissance4.getPlateauPuissance4());

      if (choix.getCoup() == CoupPuissance4.ROTATION) {
        RotationPuissance4 rotation = choix.getRotation();

        if (!rotationsActivees) {
          ihm.afficherMessage("Les rotations ne sont pas autorisées cette partie.");
          continue;
        }

        int rotationsRestantesJoueur = rotationsRestantes.get(joueurCourant - 1);
        if (rotationsRestantesJoueur == 0) {
          ihm.afficherErreur("Vous avez utilisé toutes vos rotations possibles.");
          continue;
        }

        rotationsRestantes.set(joueurCourant - 1, rotationsRestantesJoueur - 1);
        jeuPuissance4.rotationner(rotation);
      } else {
        int colonne = choix.getColonne();

        if (jeuPuissance4.colonneInvalide(colonne)) {
          ihm.afficherErreur("Veuillez choisir une colonne valide.");
          continue;
        }

        CellulePuissance4 cellule =
            joueurCourant == 1 ? CellulePuissance4.ROUGE : CellulePuissance4.JAUNE;
        jeuPuissance4.jouer(cellule, colonne);
      }

      break;
    }
  }
}
