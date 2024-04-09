/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.main;

import fr.nc0.cda.controleur.ControleurPuissance4;
import fr.nc0.cda.vue.Ihm;

/**
 * Point d'entrée de l'application Puissance 4.
 *
 * <p>Cette classe initialise l'interface utilisateur et le contrôleur du jeu Puissance 4, puis
 * lance une partie.
 */
public class MainPuissance4 {
  public static void main(String[] args) {
    Ihm ihm = new Ihm(); // Initialisation de l'IHM
    ControleurPuissance4 p4 =
        new ControleurPuissance4(ihm); // Initialisation du contrôleur Puissance 4 avec l'IHM
    p4.jouer(); // Lancement d'une partie de Puissance 4
  }
}
