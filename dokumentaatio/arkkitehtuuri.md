# Arkkitehtuurikuvaus

## Rakenne
Ohjelma on rakentunut kolmesta pääpaukkauksesta ja yhdestä erillisestä pakkauksesta joka ei koske muihin osiin sovellusta.

Pakkaus _roguelike.ui_ sisältää JavaFX:llä toteutetun käyttöliittymän koodin

Pakkaus _roguelike.domain_ sisältää sovelluslogiikan

Pakkaus _roguelike.dao_ sisältää tiedostoja käsittelevää koodia

Pakkaus _roguelike.util_ on ns. "erillinen" pakkaus joka sisältää globaaleja metodeja yleiskäyttöön

## Käyttöliittymä
Käyttöliittymään sisältyy pelaajalle näytettävä pelikenttä, pelaajan statistiikat tekstinä ja toimintaloki.

Kaikki käyttöliittymän sisällä on samassa [Scene](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/Scene.html) -oliossa. Sovellus näyttää aina yhden tason kerrallaan ja luo uudelle tasolle uuden Scenen.

Käyttöliittymän koodi on eritelty sovelluslogiikasta; se saa tarvitsemansa tiedot pelikentästä _LevelManager_ -luokan kautta ja muuten se huolehtii vain käyttöliittymästä.

Käyttöliittymän renderöinti on toteutettu JavaFX:n Text -objekteja käyttäen, jotka sisältävät vain tekstiä joka sitten näytetään sovellusikkunassa.

## Sovellulogiikka
Sovelluksen toimintaa hallinnoi _LevelManager_ -luokka, jota kutsutaan käyttöliittymän koodista kun pelaaja liikuttaa pelihahmoa. LevelManager -luokka huolehtii pelitasojen luomisesta ja niiden hallinnoimisesta. Luokka liikuttaa pelaajaa sen oman _Unit_ -pääluokan metodin _move()_ -metodin kautta jonka jälkeen UIManager -luokassa rekisteröity callback -metodi pyytää tasoa liikuttamaan sen vihollisia. Viholliset liikkuvat myös käyttäen _Unit_ -luokan move() -metodia, mutta vihollisille lasketaan ensin haluttu toiminta _Level_ -luokan _moveEnemies()_ metodin kautta.

Pelin pyöriminen perustuu siis siihen, että pelaaja tekee jonkin liikeen, jonka jälkeen kaikki viholliset tekevät jonkin liikkeen. Tämän jälkeen tämä toistuu jos pelaaja ei ole voittanut tai hävinnyt peliä.

## Luokkakaavio

![](https://raw.githubusercontent.com/Zentryn/ot-harjoitustyo/master/dokumentaatio/kuvat/luokkakaavio.png)


## Päätoiminnallisuudet
Peli tekee toimintoja aina sen jälkeen kun pelaaja painaa jotakin näppäintä. Tässä esimerkki mahdollisista toiminnoista jotka voivat tapahtua, kun pelaaja painaa jotakin nuolinäppäimistä. Pelaaja ensin liikkuu tai hyökkää vihollista kohtaan ja sen jälkeen viholliset liikkuvat:

![](https://raw.githubusercontent.com/Zentryn/ot-harjoitustyo/master/dokumentaatio/kuvat/PeliVuoro.png)
