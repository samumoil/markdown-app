package net.samumoila.markdownapp;

/**
 * Luokan Teksti alaluokka, joka tässä ohjelman versiossa on ainut käytettävä tekstiolion luokka.
 *
 * Ei sisällä tällä hetkellä erityistä toiminnallisuutta, mutta tulevaisuudessa tänne voisi lisätä
 * esimerkiksi pääotsikoiden sijainnin ja lukumäärän.
 */
public class TekstiMarkdown extends Teksti {

    /**
     * Oletusalustaja, joka luo tyhjän tekstiolion.
     */
    public TekstiMarkdown() {
        super();
    }

    /**
     * Alustaja, joka luo tekstiolion annetulla tekstillä.
     *
     * @param teksti Annettu teksti String-oliona.
     */
    public TekstiMarkdown(String teksti) {
        super(teksti);
    }
}
