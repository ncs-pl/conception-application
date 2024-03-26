/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.puissance4.main;

import fr.nc0.cda.puissance4.controleur.ControleurPuissance4;
import fr.nc0.cda.puissance4.vue.Ihm;

public class Main {
  public static void main(String[] args) {
    Ihm ihm = new Ihm();
    ControleurPuissance4 p4 = new ControleurPuissance4(ihm);
    p4.jouer();
  }
}
