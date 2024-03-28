# PROJEKTIKERTOMUS

Itä-Suomen yliopiston Ohjelmointi II -kurssin lopputyönä opiskelijoiden tuli koodata itse ohjelma, joka monipuolisesti hyödyntää Java-kielen luokkia, rajapintoja sekä javafx-ulkoasun ominaisuuksia. Olen jo jonkin aikaa ollut kiinnostunut Markdown-kielestä ja siitä sain ajatuksen tehdä oma markdown-editori. 

Mielestäni projektin koodaus oli mielekästä enkä missään vaiheessa ollut aivan pulassa koodin kanssa. Yksi isompi takaisku tai luovutus tapahtui, kun päätin, etten ala itse koodaamaan markdown-parseria, vaan otin käyttöön valmiin parserin. Mielestäni tämä oli kuitenkin oikea päätös, koska erilaiset algoritmit ja tiedon analysointi ei ollut tämän kurssin pääaiheena.

Markdown-editori oli muuten oikein hyvä valinta projektityöksi, mutta siinä en päässyt ehkä niin hyvin esittelemään javafx-osaamistani, kuin olisin halunnut. En myöskään päässyt käyttämään hyödyksi javan geneerisiä luokkia tai linkitettyä listaa, joita olisin halunnut kokeilla käytännössä.

Koen onnistuneeni hyvin luokkien suunnittelussa. Apuluokat eivät ole riippuvaisia toisistaan ja ohjelman logiikkaa ohjataan yhdestä tai kahdesta paikasta. Luokkien eriyttäminen on ollut luonnollinen ajatus ja tässä sitä pääsi harjoittelemaan hyvin. Myös dokumentaatio on mielestäni onnnistunut hyvin ja opin tekemään koodin logiikkaan liittyvää dokumentaatiota osana koodausta.

Parannettavaa tässä työssä on kesken jäänyt rajapinnan ja Teksti-luokan hyödyntäminen. Tarkoitukseni oli luoda tiedoston avaamiseen logiikka, joka tunnistaa tiedostopäätteen ja sen perusteella luo käsiteltävästä tekstioliosta joko markdown-olion tai tavallisen tekstiolion. Tähän en kuitenkaan ryhtynyt ajanpuutteen vuoksi. Käyttöliittymän väritystä ja muuta pientä yksityiskohtaa olisi ehkä voinut myös hioa, mutta mielestäni se on nyt käyttäjän näkökulmasta jo riittävän selkeä.

# Päiväkirja projektin etenemisestä

## 05.03. - 0,25h
- Projektin aloitus ja materiaaliin tutustuminen. Alkuperäisen idean keksiminen ja ilmoitus keskustelualueelle.

## 11.03. - 5,75h
- Työn vaatimusten kertausta
- Alustava suunnitelma luokkien tehtävistä
- TextSimple aika lähelle valmista
- Muiden luokkien alustava runko
- Yritin saada iteraattoria toimimaan

## 12.03. - 3h
- Sain toimimaan TextSimple wordCount
- Ensimmäinen versio käyttöliittymästä.
- MenuBar

## 14.03. - 2h
- Tiedoston avausta ja tallennusta
- FileChooser
- Englanninkieliset asiat suomeksi

## 15.03. - 2,5h
- Tekstikenttien koko ja reagointi ikkunan koon muutokseen.

## 21.03. - 1h
- Yritetty saada omaa markdown toimintalogiikkaa toimimaan.

## 22.03. - 4h
- Hylätty oman logiikan käyttö ja etsitty sopivaa valmista parseria.
- Otetaan käyttöön https://github.com/commonmark/commonmark-java
- Päivitetty pom.xml and module-info.java sisältämään vaadittavat rivit.
- Markdown-logiikka toimii nyt oikein.
- Luotu säikeitä hyödyntävä markdown-renderöinti. (Se ei vielä toiminut oikein tässä vaiheessa.)

## 24.03. - 2h
- Lisätty dokumentaatiota.
- Löydetty ja poistettu system.out.println joka aiheutti tekstin tulostuksen komentoriville.
- Hienosäädetty avaus- ja tallennusikkunoiden asioita. Vielä kesken.
- Muita pikkusäätöjä ja logiikan parantamista.

## 25.03. - 3h
- Lisätty dokumentaatiota.
- Korjattu renderöinnin "lagi". Tuli uusi ongelma tilalle, kun tervetuloteksti ei näy.
- Aloitettu käymään läpi muuttujia ja muutettu niitä private/protected.

## 26.03. - 3,5h
- Viimeistelty dokumentaatio.
- Luotu dokumentaatio pääkansioon.
- Hienosäädetty projektin määrittelytiedostoja.

## 27.3. - 2h
- Pientä hienosäätöä
- Lisätty "piilota/näytä markdown"-nappi ja sen toiminnallisuus
- Aloitettu projektikertomuksen ja muiden kurssiin liittyvien dokumenttien kirjaaminen.
- UML-kaavio piirretty.

## 28.3. - 2h
- Kurssin ja projektin dokumentaation viimeistelyä.
