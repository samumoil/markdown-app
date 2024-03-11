# LUOKAT

## TiedostonLukija
- Hoitaa tiedoston lukemiseen liittyvän toiminnallisuuden
- Palauttaa String-olion
- Kutsutaan Teksti-luokasta



## Teksti - INTERFACE
- Sisältää String-muodossa itse tekstin, TiedostonLukijan palauttama
- Rivien määrä
- Merkkien määrä
- Sanojen määrä

Metodeja:
- updateText (päivittää tekstin)
- updateCounts (päivittää rivit, merkit, sanat)
- getRivit/getSanat/getMerkit

### TekstiMarkdown

### TekstiOther


## Kayttoliittyma


TODO:
- Rivien näyttäminen vasemmassa laidassa
- Fonttikoon muuttaminen
- Dark mode
- Käytössä olevien markdown elementtien lista


## MerkkienTulkitsija
- VOIKO TÄSSÄ HYÖDYNTÄÄ RAJAPINTAA?
	- Jokaiselle merkille oma tulkitsija?



# SATUNNAISIA AJATUKSIA

- Abstraktin luokan hyödyntäminen

- Line wrapping?
- Ideoinnissa on hyödynnetty ghostwriter-ohjelmaa
- UI englanniksi
- Käytä StringBuilder
- Oletus teksti lorem ipsum?
