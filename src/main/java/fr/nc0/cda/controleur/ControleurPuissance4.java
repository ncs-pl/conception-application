/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.controleur;

import fr.nc0.cda.modele.jeu.CoupInvalideException;
import fr.nc0.cda.modele.jeu.EtatPartie;
import fr.nc0.cda.modele.jeu.EtatPartieException;
import fr.nc0.cda.modele.jeu.Joueurs;
import fr.nc0.cda.modele.joueur.Joueur;
import fr.nc0.cda.modele.puissance4.*;
import fr.nc0.cda.vue.Ihm;

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

  /** La partie en cours de Puissance 4 */
  private JeuPuissance4 puissance4;

  /** True si la partie peut se faire avec des rotations. */
  private boolean rotationsActivees = false;

  /** Rotations restantes possibles pour le premier joueur */
  private int rotationsRestantesJoueur1;

  /** Rotations restantes possibles pour le second joueur */
  private int rotationsRestantesJoueur2;

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
    return puissance4.getPlateau().toString();
  }

  @Override
  EtatPartie getEtatPartie() {
    return puissance4.getEtatPartie();
  }

  @Override
  void initialiserPartie() {
    puissance4 = new JeuPuissance4(LONGUEUR, HAUTEUR);
    rotationsActivees =
        ihm.demanderBoolean("Voulez-vous activer la possibilité de rotation de la grille ?");
    if (rotationsActivees) {
      rotationsRestantesJoueur1 = ROTATIONS_DISPONIBLES_DEFAUT;
      rotationsRestantesJoueur2 = ROTATIONS_DISPONIBLES_DEFAUT;
    }
  }

  @Override
  void jouerCoup() throws CoupInvalideException, EtatPartieException {
    Joueur joueur = getJoueur(joueurCourant);
    // TODO(nc0): avoid casting?
    ChoixPuissance4 choix =
        (ChoixPuissance4) joueur.getStrategie().jouer(ihm, puissance4.getPlateau(), joueur);

    if (choix.getCoup() == CoupPuissance4.ROTATION) {
      if (!rotationsActivees) {
        throw new CoupInvalideException("Les rotations ne sont pas autorisées cette partie.");
      }

      int rotationsRestantesJoueur =
          joueurCourant == Joueurs.JOUEUR_1 ? rotationsRestantesJoueur1 : rotationsRestantesJoueur2;
      if (rotationsRestantesJoueur <= 0) {
        throw new CoupInvalideException("Vous avez utilisé toutes vos rotations possibles.");
      }

      if (joueurCourant == Joueurs.JOUEUR_1) {
        --rotationsRestantesJoueur1;
      } else {
        --rotationsRestantesJoueur2;
      }
    }

    puissance4.jouer(joueurCourant, choix);
  }
}
