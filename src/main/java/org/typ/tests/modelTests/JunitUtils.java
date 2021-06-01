package org.typ.tests.modelTests;

import org.typ.view.ViewMode;

import java.util.Observable;

/** Regroupe des fonctions et des attributs essentiel au classes de testes
 *
 */
public class JunitUtils {

    /** Permet de tester las classes qui implémentent l'AbstractCorrector
     *
     */
    public static class ViewDummyMode implements ViewMode {
        @Override
        public void update(Observable observable, Object o) {
        }
    }

    /** Retourne une vue qui ne fait rien pour tester les sous-classe de AbstractCorrector
     *
     * @return une dummyViewMode
     */
    public static ViewMode dummyViewMode(){
        return new ViewDummyMode();
    }

}
