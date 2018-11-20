# Roguelike

Roguelike on 2D vuoropohjainen luolastoseikkailupeli, jossa pelaaja liikkuu luolastoissa. Luolastoissa tulee vastaan vihollisia joita vastaan voi taistella ja tavaroita joita voi kerätä jotka vaikuttavat pelaajan vahvuuteen. Pelin tarkoituksena on selvitä seuraaville tasoille ja voittaa näin peli pääsemällä viimeinen taso läpi.

## Dokumentaatio
[Vaatimusmäärittely](https://github.com/Zentryn/ot-harjoitustyo/blob/master/Roguelike/dokumentaatio/vaatimusmaarittely.md)

[Tuntikirjanpito](https://github.com/Zentryn/ot-harjoitustyo/blob/master/Roguelike/dokumentaatio/tuntikirjanpito.md)

## Komentorivitoiminnot

Testit suoritetaan komennolla
```
mvn test
```

Testikattavuusraportti luodaan komennolla
```
mvn jacoco:report
```

Suoritettavan jar -tiedoston sovelluksesta saa generoitua komennolla
```
mvn package
```
Komennon ajon jälkeen suoritettava jar -tiedosto löytyy target/ -kansion alta nimellä _Roguelike-1.0-SNAPSHOT.jar_
