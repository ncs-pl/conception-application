/*
 * Copyright (c) 2024 Lucas Paulo, Younes Ouaammou, Nicolas Paul.
 * Use of this source code is governed by a BSD-style license
 * that can be found in the COPYRIGHT file.
 */

package fr.nc0.cda.main;

import fr.nc0.cda.controleur.ControleurJeuNim;
import fr.nc0.cda.vue.Ihm;

public class MainNim {
  public static void main(String[] args) {
    Ihm ihm = new Ihm();
    ControleurJeuNim controleurJeuNim = new ControleurJeuNim(ihm);
    controleurJeuNim.jouer();
  }
}
