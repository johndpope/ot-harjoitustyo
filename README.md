# Roguelike

Roguelike on 2D vuoropohjainen luolastoseikkailupeli, jossa pelaaja liikkuu luolastoissa. Luolastoissa tulee vastaan vihollisia joita vastaan voi taistella ja tavaroita joita voi kerätä jotka vaikuttavat pelaajan vahvuuteen. Pelin tarkoituksena on selvitä seuraaville tasoille ja voittaa näin peli pääsemällä viimeinen taso läpi.

## Releaset
[Viikko 5](https://github.com/Zentryn/ot-harjoitustyo/releases/tag/Viikko5)

[Viikko 6](https://github.com/Zentryn/ot-harjoitustyo/releases/tag/Viikko6)

## Dokumentaatio
[Vaatimusmäärittely](https://github.com/Zentryn/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Tuntikirjanpito](https://github.com/Zentryn/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

[Arkkitehtuurikuvaus](https://github.com/Zentryn/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla
```
mvn test
```



Testikattavuusraportti luodaan komennolla
```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jar -tiedoston generointi
Suoritettavan jar -tiedoston sovelluksesta saa generoitua komennolla
```
mvn package
```
Komennon ajon jälkeen suoritettava jar -tiedosto löytyy target/ -kansion alta nimellä _Roguelike-1.0-SNAPSHOT.jar_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/Zentryn/ot-harjoitustyo/blob/master/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
