# Vaatimuusmäärittely

## Sovelluksen tarkoitus

Sovellus on vuoropohjainen 2D videopeli, missä pelaaja liikkuu luolistoissa (dungeon), jossa pelaajan täytyy taistella vihollisia vastaan ja selvitä seuraaville tasoille.

## Toiminnallisuudet

- Pelaaja (käyttäjä) liikkuu pelimaailmassa nuolinäppäimillä
- Pelissä on vihollisia joita vastaan pelaajan täytyy taistella
  - Viholliset liikkuvat joko satunnaisesti, tai ne yrittävät seurata ja hyökätä pelaajan kimppuun.
  - Pelissä on myös hieman edistyneempi polkujenetsimisalgoritmi, joka mahdollistaa viholliset jotka löytävät pelaajan pitkänkin matkan päästä ja jopa seinien takaa.
- Peliin sisältyy ns. "toimintoloki", joka kertoo ruudulle viesteinä mitä pelissä tapahtuu
  - Esimerkiksi jos pelaaja taistelee vihollista vastaa, tulee tähän lokiin tietoa taistelusta tai jos pelaaja löytää esineen, kertoo loki siitä
- Pelikentistä löytyy erilaisia esineitä ja tavaroita, joita pelaaja voi kerätä. Tähän sisältyy mm:
  - Aseita, jotka antavat pelaajalle enemmän vahvuutta vihollisia vastaan
  - Tavaroita, jotka antavat pelaajalle lisää suojaa vihollisten hyökkäyksiltä
- Pelin päätarkoituksena on selvitä seuraaville tasoille ja lopulta voittaa peli pääsemällä viimeinen taso läpi
