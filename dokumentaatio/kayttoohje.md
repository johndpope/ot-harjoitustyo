# Käyttöohje

## Ennakkovaatimukset
Jotta pelin voi käynnistää ja jotta sitä voi pelata, täytyy [java](https://www.java.com/en/) olla asennettuna. Peli vaatii myös internet-yhteyden sen avaamisen yhteydessä.

## Lataus
Lataa ensin pelin suoritettava [jar -tiedosto](https://github.com/Zentryn/ot-harjoitustyo/releases/download/Viikko6/Roguelike-VK6.jar)

## Ohjelman käynnistäminen
Ohjelma käynnistetään komennolla
```
java -jar todoapp.jar
```

## Pelin toiminta ja komennot
Kun peli alkaa, näkyy ruudulla pelikenttä joka koostuu erilaisista tiilistä. Pelaaja on merkkitty kentässä _@_ -merkillä ja viholliset on merkkitty merkillä _Z_. Sekä pelaaja, että viholliset voivat liikkua tiilillä, joissa on lattiaa, jotka on merkitty pisteellä (.).

Pelihahmoa liikutetaan nuolinäppäimillä. Kun liikutat pelaajaa, tekee viholliset tämän jälkeen vuoronsa.

Pelissä on erilaisia tavaroita, kuten aseita ja armoreita. Aseet on merkitty pelikentässä merkillä _W_ ja armorit merkillä _A_. Pelistä löytyy myös pommeja, jotka on merkitty merkillä _B_. Jos pelaajalla on pommi, voi sen käyttää painamalla välilyöntiä.

Pelitason pääsee läpi, kun pelaaja liikkuu tason uloskäyntiin, joka on merkitty merkillä _O_. Pääse jokaisen tason uloskäyntiin ja voitat pelin.
